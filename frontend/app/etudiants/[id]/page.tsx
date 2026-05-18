'use client';

import { useEffect, useState } from 'react';
import { useRouter, useParams } from 'next/navigation';
import { api } from '../../../lib/api';

type Departement = { id: number; nom: string };

export default function EditEtudiantPage() {
  const router = useRouter();
  const params = useParams();
  const id = params.id;
  const [form, setForm] = useState<any>(null);
  const [departements, setDepartements] = useState<Departement[]>([]);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    api<any>(`/api/etudiants/${id}`).then(setForm).catch(e => setError(e.message));
    api<Departement[]>('/api/departements').then(setDepartements).catch(() => {});
  }, [id]);

  const submit = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      await api(`/api/etudiants/${id}`, { method: 'PUT', body: JSON.stringify(form) });
      router.push('/etudiants');
    } catch (err: any) { setError(err.message); }
  };

  if (!form) return <p>Chargement...</p>;

  return (
    <div className="max-w-2xl mx-auto bg-white p-8 rounded-xl shadow">
      <h1 className="text-2xl font-bold mb-6">Editer etudiant #{id}</h1>
      {error && <div className="bg-red-100 text-red-700 p-3 rounded mb-4">{error}</div>}
      <form onSubmit={submit} className="space-y-4">
        <input value={form.cin} onChange={e => setForm({...form, cin: e.target.value})} placeholder="CIN" className="w-full p-2 border rounded" />
        <input value={form.nom} onChange={e => setForm({...form, nom: e.target.value})} placeholder="Nom" className="w-full p-2 border rounded" />
        <input type="date" value={form.dateNaissance} onChange={e => setForm({...form, dateNaissance: e.target.value})} className="w-full p-2 border rounded" />
        <input value={form.email || ''} onChange={e => setForm({...form, email: e.target.value})} placeholder="Email" className="w-full p-2 border rounded" />
        <input type="number" value={form.anneePremiereInscription} onChange={e => setForm({...form, anneePremiereInscription: +e.target.value})} className="w-full p-2 border rounded" />
        <select value={form.departementId || 0} onChange={e => setForm({...form, departementId: +e.target.value || null})} className="w-full p-2 border rounded">
          <option value={0}>-- Aucun --</option>
          {departements.map(d => <option key={d.id} value={d.id}>{d.nom}</option>)}
        </select>
        <button type="submit" className="bg-indigo-600 text-white px-6 py-2 rounded">Enregistrer</button>
      </form>
    </div>
  );
}
