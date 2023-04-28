function getCookie(name) {
    let nameEQ = name + "=";
    let ca = document.cookie.split(';');
    for (let i = 0; i < ca.length; i++) {
        let c = ca[i];
        while (c.charAt(0) == ' ') c = c.substring(1, c.length);
        if (c.indexOf(nameEQ) == 0) return c.substring(nameEQ.length, c.length);
    }
    return null;
}

function getUserIdFromCookie() {
    console.log("getUserIdFromCookie");
    return 1;
    // return getCookie("userId");
}

var userID = getUserIdFromCookie();
var url_fetch_portfolio = "http://localhost:8080/portfolio/" + userID;
var url_movement = "http://localhost:8080/movement";
var url_server_health = "http://localhost:8080/actuator/health";
var url_upload_file = "http://localhost:8080/upload";
var url_download_file = "http://localhost:8080/download/" + userID;
var serverUP = false;

function formatDate(dateString) {
    const date = new Date(dateString);
    const day = date.getDate().toString().padStart(2, '0');
    const month = (date.getMonth() + 1).toString().padStart(2, '0');
    const year = date.getFullYear();
    return `${day}/${month}/${year}`;
}



function fillTable(responseJson) {  
  const table = document.getElementById("table-portfolio");
  table.innerHTML = `<tr>
  <th>ID</th>
  <th>Fecha de compra</th>
  <th>Símbolo</th>
  <th>Número de acciones</th>
  <th>Precio de compra</th>
  </tr>`;
  
  console.log(responseJson);

  for(let i = 0; i<responseJson.length; i++){
      const row = table.insertRow(); 
      responseJson[i].date = formatDate(responseJson[i].date);
      const values = [responseJson[i].id, responseJson[i].date, responseJson[i].ticker, responseJson[i].quantity, responseJson[i].price];

      for(let j = 0; j<values.length; j++){
          const cell = row.insertCell();
          cell.innerHTML = values[j];
      }
  }
}



// Add this function to handle the upload button's state
function handleFileUploadButtonState() {
    const fileInput = document.getElementById("file-upload");
    const uploadButton = document.getElementById("upload-file-button");
  
    fileInput.addEventListener("change", function () {
      if (fileInput.files.length > 0 && fileInput.files[0].type === "text/csv") {
        uploadButton.disabled = false;
      } else {
        uploadButton.disabled = true;
      }
    });
  }
  
  // Add this function to handle file upload
async function uploadFile() {
    const fileInput = document.getElementById("file-upload");
  
    const formData = new FormData();
    formData.append("file", fileInput.files[0]);
  
    try {
      const response = await fetch(url_upload_file, {
        method: "POST",
        body: formData,
      });
  
      if (!response.ok) {
        const errorText = await response.text();
        throw new Error(errorText);
      } else {
        alert("Archivo subido con éxito");
        fetchTable(); // Reload the table after a successful file upload
      }
    } catch (error) {
      alert("Error al subir el archivo: " + error.message);
    }
  }
  
  
  // Add this function to handle file download
  function downloadFile() {
    window.location.href = url_download_file;
  }
  
  // Add event listeners to the upload and download buttons
  function handleFileButtons() {
    const uploadButton = document.getElementById("upload-file-button");
    const downloadButton = document.getElementById("download-file-button");
  
    uploadButton.addEventListener("click", uploadFile);
    downloadButton.addEventListener("click", downloadFile);
  }


async function fetchTable() {
    if (serverUP) {
        await fetch(url_fetch_portfolio,
            { method: 'GET' } )
        .then((response) => {
            console.log(response);
            if (response.ok) {
            return response.json();
            }
            throw new Error(response.statusText);
        })
        .then((responseJson) => {
            fillTable(responseJson);
        })
        .catch((error) => {
            console.log(error)
        });
    }
}

async function postPortfolioMovement(symbol, date, quantity, price) {
    try {
        const response = await fetch(url_movement, {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: `{
                "ticker": "${symbol}",
                "date": "${date}",
                "quantity": ${quantity},
                "price": ${price},
                "userId": ${userID}
            }`,
        });

        if (!response.ok) {
            const errorText = await response.text();
            throw new Error(errorText);
        }
    } catch (error) {
        return error.message;
    }
    return null;
}



