'use client';

import { useEffect, useState } from 'react';
import Link from 'next/link';
import { api } from '../../lib/api';

type Etudiant = {
  id: number; cin: string; nom: string; dateNaissance: string;
  email?: string; anneePremiereInscription: number;
  departementId?: number; departementNom?: string; age?: number;
};

export default function EtudiantsPage() {
  const [etudiants, setEtudiants] = useState<Etudiant[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  const load = async () => {
    try {
      setLoading(true);
      const data = await api<Etudiant[]>('/api/etudiants');
      setEtudiants(data);
    } catch (e: any) {
      setError(e.message);
    } finally { setLoading(false); }
  };

  useEffect(() => { load(); }, []);

  const remove = async (id: number) => {
    if (!confirm('Supprimer ?')) return;
    await api(`/api/etudiants/${id}`, { method: 'DELETE' });
    load();
  };

  if (loading) return <p>Chargement...</p>;
  if (error) return <div className="bg-red-100 text-red-700 p-4 rounded">{error}</div>;

  return (
    <div className="space-y-4" data-testid="etudiant-list">
      <div className="flex justify-between items-center">
        <h1 className="text-3xl font-bold">Etudiants ({etudiants.length})</h1>
        <Link href="/etudiants/nouveau" className="bg-indigo-600 text-white px-4 py-2 rounded hover:bg-indigo-700">
          + Nouveau
        </Link>
      </div>
      <div className="bg-white rounded-xl shadow overflow-hidden">
        <table className="w-full text-sm">
          <thead className="bg-slate-100">
            <tr>
              <th className="px-4 py-3 text-left">CIN</th>
              <th className="px-4 py-3 text-left">Nom</th>
              <th className="px-4 py-3 text-left">Naissance</th>
              <th className="px-4 py-3 text-left">Age</th>
              <th className="px-4 py-3 text-left">Departement</th>
              <th className="px-4 py-3 text-right">Actions</th>
            </tr>
          </thead>
          <tbody>
            {etudiants.map(e => (
              <tr key={e.id} className="border-t" data-testid="etudiant-item">
                <td className="px-4 py-2">{e.cin}</td>
                <td className="px-4 py-2 font-medium">{e.nom}</td>
                <td className="px-4 py-2">{e.dateNaissance}</td>
                <td className="px-4 py-2">{e.age ?? '-'}</td>
                <td className="px-4 py-2">{e.departementNom ?? '-'}</td>
                <td className="px-4 py-2 text-right space-x-2">
                  <Link href={`/etudiants/${e.id}`} className="text-indigo-600 hover:underline">Editer</Link>
                  <button onClick={() => remove(e.id)} className="text-red-600 hover:underline">Suppr.</button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}
