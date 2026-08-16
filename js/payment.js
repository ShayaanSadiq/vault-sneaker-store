const SHIPPING = 12;
const TAX_RATE = 0.08;

function orderTotals() {
  const subtotal = getCartSubtotal();
  const shipping = subtotal === 0 ? 0 : SHIPPING;
  const tax = subtotal * TAX_RATE;
  return { subtotal, shipping, tax, total: subtotal + shipping + tax };
}

function luhnCheck(number) {
  let sum = 0;
  let shouldDouble = false;
  for (let i = number.length - 1; i >= 0; i -= 1) {
    let digit = Number(number[i]);
    if (shouldDouble) {
      digit *= 2;
      if (digit > 9) digit -= 9;
    }
    sum += digit;
    shouldDouble = !shouldDouble;
  }
  return sum % 10 === 0;
}

function digits(value) {
  return value.replace(/\D/g, "");
}

function setError(name, message) {
  const input = document.querySelector(`[name="${name}"]`);
  const error = document.querySelector(`[data-error="${name}"]`);
  if (input) input.classList.toggle("is-invalid", Boolean(message));
  if (error) error.textContent = message || "";
}

function validate() {
  const values = {
    fullName: document.getElementById("fullName").value.trim(),
    email: document.getElementById("email").value.trim(),
    phone: document.getElementById("phone").value.trim(),
    address: document.getElementById("address").value.trim(),
    city: document.getElementById("city").value.trim(),
    zip: document.getElementById("zip").value.trim(),
    cardName: document.getElementById("cardName").value.trim(),
    cardNumber: digits(document.getElementById("cardNumber").value),
    expiry: document.getElementById("expiry").value.trim(),
    cvv: digits(document.getElementById("cvv").value),
    terms: document.getElementById("terms").checked,
  };

  const errors = {};

  if (values.fullName.split(/\s+/).length < 2) {
    errors.fullName = "Enter your first and last name.";
  }
  if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(values.email)) {
    errors.email = "Enter a valid email address.";
  }
  if (digits(values.phone).length < 10) {
    errors.phone = "Enter a phone number with at least 10 digits.";
  }
  if (values.address.length < 6) {
    errors.address = "Enter a full street address.";
  }
  if (values.city.length < 2) {
    errors.city = "Enter a city.";
  }
  if (!/^[A-Za-z0-9\s-]{4,10}$/.test(values.zip)) {
    errors.zip = "Enter a valid ZIP or postal code.";
  }
  if (values.cardName.split(/\s+/).length < 2) {
    errors.cardName = "Enter the name printed on the card.";
  }
  if (values.cardNumber.length !== 16 || !luhnCheck(values.cardNumber)) {
    errors.cardNumber = "Enter a valid 16-digit card number.";
  }

  const expiryMatch = values.expiry.match(/^(0[1-9]|1[0-2])\/(\d{2})$/);
  if (!expiryMatch) {
    errors.expiry = "Use MM/YY format.";
  } else {
    const month = Number(expiryMatch[1]);
    const year = 2000 + Number(expiryMatch[2]);
    if (new Date(year, month) <= new Date()) {
      errors.expiry = "This card is expired.";
    }
  }

  if (values.cvv.length < 3 || values.cvv.length > 4) {
    errors.cvv = "Enter a 3 or 4 digit CVV.";
  }
  if (!values.terms) {
    errors.terms = "Please confirm before paying.";
  }

  [
    "fullName",
    "email",
    "phone",
    "address",
    "city",
    "zip",
    "cardName",
    "cardNumber",
    "expiry",
    "cvv",
    "terms",
  ].forEach((name) => setError(name, errors[name]));

  return Object.keys(errors).length === 0;
}

function renderSummary() {
  const cart = getCart();
  const box = document.getElementById("order-summary");
  if (!cart.length) {
    box.innerHTML = `
      <h2>Your bag is empty</h2>
      <p>Add a pair before paying.</p>
      <p><a class="btn btn-accent btn-full" href="products.html">Go to shop</a></p>
    `;
    document.querySelector("#payment-form button[type=submit]").disabled = true;
    return;
  }

  const totals = orderTotals();
  box.innerHTML = `
    <h2>Order summary</h2>
    ${cart
      .map(
        (item) => `
          <div class="summary-row">
            <span>${item.name} · US ${item.size} × ${item.quantity}</span>
            <span>${formatCurrency(item.price * item.quantity)}</span>
          </div>
        `
      )
      .join("")}
    <div class="summary-row"><span>Shipping</span><span>${formatCurrency(totals.shipping)}</span></div>
    <div class="summary-row"><span>Tax</span><span>${formatCurrency(totals.tax)}</span></div>
    <div class="summary-row total"><span>Total</span><span>${formatCurrency(totals.total)}</span></div>
  `;
}

function showSuccess(orderId, total) {
  clearCart();
  document.getElementById("payment-root").innerHTML = `
    <div class="success">
      <p class="kicker">Paid</p>
      <h1>ORDER LOCKED IN.</h1>
      <p>This is a demo confirmation. No payment was processed.</p>
      <p>Order <span class="order-id">${orderId}</span> · ${total}</p>
      <p>
        <a class="btn btn-accent" href="index.html">Back home</a>
        <a class="btn btn-ghost" href="products.html">Shop again</a>
      </p>
    </div>
  `;
}

document.addEventListener("DOMContentLoaded", () => {
  renderSummary();

  const cardNumber = document.getElementById("cardNumber");
  const expiry = document.getElementById("expiry");

  cardNumber.addEventListener("input", () => {
    const raw = digits(cardNumber.value).slice(0, 16);
    cardNumber.value = raw.replace(/(\d{4})(?=\d)/g, "$1 ").trim();
  });

  expiry.addEventListener("input", () => {
    const raw = digits(expiry.value).slice(0, 4);
    expiry.value = raw.length > 2 ? `${raw.slice(0, 2)}/${raw.slice(2)}` : raw;
  });

  document.getElementById("payment-form").addEventListener("submit", (event) => {
    event.preventDefault();
    if (!getCart().length) return;
    if (!validate()) return;
    showSuccess(`VLT-${Date.now().toString().slice(-8)}`, formatCurrency(orderTotals().total));
  });
});
