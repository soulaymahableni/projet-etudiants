'use client';

import { useEffect, useState } from 'react';
import { api } from '../../lib/api';

type Departement = { id: number; nom: string };

export default function DepartementsPage() {
  const [items, setItems] = useState<Departement[]>([]);
  const [nom, setNom] = useState('');
  const [error, setError] = useState<string | null>(null);

  const load = async () => {
    try { setItems(await api<Departement[]>('/api/departements')); }
    catch (e: any) { setError(e.message); }
  };
  useEffect(() => { load(); }, []);

  const create = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!nom.trim()) return;
    await api('/api/departements', { method: 'POST', body: JSON.stringify({ nom }) });
    setNom('');
    load();
  };

  const remove = async (id: number) => {
    if (!confirm('Supprimer ?')) return;
    await api(`/api/departements/${id}`, { method: 'DELETE' });
    load();
  };

  return (
    <div className="space-y-6">
      <h1 className="text-3xl font-bold">Departements</h1>
      {error && <div className="bg-red-100 text-red-700 p-3 rounded">{error}</div>}
      <form onSubmit={create} className="flex gap-2 bg-white p-4 rounded-xl shadow">
        <input value={nom} onChange={e => setNom(e.target.value)} placeholder="Nom du departement"
               className="flex-1 p-2 border rounded" />
        <button className="bg-indigo-600 text-white px-4 py-2 rounded">Ajouter</button>
      </form>
      <ul className="bg-white rounded-xl shadow divide-y">
        {items.map(d => (
          <li key={d.id} className="flex justify-between items-center p-4">
            <span><strong>#{d.id}</strong> &nbsp; {d.nom}</span>
            <button onClick={() => remove(d.id)} className="text-red-600 hover:underline">Supprimer</button>
          </li>
        ))}
      </ul>
    </div>
  );
}
