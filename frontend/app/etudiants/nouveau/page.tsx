'use client';

import { useEffect, useState } from 'react';
import { useRouter } from 'next/navigation';
import { api } from '../../../lib/api';

type Departement = { id: number; nom: string };

export default function NouveauEtudiantPage() {
  const router = useRouter();
  const [departements, setDepartements] = useState<Departement[]>([]);
  const [form, setForm] = useState({
    cin: '', nom: '', dateNaissance: '', email: '',
    anneePremiereInscription: new Date().getFullYear(),
    departementId: 0,
  });
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    api<Departement[]>('/api/departements').then(setDepartements).catch(e => setError(e.message));
  }, []);

  const submit = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      await api('/api/etudiants', {
        method: 'POST',
        body: JSON.stringify({
          ...form,
          departementId: form.departementId || null,
        }),
      });
      router.push('/etudiants');
    } catch (err: any) { setError(err.message); }
  };

  return (
    <div className="max-w-2xl mx-auto bg-white p-8 rounded-xl shadow">
      <h1 className="text-2xl font-bold mb-6">Nouvel etudiant</h1>
      {error && <div className="bg-red-100 text-red-700 p-3 rounded mb-4">{error}</div>}
      <form onSubmit={submit} className="space-y-4">
        <Field label="CIN"><input name="cin" required value={form.cin} onChange={e => setForm({...form, cin: e.target.value})} className="input" /></Field>
        <Field label="Nom"><input name="nom" required value={form.nom} onChange={e => setForm({...form, nom: e.target.value})} className="input" /></Field>
        <Field label="Date de naissance"><input type="date" required value={form.dateNaissance} onChange={e => setForm({...form, dateNaissance: e.target.value})} className="input" /></Field>
        <Field label="Email"><input type="email" value={form.email} onChange={e => setForm({...form, email: e.target.value})} className="input" /></Field>
        <Field label="Annee premiere inscription"><input type="number" required value={form.anneePremiereInscription} onChange={e => setForm({...form, anneePremiereInscription: +e.target.value})} className="input" /></Field>
        <Field label="Departement">
          <select value={form.departementId} onChange={e => setForm({...form, departementId: +e.target.value})} className="input">
            <option value={0}>-- Aucun --</option>
            {departements.map(d => <option key={d.id} value={d.id}>{d.nom}</option>)}
          </select>
        </Field>
        <button type="submit" className="bg-indigo-600 text-white px-6 py-2 rounded hover:bg-indigo-700">Creer</button>
      </form>
      <style jsx>{`.input { width: 100%; padding: 0.5rem; border: 1px solid #cbd5e1; border-radius: 6px; }`}</style>
    </div>
  );
}

function Field({ label, children }: { label: string; children: React.ReactNode }) {
  return <label className="block"><span className="text-sm font-medium text-slate-700">{label}</span>{children}</label>;
}
