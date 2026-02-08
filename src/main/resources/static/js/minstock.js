document.addEventListener("DOMContentLoaded", async () => {
  const tableBody = document.getElementById("minStockTable");
  const chartCanvas = document.getElementById("minStockChart");
  let chartInstance = null;

  async function loadMinStockData() {
    try {
      const response = await fetch("/api/products");
      const products = await response.json();

      const lowStockProducts = products.filter(
        p => p.stock <= p.minStockLevel
      );

      tableBody.innerHTML = "";

      lowStockProducts.forEach(p => {
        const row = document.createElement("tr");
        row.innerHTML = `
          <td>${p.id}</td>
          <td>${p.name}</td>
          <td>${p.stock}</td>
          <td>${p.minStockLevel}</td>
        `;
        tableBody.appendChild(row);
      });

      
      const labels = lowStockProducts.map(p => p.name);
      const currentStock = lowStockProducts.map(p => p.stock);
      const minStock = lowStockProducts.map(p => p.minStockLevel);

      if (chartInstance) chartInstance.destroy();

      chartInstance = new Chart(chartCanvas, {
        type: "bar",
        data: {
          labels: labels,
          datasets: [
            {
              label: "Current Stock",
              data: currentStock,
              backgroundColor: "rgba(255, 99, 132, 0.7)"
            },
            {
              label: "Minimum Required",
              data: minStock,
              backgroundColor: "rgba(54, 162, 235, 0.7)"
            }
          ]
        },
        options: {
          responsive: true,
          plugins: {
            title: {
              display: true,
              text: "Low Stock vs Minimum Stock Level"
            }
          },
          scales: {
            y: {
              beginAtZero: true
            }
          }
        }
      });

    } catch (err) {
      console.error("Error loading min stock data:", err);
    }
  }

  loadMinStockData();
});
