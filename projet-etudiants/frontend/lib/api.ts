// Centralized API URL - points to API Gateway by default
export const API_BASE = process.env.NEXT_PUBLIC_API_URL || 'http://localhost:8080';

export async function api<T>(path: string, options: RequestInit = {}): Promise<T> {
  const res = await fetch(`${API_BASE}${path}`, {
    headers: { 'Content-Type': 'application/json', ...(options.headers || {}) },
    cache: 'no-store',
    ...options,
  });
  if (!res.ok) {
    const text = await res.text();
    throw new Error(`HTTP ${res.status} : ${text}`);
  }
  if (res.status === 204) return undefined as T;
  return res.json();
}
