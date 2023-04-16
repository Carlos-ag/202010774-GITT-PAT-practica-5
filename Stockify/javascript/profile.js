$(document).ready(function() {
    fetchUserData();
    fetchContactMessages();

    $('#edit-button').on('click', editUserInfo);
    $('#back-button').on('click', goBackToMessagesList);
});

function fetchUserData() {
    // Replace with a call to your API to fetch user data using the user token
    const userData = {
        name: 'John',
        surname: 'Doe',
        phone: '+1 (555) 123-4567',
        email: 'john.doe@example.com',
        subscription: 'Premium'
    };

    // Populate the form with the user data
    $('#name').val(userData.name);
    $('#surname').val(userData.surname);
    $('#phone').val(userData.phone);
    $('#email').val(userData.email);
    $('#subscription').val(userData.subscription);
}

function fetchContactMessages() {
    // Replace with a call to your API to fetch contact messages using the user token
    const messages = [        { id: 1, subject: 'Billing Issue' },        { id: 2, subject: 'Technical Support' },        { id: 3, subject: 'Feature Request' }    ];

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
    const fields = ['name', 'surname', 'phone', 'email', 'subscription'];
    fields.forEach(field => {
        const input = $(`#${field}`);
        input.prop('readonly', !input.prop('readonly'));
    });
}

function displayChat(messageId) {
    // Replace with a call to your API to fetch the chat data for the selected message
    const chatData = [        { sender: 'user', text: 'Hello, I have a problem with my account.' },        { sender: 'support', text: 'Hi, how can we help you?' },        { sender: 'user', text: 'I cannot access my account.' },        { sender: 'support', text: 'Please try resetting your password.' }    ];

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
    $('#chat').show();
}

function goBackToMessagesList() {
    // Show the messages list and hide the chat
    $('#chat').hide();
    $('#messages-list').show();
}
