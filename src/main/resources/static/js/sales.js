document.addEventListener("DOMContentLoaded", () => {

  const productSelect = document.getElementById("productSelect");
  const quantityInput = document.getElementById("quantity");
  const cartTable = document.getElementById("cartTable");
  const grandTotalSpan = document.getElementById("grandTotal");
  const message = document.getElementById("message");

  let products = [];
  let cart = [];
  const today = new Date();


  async function loadProducts() {
    const res = await fetch("/api/products");
    products = await res.json();

    productSelect.innerHTML = "";
    products
  .filter(p => !p.expiryDate || new Date(p.expiryDate) >= today)
  .forEach(p => {
    const option = document.createElement("option");
    option.value = p.id;
    option.textContent = `${p.name} - ₹${p.price} (Stock: ${p.stock})`;
    productSelect.appendChild(option);
  });
  }

  window.addToCart = function () {
  const productId = productSelect.value;
  const quantity = Number(quantityInput.value);

  if (!productId || quantity <= 0) {
    alert("Enter valid quantity");
    return;
  }

  const product = products.find(p => p.id === productId);
  if (!product) return;

  if (quantity > product.stock) {
    alert(
      `Not enough stock!\nAvailable: ${product.stock}`
    );
    return;
  }

  cart.push({
    productId: product.id,
    quantity: quantity
  });

  quantityInput.value = "";
  renderCart();
};


  function renderCart() {
    cartTable.innerHTML = "";
    let total = 0;

    cart.forEach(item => {
      const product = products.find(p => p.id === item.productId);
      const itemTotal = product.price * item.quantity;
      total += itemTotal;

      cartTable.innerHTML += `
        <tr>
          <td>${product.name}</td>
          <td>${item.quantity}</td>
          <td>₹${product.price}</td>
          <td>₹${itemTotal}</td>
        </tr>`;
    });

    grandTotalSpan.textContent = `₹${total}`;
  }

  window.generateBill = async function () {
    if (cart.length === 0) {
      alert("Cart is empty");
      return;
    }

    try {
      const res = await fetch("/api/bills", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(cart)
      });

      if (!res.ok) {
        const err = await res.text();
        throw new Error(err);
      }

      const bill = await res.json();

      message.style.color = "green";
      message.textContent = `✅ Bill Generated. Total ₹${bill.totalAmount}`;

      cart = [];
      renderCart();
      loadProducts(); 

    } catch (err) {
      message.style.color = "red";
      message.textContent = err.message;
    }
  };

  loadProducts();
});
