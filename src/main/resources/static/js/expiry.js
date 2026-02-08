const baseUrl = "/api/products/expiring-soon";
let chartInstance = null;

async function loadExpiry(days = 7) {
  const res = await fetch(`${baseUrl}?days=${days}`);
  const products = await res.json();

  const tbody = document.getElementById("expiryTable");
  tbody.innerHTML = "";

  let expiredCount = 0;
  let expiringSoonCount = 0;

  products.forEach(p => {
    const tr = document.createElement("tr");

    const expiryDate = new Date(p.expiryDate);
    const today = new Date();
    today.setHours(0, 0, 0, 0);
    expiryDate.setHours(0, 0, 0, 0);

    const status = expiryDate < today ? "Expired" : "Expiring Soon";

    if (status === "Expired") expiredCount++;
    else expiringSoonCount++;

    tr.innerHTML = `
      <td>${p.id}</td>
      <td>${p.name}</td>
      <td>${p.expiryDate ? p.expiryDate.split("T")[0] : ''}</td>
      <td>${status}</td>
    `;
    tbody.appendChild(tr);
  });

  const chartCanvas = document.getElementById("expiryChart");
  const data = {
    labels: ["Expired", "Expiring Soon"],
    datasets: [{
      data: [expiredCount, expiringSoonCount],
      backgroundColor: ["rgba(255, 99, 132, 0.7)", "rgba(255, 159, 64, 0.7)"], 
      borderColor: ["rgba(255, 99, 132, 1)", "rgba(255, 159, 64, 1)"],
      borderWidth: 1
    }]
  };

  if (chartInstance) chartInstance.destroy();

  chartInstance = new Chart(chartCanvas, {
    type: "pie",
    data: data,
    options: {
      responsive: true,
      plugins: {
        legend: { position: "bottom" },
        title: { display: true, text: "Expiry Status" }
      }
    }
  });
}

window.onload = () => loadExpiry();
