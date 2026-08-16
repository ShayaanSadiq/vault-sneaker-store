const API_BASE = "http://localhost:8080";

async function fetchProducts(category) {
  const params = category && category !== "all" ? `?category=${encodeURIComponent(category)}` : "";
  const response = await fetch(`${API_BASE}/api/products${params}`);
  if (!response.ok) {
    throw new Error("Unable to load products");
  }
  return response.json();
}

async function loadCatalog() {
  try {
    const items = await fetchProducts("all");
    PRODUCTS.splice(0, PRODUCTS.length, ...items);
  } catch {
    // Keep the local catalog if the API is not running.
  }
}

async function createOrder(payload) {
  const response = await fetch(`${API_BASE}/api/orders`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(payload),
  });

  const body = await response.json().catch(() => ({}));
  if (!response.ok) {
    throw new Error(body.detail || "Checkout failed");
  }
  return body;
}
