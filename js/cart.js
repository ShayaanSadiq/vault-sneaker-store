const CART_KEY = "vault-cart";

function getCart() {
  try {
    const raw = localStorage.getItem(CART_KEY);
    return raw ? JSON.parse(raw) : [];
  } catch {
    return [];
  }
}

function saveCart(cart) {
  localStorage.setItem(CART_KEY, JSON.stringify(cart));
  updateCartCount();
}

function addToCart(productId, size, quantity) {
  const product = PRODUCTS.find((item) => item.id === productId);
  if (!product) return;

  const cart = getCart();
  const qty = Number(quantity) || 1;
  const existing = cart.find((item) => item.id === productId && item.size === size);

  if (existing) {
    existing.quantity += qty;
  } else {
    cart.push({
      id: product.id,
      name: product.name,
      price: product.price,
      image: product.image,
      size,
      quantity: qty,
    });
  }

  saveCart(cart);
}

function updateQuantity(productId, size, quantity) {
  const cart = getCart();
  const item = cart.find((entry) => entry.id === productId && entry.size === size);
  if (!item) return;

  const next = Number(quantity);
  if (next < 1) {
    removeFromCart(productId, size);
    return;
  }

  item.quantity = next;
  saveCart(cart);
}

function removeFromCart(productId, size) {
  saveCart(getCart().filter((item) => !(item.id === productId && item.size === size)));
}

function clearCart() {
  saveCart([]);
}

function getCartCount() {
  return getCart().reduce((sum, item) => sum + item.quantity, 0);
}

function getCartSubtotal() {
  return getCart().reduce((sum, item) => sum + item.price * item.quantity, 0);
}

function updateCartCount() {
  const count = getCartCount();
  document.querySelectorAll("[data-cart-count]").forEach((el) => {
    el.textContent = String(count);
    el.hidden = count === 0;
  });
}

document.addEventListener("DOMContentLoaded", updateCartCount);
