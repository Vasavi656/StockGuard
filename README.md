# StockGuard  

StockGuard is a backend-focused inventory management system designed to help businesses track stock levels, monitor product expiry dates, and generate automatic alerts to prevent shortages and losses.  
The system emphasizes business logic, automation, and alert handling using Spring Boot

---

## Features  

- **Product Management (CRUD)** – Add, update, delete, and view inventory products  
- **Low Stock Alerts** – Automatic alerts when product quantity reaches the minimum stock level  
- **Expiry Alerts** – Alerts for products that are near expiry or already expired  
- **Sales Tracking** – Stock is reduced automatically when sales are recorded  
- **Scheduled Daily Alerts** – Daily system checks for low stock and expiry conditions  

---

## Technologies Used  

- **Spring Boot** – Backend framework and business logic  
- **MongoDB** – NoSQL database for inventory storage  
- **Spring Scheduler** – Automated daily alert checks  
- **Spring Security** – Application security configuration  

---

## How to Use  

1. Add products with quantity, minimum stock level, and expiry date  
2. Record sales to automatically update stock levels  
3. View alerts for low-stock and expiry conditions  
4. System runs scheduled daily checks to ensure inventory safety  
