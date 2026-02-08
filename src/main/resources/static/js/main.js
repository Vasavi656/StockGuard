const baseUrl = "/api/products";

async function loadDashboard() {
  const res = await fetch(baseUrl);
  const products = await res.json();

  const today = new Date();
  const sevenDaysLater = new Date();
  sevenDaysLater.setDate(today.getDate() + 7);

  let expiredCount = 0;
  let expiringSoonCount = 0;
  let freshCount = 0;

  products.forEach(p => {
    if (!p.expiryDate) return;

    const expiry = new Date(p.expiryDate);

    if (expiry < today) {
      expiredCount++;
    } else if (expiry <= sevenDaysLater) {
      expiringSoonCount++;
    } else {
      freshCount++;
    }
  })

  document.getElementById("totalProducts").innerText =
    products.length;

  document.getElementById("lowStock").innerText =
    products.filter(p => Number(p.stock) <= Number(p.minStockLevel)).length;

  document.getElementById("expiringSoon").innerText =
    expiringSoonCount;

  document.getElementById("expired").innerText =
    expiredCount;

  document.getElementById("fresh").innerText =
    freshCount;
}

window.onload = loadDashboard;
