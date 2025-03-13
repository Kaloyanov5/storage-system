const BASE_URL = "http://localhost:8080";
const productList = document.getElementById("product-list");

document.addEventListener("DOMContentLoaded", async () => {
    try {
        const response = await fetch(`${BASE_URL}/api/furniture`, {
            method: "GET"
        });

        if (response.ok) {
            const furnitureList = await response.body();

            furnitureList.forEach(product => {
                const productDiv = document.createElement("div");
                productDiv.classList.add("product");

                productDiv.innerHTML = `
                    <img src="/images/${product.imageUrl}" alt="Furniture Image">
                    <h2>${product.name} - ${product.id}</h2>
                    <h3>${product.category}</h3>
                    <p>Цена: ${product.price} лв.</p>
                `;

                productList.appendChild(productDiv);
            });
        }
    } catch (error) {
        alert("Error occurred! Check console for more information.")
        console.error(error);
    }
})