const SHIPPING = 12;
const TAX_RATE = 0.08;

function orderTotals() {
  const subtotal = getCartSubtotal();
  const shipping = subtotal === 0 ? 0 : SHIPPING;
  const tax = subtotal * TAX_RATE;
  return {
    subtotal,
    shipping,
    tax,
    total: subtotal + shipping + tax,
  };
}

function renderCart() {
  const root = document.getElementById("cart-root");
  const cart = getCart();

  if (!cart.length) {
    root.innerHTML = `
      <div class="empty-state">
        <p class="kicker">Empty bag</p>
        <h2>Nothing in the vault yet.</h2>
        <p>Grab a pair from the shop and it will show up here.</p>
        <p><a class="btn btn-accent" href="products.html">Shop sneakers</a></p>
      </div>
    `;
    return;
  }

  const totals = orderTotals();
  const items = cart
    .map(
      (item) => `
        <article class="cart-item" data-id="${item.id}" data-size="${item.size}">
          <img src="${item.image}" alt="${item.name}" />
          <div>
            <h3>${item.name}</h3>
            <p class="category-tag">Size US ${item.size}</p>
            <div class="qty-row">
              <button type="button" data-action="dec" aria-label="Decrease quantity">−</button>
              <span>${item.quantity}</span>
              <button type="button" data-action="inc" aria-label="Increase quantity">+</button>
              <button class="remove-btn" type="button" data-action="remove">Remove</button>
            </div>
          </div>
          <strong class="item-price">${formatCurrency(item.price * item.quantity)}</strong>
        </article>
      `
    )
    .join("");

  root.innerHTML = `
    <div class="cart-layout">
      <div>${items}</div>
      <aside class="summary-card">
        <h2>Order summary</h2>
        <div class="summary-row"><span>Subtotal</span><span>${formatCurrency(totals.subtotal)}</span></div>
        <div class="summary-row"><span>Shipping</span><span>${formatCurrency(totals.shipping)}</span></div>
        <div class="summary-row"><span>Tax</span><span>${formatCurrency(totals.tax)}</span></div>
        <div class="summary-row total"><span>Total</span><span>${formatCurrency(totals.total)}</span></div>
        <p><a class="btn btn-ghost btn-full" href="products.html">Keep shopping</a></p>
      </aside>
    </div>
  `;
}

document.addEventListener("DOMContentLoaded", () => {
  const root = document.getElementById("cart-root");
  root.addEventListener("click", (event) => {
    const button = event.target.closest("[data-action]");
    const item = event.target.closest(".cart-item");
    if (!button || !item) return;

    const id = item.dataset.id;
    const size = Number(item.dataset.size);
    const cartItem = getCart().find((entry) => entry.id === id && entry.size === size);
    if (!cartItem) return;

    if (button.dataset.action === "inc") updateQuantity(id, size, cartItem.quantity + 1);
    if (button.dataset.action === "dec") updateQuantity(id, size, cartItem.quantity - 1);
    if (button.dataset.action === "remove") removeFromCart(id, size);
    renderCart();
  });

  renderCart();
});
