// When the login button is clicked
document.querySelector('button[type="submit"]').addEventListener('click', function(event) {
    // Prevent the form from submitting normally
    event.preventDefault();

    // Get the user input values
    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;

    // Make an API request to authenticate the user
    // CAMBIAR CARLOS TODO:!!!!!
    fetch('/api/authenticate', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            email: email,
            password: password
        })
    })
    .then(response => {
        // If the response is successful
        if (response.ok) {
            // Get the userID cookie from the response
            const cookie = response.headers.get('set-cookie');
            const userID = cookie.split(';')[0].split('=')[1];

            // Set the userID cookie
            document.cookie = `userID=${userID}; path=/`;

            // Redirect to the home page
            window.location.href = '/home';
        } else {
            // Display an error message on the screen
            const errorMessage = document.createElement('div');
            errorMessage.classList.add('alert', 'alert-danger');
            errorMessage.textContent = 'Invalid email or password. Please try again.';
            document.querySelector('.container-form').appendChild(errorMessage);
        }
    })
    .catch(error => console.error('Error:', error));
});
