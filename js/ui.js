function formatCurrency(amount) {
  return new Intl.NumberFormat("en-US", {
    style: "currency",
    currency: "USD",
  }).format(amount);
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
      </div>
    </article>
  `;
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
