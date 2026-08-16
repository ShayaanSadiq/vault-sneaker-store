function applyFilters() {
  const search = document.getElementById("search").value.trim().toLowerCase();
  const category = document.getElementById("category").value;
  const sort = document.getElementById("sort").value;

  let items = PRODUCTS.filter((product) => {
    const matchesCategory = category === "all" || product.category === category;
    const haystack = `${product.name} ${product.category} ${product.description}`.toLowerCase();
    return matchesCategory && (!search || haystack.includes(search));
  });

  if (sort === "price-asc") items.sort((a, b) => a.price - b.price);
  if (sort === "price-desc") items.sort((a, b) => b.price - a.price);
  if (sort === "name") items.sort((a, b) => a.name.localeCompare(b.name));

  document.getElementById("result-count").textContent =
    `${items.length} pair${items.length === 1 ? "" : "s"} on the wall`;
  document.getElementById("product-grid").innerHTML = items.map(productCard).join("");
}

document.addEventListener("DOMContentLoaded", () => {
  const search = document.getElementById("search");
  const category = document.getElementById("category");
  const sort = document.getElementById("sort");
  const params = new URLSearchParams(window.location.search);
  const preset = params.get("category");

  if (preset && [...category.options].some((option) => option.value === preset)) {
    category.value = preset;
  }

  [search, category, sort].forEach((el) => el.addEventListener("input", applyFilters));
  applyFilters();
});
