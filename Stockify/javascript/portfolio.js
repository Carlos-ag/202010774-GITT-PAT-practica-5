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
var url_add_movement = "http://localhost:8080/movement";
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
    <th>Fecha de compra</th>
    <th>Símbolo</th>
    <th>Número de acciones</th>
    <th>Precio de compra</th>
    </tr>`;
    
    console.log(responseJson);

    for(let i = 0; i<responseJson.length; i++){
        const row = table.insertRow(); 
        responseJson[i].date = formatDate(responseJson[i].date);
        const values = [responseJson[i].date, responseJson[i].ticker, responseJson[i].quantity, responseJson[i].price];

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
        const response = await fetch(url_add_movement, {
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

}

init();



