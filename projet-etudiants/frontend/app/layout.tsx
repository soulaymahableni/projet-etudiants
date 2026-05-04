import './globals.css';
import type { Metadata } from 'next';
import Link from 'next/link';

export const metadata: Metadata = {
  title: 'Gestion des Etudiants',
  description: 'Frontend Next.js - Microservices etudiants',
};

export default function RootLayout({ children }: { children: React.ReactNode }) {
  return (
    <html lang="fr">
      <body className="bg-slate-50 text-slate-900 min-h-screen">
        <header className="bg-gradient-to-r from-indigo-600 to-purple-600 text-white shadow-lg">
          <nav className="max-w-6xl mx-auto px-6 py-4 flex items-center justify-between">
            <Link href="/" className="text-xl font-bold">📚 Etudiants</Link>
            <div className="flex gap-4">
              <Link href="/etudiants" className="hover:underline">Etudiants</Link>
              <Link href="/departements" className="hover:underline">Departements</Link>
            </div>
          </nav>
        </header>
        <main className="max-w-6xl mx-auto px-6 py-8">{children}</main>
      </body>
    </html>
  );
}
