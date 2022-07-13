const URL = 'http://localhost:8080';
let categories = [];
$(document).ready(function() {
    document.getElementById("addCategoryForm").addEventListener("submit", addCategory);
    fetchCategories();
});

function fetchCategories() {
    fetch(`${URL}/categories`, {
        method: 'GET'
    }).then((result) => {
        result.json().then((result) => {
            categories = result;
            loadCategories();
        });
    });
    loadCategories();
}


function addCategory(e){
    e.preventDefault();

    const data = {};
    const formData = new FormData(e.target);
    data["name"] = formData.get("name");

    fetch(`${URL}/categories`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    }).then((result) => {
        result.json().then((category) => {
            fetchCategories();
        });
    });
}

function deleteCategory(id) {
    fetch(`${URL}/categories/${id}`, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json'
        }
    }).then(function() {
        fetchCategories();
    });
}


function loadCategories() {
    let categoryDisplay = document.getElementById("categoryDisplay");
    categoryDisplay.innerHTML = "";
    categories.forEach(function(category) {
        const row = document.createElement('tr');
        const id = document.createElement('td');
        id.innerText = category.id;
        const name = document.createElement('td');
        name.innerText = category.name;
        const deleteBtn = document.createElement('button');
        deleteBtn.innerText = "Delete";
        deleteBtn.onclick = function() {
            deleteCategory(category.id);
        };
        row.appendChild(id);
        row.appendChild(name);
        row.appendChild(deleteBtn);
        categoryDisplay.append(row);
    });
}