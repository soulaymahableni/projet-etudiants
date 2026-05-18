import Link from 'next/link';

export default function Home() {
  return (
    <div className="space-y-6">
      <h1 className="text-4xl font-bold">Bienvenue</h1>
      <p className="text-slate-600">
        Application de gestion d&apos;etudiants et de departements connectee a une architecture microservices Spring Cloud.
      </p>
      <div className="grid md:grid-cols-2 gap-4">
        <Link href="/etudiants" className="block p-6 bg-white rounded-xl shadow hover:shadow-md transition">
          <h2 className="text-xl font-semibold mb-2">👥 Gerer les etudiants</h2>
          <p className="text-slate-600">Liste, creation, edition, suppression.</p>
        </Link>
        <Link href="/departements" className="block p-6 bg-white rounded-xl shadow hover:shadow-md transition">
          <h2 className="text-xl font-semibold mb-2">🏛 Gerer les departements</h2>
          <p className="text-slate-600">CRUD complet des departements.</p>
        </Link>
      </div>
    </div>
  );
}
