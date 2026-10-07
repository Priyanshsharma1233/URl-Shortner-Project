const BASE_URL = import.meta.env.VITE_API_URL;

export async function shortenUrl(url) {
  const res = await fetch(`${BASE_URL}/shorten`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ url }),
  });

  if (!res.ok) {
    const error = await res.json();
    throw new Error(error.message || "Failed to shorten URL");
  }

  return res.json();
}

export async function login(username, password) {
  const res = await fetch(`${BASE_URL}/api/auth/login`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ username, password }),
  });

  if (!res.ok) {
    const error = await res.json();
    throw new Error(error.message || "Login failed");
  }

  return res.json();
}

export async function getAnalytics(shortCode) {
  const token = localStorage.getItem("token");

  const res = await fetch(`${BASE_URL}/api/analytics/${shortCode}`, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });

  if (res.status === 401) {
    throw new Error("UNAUTHORIZED");
  }

  if (!res.ok) {
    const error = await res.json();
    throw new Error(error.message || "Failed to load analytics");
  }

  return res.json();
}