import { saveUserIdInCookie, getUserIdFromCookie } from "./cookie.js";

$(document).ready(function () {
    fetchUserData();
    fetchContactMessages();

    $('#edit-button').on('click', toggleEditSave);
    $('#back-button').on('click', goBackToMessagesList);
    $('#new-message-button').on('click', startNewMessage);
    $('#send-message').on('click', sendMessage);
});

async function fetchSubscriptionPlans() {
    const apiUrl = 'http://localhost:8080/subscriptionPlans';

    try {
        const response = await fetch(apiUrl);
        const plans = await response.json();

        const subscriptionSelect = $('#subscription');
        plans.forEach(plan => {
            const option = $('<option></option>').val(plan.id).text(plan.name);
            subscriptionSelect.append(option);
        });
    } catch (error) {
        console.error('Error fetching subscription plans:', error);
    }
}

async function fetchUserData() {
    await fetchSubscriptionPlans();
    const userID = getUserIdFromCookie();

    if (!userID) {
        console.error('User ID not found in the cookie');
        return;
    }

    const apiUrl = `http://localhost:8080/users/${userID}`;

    try {
        const response = await fetch(apiUrl);
        const userData = await response.json();

        // Populate the form with the user data
        $('#name').val(userData.name);
        $('#phone').val(userData.phone);
        $('#email').val(userData.email);
        $('#subscription').val(userData.subscriptionPlan.id);

    } catch (error) {
        console.error('Error fetching user data:', error);
    }
}

function fetchContactMessages() {
    // Replace with a call to your API to fetch contact messages using the user token
    const messages = [
        { id: 1, subject: 'Billing Issue' },
        { id: 2, subject: 'Technical Support' },
        { id: 3, subject: 'Feature Request' }
    ];

    // Add messages to the list
    messages.forEach(message => {
        const listItem = $('<li></li>')
            .addClass('list-group-item')
            .text(message.subject)
            .on('click', () => displayChat(message.id));
        $('#messages-list ul').append(listItem);
    });
}

function editUserInfo() {
    const fields = ['name', 'phone', 'subscription'];
    fields.forEach(field => {
        const input = $(`#${field}`);
        input.prop('readonly', !input.prop('readonly'));
    });
}

function toggleEditSave() {
    const isEditMode = $('#edit-button').text().trim() === 'Edit';
    const fields = ['name', 'phone', 'subscription'];

    if (isEditMode) {
        fields.forEach(field => {
            const input = $(`#${field}`);
            input.prop('readonly', false);
        });
        // Make email editable
        $('#email').prop('readonly', false);
        $('#subscription').prop('disabled', false);
        $('#edit-button').html('<i class="fas fa-save"></i> Save');
    } else {
        let dataChanged = false;
        fields.push('email'); // Include email in the fields to check for changes
        fields.forEach(field => {
            const input = $(`#${field}`);
            if (input.val() !== input.data('initial-value')) {
                dataChanged = true;
            }
            input.prop('readonly', true);
        });

        if (dataChanged) {
            // Save data by calling your API here
            console.log('Data changed. Save to API.');
        } else {
            console.log('No changes detected.');
        }
        $('#subscription').prop('disabled', true);
        $('#edit-button').html('<i class="fas fa-edit"></i> Edit');
    }
}


function displayChat(messageId) {

    // Replace with a call to your API to fetch the chat data for the selected message
    const chatData = [
        { sender: 'user', text: 'Hello, I cannot access my account.' },
        { sender: 'support', text: 'Please try resetting your password.' }
    ];

    // Populate the chat content
    $('#chat-content').empty();
    chatData.forEach(message => {
        const messageDiv = $('<div></div>').addClass(`message ${message.sender}`);
        const messageText = $('<div></div>').addClass('message-text').text(message.text);
        messageDiv.append(messageText);
        $('#chat-content').append(messageDiv);
    });

    // Show the chat and hide the messages list
    $('#messages-list').hide();
    $('#new-message-button').hide(); // Hide the New Message button
    $('#chat').show();
    $('#message-input').show(); // Show the message input

}

function goBackToMessagesList() {
    // Show the messages list and hide the chat
    $('#chat').hide();
    $('#message-input').hide(); // Hide the message input
    $('#messages-list').show();
    $('#new-message-button').show(); // Show the New Message button
}

function startNewMessage() {
    // Show the chat and hide the messages list
    $('#messages-list').hide();
    $('#new-message-button').hide(); // Hide the New Message button
    $('#chat').show();
    $('#chat-content').empty(); // Clear the chat content
    $('#message-input').show(); // Show the message input
}

function sendMessage() {
    const messageText = $('#message-input input').val().trim();
    if (messageText) {
        // TODO: Call the API to send the message using the conversation ID and the message text
        console.log('Sending message:', messageText);
        // Clear the input field
        $('#message-input input').val('');
    }

}
