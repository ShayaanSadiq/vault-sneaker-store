function formatCurrency(amount) {
  return new Intl.NumberFormat("en-US", {
    style: "currency",
    currency: "USD",
  }).format(amount);
}

function showToast(message) {
  const toast = document.getElementById("toast");
  if (!toast) return;
  toast.textContent = message;
  toast.classList.add("is-visible");
  window.clearTimeout(showToast.timer);
  showToast.timer = window.setTimeout(() => toast.classList.remove("is-visible"), 1800);
}

function sizeOptions() {
  return SIZES.map((size) => `<option value="${size}">US ${size}</option>`).join("");
}

function productCard(product) {
  const badgeClass = product.badge === "Limited" ? "badge limited" : "badge";
  const badge = product.badge ? `<span class="${badgeClass}">${product.badge}</span>` : "";

  return `
    <article class="product-card">
      <div class="product-media">
        ${badge}
        <img src="${product.image}" alt="${product.name} sneaker" />
      </div>
      <div class="product-body">
        <div class="product-meta">
          <div>
            <h3>${product.name}</h3>
            <p class="category-tag">${product.category}</p>
          </div>
          <span class="price">${formatCurrency(product.price)}</span>
        </div>
        <p class="category-tag">${product.description}</p>
        <form class="card-actions" data-add="${product.id}">
          <select name="size" aria-label="Size for ${product.name}">
            ${sizeOptions()}
          </select>
          <button class="btn btn-primary" type="submit">Add</button>
        </form>
      </div>
    </article>
  `;
}

function bindAddToCart(root) {
  root.querySelectorAll("form[data-add]").forEach((form) => {
    form.addEventListener("submit", (event) => {
      event.preventDefault();
      addToCart(form.dataset.add, Number(form.size.value), 1);
      showToast("Added to bag");
    });
  });
}

function initNav() {
  const toggle = document.querySelector(".nav-toggle");
  const nav = document.querySelector(".site-nav");
  if (toggle && nav) {
    toggle.addEventListener("click", () => {
      const open = nav.classList.toggle("is-open");
      toggle.setAttribute("aria-expanded", String(open));
    });
  }
}

document.addEventListener("DOMContentLoaded", initNav);
