const BASE_URL = "http://localhost:8080";
const productDetails = document.getElementById("product-details");

document.addEventListener("DOMContentLoaded", async () => {
    const urlParams = new URLSearchParams(window.location.search);
    const productId = urlParams.get("id");

    if (productId) {
        try {
            const response = await fetch(`${BASE_URL}/api/furniture/${productId}`, {
                method: "GET"
            });

            if (response.ok) {
                const furniture = await response.json();

                productDetails.innerHTML = `
                    <img src="/images/${furniture.imageName}" alt="Furniture Image">
                    <h2>${furniture.name}</h2>
                    <h3>${furniture.category}</h3>
                    <p>Цена: ${furniture.price} лв.</p>
                `;
            }
        } catch (error) {
            alert("Error occurred! Check console for more information.")
            console.error(error);
        }
    } else {
        productDetails.innerHTML = "<p>Invalid product ID.</p>";
    }
});