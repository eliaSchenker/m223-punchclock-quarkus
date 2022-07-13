const URL = 'http://localhost:8080';
let entries = [];

function openUpdateEntryForm() {
    document.getElementById("error").innerText = "";
    document.getElementById("createEntryForm").removeEventListener("submit", createEntry);
    document.getElementById("createEntryForm").addEventListener("submit", updateEntry);
    document.getElementById("formTitle").innerText = "Update entry";
    document.getElementById("updateBack").style.display = "block";
}

function closeUpdateEntryForm() {
    document.getElementById("error").innerText = "";
    document.getElementById("createEntryForm").removeEventListener("submit", updateEntry);
    document.getElementById("createEntryForm").addEventListener("submit", createEntry);
    document.getElementById("formTitle").innerText = "Add entry";
    document.getElementById("updateBack").style.display = "none";
}

const createEntry = (e) => {
    e.preventDefault();
    const formData = new FormData(e.target);
    let data = {};
    data["checkIn"] = formData.get("checkIn");
    data["checkOut"] = formData.get("checkOut");
    data["category"] = categories[formData.get("category")];

    fetch(`${URL}/entries`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    }).then((result) => {
        result.json().then((entry) => {
            if(entry.id == undefined) {
                document.getElementById("error").innerText = entry.parameterViolations[0].message;
            }else {
                indexEntries();
            }
        });
    }).catch((result) => {
        result.json().then((response) => {
            document.getElementById("error").innerText = response.parameterViolations[0].message;
        });
    });
};

const updateEntry = (e) => {
    e.preventDefault();
    const formData = new FormData(e.target);
    let data = {};
    data["id"] = formData.get("id");
    data["checkIn"] = formData.get("checkIn");
    data["checkOut"] = formData.get("checkOut");
    data["category"] = categories[formData.get("category")];

    fetch(`${URL}/entries`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    }).then((result) => {
        result.json().then((entry) => {
            if(entry.checkIn == undefined) {
                document.getElementById("errorUpdate").innerText = entry.parameterViolations[0].message;
            }else {
                closeUpdateEntryForm();
                indexEntries();
            }
        });
    }).catch((result) => {
        result.json().then((response) => {
            document.getElementById("errorUpdate").innerText = response.parameterViolations[0].message;
        });
    });
};


const deleteEntry = (id) => {
    fetch(`${URL}/entries/${id}`, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json'
        }
    });
}

const indexEntries = () => {
    fetch(`${URL}/entries`, {
        method: 'GET'
    }).then((result) => {
        result.json().then((result) => {
            entries = result;
            renderEntries();
        });
    });
    renderEntries();
};

const createCell = (text) => {
    const cell = document.createElement('td');
    cell.innerText = text;
    return cell;
};

let categories = [];

function renderCategoryDropdown() {
    let dropdown = document.getElementById("categoriesDropdown");
    for(let i = 0;i<categories.length;i++) {
        const categoryIndex = i;
        let option = document.createElement("option");
        option.innerText = categories[categoryIndex].name;
        option.value = categoryIndex;
        dropdown.appendChild(option);
    }
}

const loadCategories = () => {
    fetch(`${URL}/categories`, {
        method: 'GET'
    }).then((result) => {
        result.json().then((result) => {
            categories = result;
            renderCategoryDropdown();
        });
    });
}

const renderEntries = () => {
    const display = document.querySelector('#entryDisplay');
    display.innerHTML = '';
    entries.forEach((entry) => {
        const row = document.createElement('tr');
        row.appendChild(createCell(entry.id));
        row.appendChild(createCell(new Date(entry.checkIn).toLocaleString()));
        row.appendChild(createCell(new Date(entry.checkOut).toLocaleString()));
        row.appendChild(createCell(entry.category.name));

        const deleteButton = document.createElement('button');
        deleteButton.innerText = "Delete";
        deleteButton.onclick = function() {
            deleteEntry(entry.id);
            indexEntries();
        };
        row.appendChild(deleteButton);

        const updateButton = document.createElement('button');
        updateButton.innerText = "Update";
        updateButton.onclick = function() {
            let checkInDate = new Date(entry.checkIn);
            checkInDate.setMinutes(checkInDate.getMinutes() - checkInDate.getTimezoneOffset());
            let checkOutDate = new Date(entry.checkOut);
            checkOutDate.setMinutes(checkOutDate.getMinutes() - checkOutDate.getTimezoneOffset());
            document.getElementById("id").value = entry.id;
            document.getElementById("checkIn").value = checkInDate.toISOString().slice(0, 16);
            document.getElementById("checkOut").value = checkOutDate.toISOString().slice(0, 16);
            document.getElementById("categoriesDropdown").selectedIndex = categories.map(function(e) { return e.id; }).indexOf(entry.category.id);
            openUpdateEntryForm();
        }
        row.appendChild(updateButton);

        display.appendChild(row);
    });
};

document.addEventListener('DOMContentLoaded', function(){
    closeUpdateEntryForm();
    indexEntries();
    loadCategories();
});