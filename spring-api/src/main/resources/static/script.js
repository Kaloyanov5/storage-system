const BASE_URL = "http://localhost:8080";
const furnitureList = document.getElementById("furniture-list");

document.addEventListener("DOMContentLoaded", async () => {
    try {
        const response = await fetch(`${BASE_URL}/api/furniture`, {
            method: "GET"
        });

        if (response.ok) {
            const furnitureListResponse = await response.json();

            furnitureListResponse.forEach(furniture => {
                const furnitureDiv = document.createElement("div");
                furnitureDiv.classList.add("furniture");

                furnitureDiv.innerHTML = `
                    <img src="/images/${furniture.imageName}" alt="Furniture Image">
                    <h2>${furniture.name} - ${furniture.id}</h2>
                    <h3>${furniture.category}</h3>
                    <p>Цена: ${furniture.price} лв.</p>
                `;

                furnitureList.appendChild(furnitureDiv);
            });
        }
    } catch (error) {
        alert("Error occurred! Check console for more information.")
        console.error(error);
    }
})