const baseUrl = "/api/products";
let products = [];
let isEditMode = false;

async function loadProducts() {
  const res = await fetch(baseUrl);
  products = await res.json();

  const tbody = document.getElementById("productTable");
  tbody.innerHTML = "";

  products.forEach(p => {
    const tr = document.createElement("tr");
    tr.innerHTML = `
      <td>${p.id}</td>
      <td>${p.name}</td>
      <td>${p.stock}</td>
      <td>${p.minStockLevel}</td>
      <td>${p.expiryDate ? p.expiryDate.split('T')[0] : ''}</td>
      <td>
        <button onclick="editProduct('${p.id}')">Edit</button>
        <button onclick="deleteProduct('${p.id}')">Delete</button>
      </td>
    `;
    tbody.appendChild(tr);
  });
}

document.getElementById("productForm").addEventListener("submit", async (e) => {
  e.preventDefault();

  const id = document.getElementById("productId").value;

  const product = {
    name: document.getElementById("name").value,
    minStockLevel: Number(document.getElementById("minStock").value),
    expiryDate: document.getElementById("expiryDate").value
  };

  
  if (!isEditMode) {
    product.stock = Number(document.getElementById("stock").value);
  }

  try {
    let res;

    if (id) {
      res = await fetch(`${baseUrl}/${id}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(product)
      });
    } else {
      res = await fetch(baseUrl, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(product)
      });
    }

    if (!res.ok) {
      const msg = await res.text();
      alert(`❌ ${msg}`);
      return;
    }

    alert("✅ Product saved successfully");

    resetForm();
    loadProducts();

  } catch (err) {
    console.error(err);
    alert("⚠️ Something went wrong");
  }
});

async function deleteProduct(id) {
  if (!confirm("Are you sure you want to delete this product?")) return;

  await fetch(`${baseUrl}/${id}`, { method: "DELETE" });
  loadProducts();
}

function editProduct(id) {
  const p = products.find(prod => prod.id === id);
  if (!p) return;

  isEditMode = true;

  document.getElementById("productId").value = p.id;
  document.getElementById("name").value = p.name;
  document.getElementById("minStock").value = p.minStockLevel;
  document.getElementById("expiryDate").value =
    p.expiryDate ? p.expiryDate.split("T")[0] : "";


  const stockInput = document.getElementById("stock");
  stockInput.value = p.stock;
  stockInput.disabled = true;
}

function resetForm() {
  document.getElementById("productForm").reset();
  document.getElementById("productId").value = "";
  document.getElementById("stock").disabled = false;
  isEditMode = false;
}

window.onload = loadProducts;
