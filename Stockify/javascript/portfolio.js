var url_fetch_portfolio = "http://localhost:8080/portfolio";
var url_add_movement = "http://localhost:8080/movement";
var serverUP = false;


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
        responseJson[i].date = (parseInt(responseJson[i].date.substring(8,10)) + 1) + "/" + responseJson[i].date.substring(5,7) + "/" + responseJson[i].date.substring(0,4);
        const values = [responseJson[i].date, responseJson[i].stock.ticker, responseJson[i].quantity, responseJson[i].price];

        for(let j = 0; j<values.length; j++){
            const cell = row.insertCell();
            cell.innerHTML = values[j];
        }
    }

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
                "stock": {
                    "ticker": "${symbol}"
                },
                "date": "${date}",
                "quantity": ${quantity},
                "price": ${price}
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
        if (responseJson.status == "UP") {
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

}

init();