function displayFoundMovement(movement) {
    const symbolInput = document.getElementById("form-search-symbol");
    const dateInput = document.getElementById("form-search-date");
    const quantityInput = document.getElementById("form-search-quantity");
    const priceInput = document.getElementById("form-search-price");
  
    symbolInput.value = movement.ticker;
    dateInput.value = movement.date;
    quantityInput.value = movement.quantity;
    priceInput.value = movement.price;
  
    document.getElementById("found-movement").style.display = "block";
    document.getElementById("modify-button").style.display = "inline";
    document.getElementById("delete-button").style.display = "inline";
  }
  
  async function handleModifyMovement(movementID) {
    const symbolInput = document.getElementById("form-search-symbol");
    const dateInput = document.getElementById("form-search-date");
    const quantityInput = document.getElementById("form-search-quantity");
    const priceInput = document.getElementById("form-search-price");
  
    symbolInput.readOnly = !symbolInput.readOnly;
    dateInput.readOnly = !dateInput.readOnly;
    quantityInput.readOnly = !quantityInput.readOnly;
    priceInput.readOnly = !priceInput.readOnly;
  
    if (!symbolInput.readOnly) {
      document.getElementById("modify-button").innerText = "Guardar";
    } else {
      document.getElementById("modify-button").innerText = "Modificar";
  
      try {
        const response = await fetch(`${url_movement}/update`, {
          method: "POST",
          headers: {
            "Accept": "application/json",
            "Content-Type": "application/json",
          },
          body: JSON.stringify({
            id: movementID,
            ticker: symbolInput.value,
            date: dateInput.value,
            quantity: quantityInput.value,
            price: priceInput.value,
            userId: userID,
          }),
        });
  
        if (!response.ok) {
          const errorText = await response.text();
          throw new Error(errorText);
        }
  
        alert("Movimiento modificado con éxito");
        fetchTable();
      } catch (error) {
        alert("Error al modificar el movimiento: " + error.message);
      }
    }
  }
  
  async function handleDeleteMovement(movementID) {
    if (confirm("¿Estás seguro de que deseas eliminar este movimiento?")) {
      try {
        const response = await fetch(`${url_movement}/${movementID}`, {
          method: "DELETE",
        });
  
        if (!response.ok) {
          const errorText = await response.text();
          throw new Error(errorText);
        }
  
        alert("Movimiento eliminado con éxito");
        fetchTable();
      } catch (error) {
        alert("Error al eliminar el movimiento: " + error.message);
      }
    }
  }
  

  function handleSearchButton() {
    const searchButton = document.getElementById("form-search-submit");
    const searchInput = document.getElementById("form-search-id");
  
    searchButton.addEventListener("click", async function () {
      const movementID = searchInput.value;
  
      try {
        url_fetch_movements = `${url_movement}/${movementID}`;
        const response = await fetch(url_fetch_movements);
  
        if (!response.ok) {
          const errorText = await response.text();
          throw new Error(errorText);
          }  
          const movementData = await response.json();
          displayMovementData(movementData);
          handleModifyButton(movementID);
          handleDeleteButton(movementID);
        
        } catch (error) {
          alert("Error al buscar movimiento: " + error.message);
        }
        

    });
}

function displayMovementData(movementData) {
    const symbolInput = document.getElementById("form-search-symbol");
    const dateInput = document.getElementById("form-search-date");
    const quantityInput = document.getElementById("form-search-quantity");
    const priceInput = document.getElementById("form-search-price");
  
    symbolInput.value = movementData.ticker;
    dateInput.value = movementData.date;
    quantityInput.value = movementData.quantity;
    priceInput.value = movementData.price;
  
    document.getElementById("found-movement").style.display = "block";
    document.getElementById("modify-button").style.display = "inline";
    document.getElementById("delete-button").style.display = "inline";
  }
  
  function handleModifyButton(movementID) {
    const modifyButton = document.getElementById("modify-button");
    modifyButton.addEventListener("click", () => handleModifyMovement(movementID));
  }
  
  function handleDeleteButton(movementID) {
    const deleteButton = document.getElementById("delete-button");
    deleteButton.addEventListener("click", () => handleDeleteMovement(movementID));
  }
  






function handleFormSubmit() {
    // listener in the form submit button
    const submitButton = document.getElementById("form-portfolio-submit");
    const form = document.getElementById("form-portfolio");
    submitButton.addEventListener("click", async function (event) {
        if (!form.checkValidity()) {
            return false;
        }

        event.preventDefault();
        event.stopPropagation();

        const symbol = document.getElementById("form-portfolio-symbol").value;
        const date = document.getElementById("form-portfolio-date").value;
        const quantity = document.getElementById("form-portfolio-quantity").value;
        const price = document.getElementById("form-portfolio-price").value;

        const error = await postPortfolioMovement(symbol, date, quantity, price);
        if (error) {
            alert(error);
        } else {
            fetchTable();
        }
    });
}


function handleServerDown() {
    serverUP = false;
    document.getElementById("server-down").style.display = "inline";
    document.getElementById("text-server-status").innerHTML = "Server status: DOWN";
    document.getElementById("pulse-server-status").classList.add("red");

}

function handleServerUp() {
    serverUP = true;
    document.getElementById("server-up").style.display = "inline";
    document.getElementById("text-server-status").innerHTML = "Server status: UP";
    document.getElementById("pulse-server-status").classList.add("green");

    

}

async function handleServerHealth() {
    await fetch("http://localhost:8080/actuator/health")
    .then((response) => {
        console.log(response);
        if (response.ok) {
            return response.json();
        }
        throw new Error(response.statusText);
    })
    .then((responseJson) => {
        if (responseJson.status === "UP") {
            handleServerUp();
        }
        else {
            handleServerDown();
        }
    })
    .catch((error) => {
        handleServerDown();
        console.log(error)
    });
}


async function init() {
    await handleServerHealth();
    handleFormSubmit();
    fetchTable();
    handleFileUploadButtonState();
    handleFileButtons();
    handleSearchButton();


}

init();



