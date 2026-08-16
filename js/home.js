document.addEventListener("DOMContentLoaded", () => {
  const grid = document.getElementById("featured-grid");
  if (!grid) return;
  const featured = PRODUCTS.filter((item) => item.badge).slice(0, 3);
  grid.innerHTML = featured.map(productCard).join("");
  bindAddToCart(grid);
});
