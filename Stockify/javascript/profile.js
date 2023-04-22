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

async function init() {
    console.log("init");
    await fetchUserData();
    fetchContactMessages();
    $('#edit-button').on('click', toggleEditSave);
    $('#back-button').on('click', goBackToMessagesList);
    $('#new-message-button').on('click', startNewMessage);
    $('#send-message').on('click', sendMessage);
}



var currentConversationId = null;

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
    const subscriptionPlansPromise = fetchSubscriptionPlans();
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

        await subscriptionPlansPromise; // Wait for the subscription plans to be fetched

    } catch (error) {
        console.error('Error fetching user data:', error);
    }
}


async function fetchContactMessages() {
    const userId = getUserIdFromCookie();
    const apiUrl = `http://localhost:8080/signedMessages/latest/${userId}`;

    try {
        const response = await fetch(apiUrl);
        const messages = await response.json();

        // Add messages to the list
        messages.forEach(message => {
            const listItem = $('<li></li>')
                .addClass('list-group-item')
                .text(message.message)
                .data('conversation-id', message.conversationId)
                .on('click', function () {
                    displayChat($(this).data('conversation-id'));
                });
            $('#messages-list ul').append(listItem);
        });
        
    } catch (error) {
        console.error('Error fetching contact messages:', error);
    }
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


async function displayChat(conversationId) {
    currentConversationId = conversationId;
    const apiUrl = `http://localhost:8080/signedMessages/${conversationId}`;

    try {
        const response = await fetch(apiUrl);
        const chatData = await response.json();

        // Populate the chat content
        $('#chat-content').empty();
        chatData.forEach(message => {
            const messageDiv = $('<div></div>').addClass(`message ${message.user.id !== getUserIdFromCookie() ? 'user' : 'support'}`);
            const messageText = $('<div></div>').addClass('message-text').text(message.message);
            messageDiv.append(messageText);
            $('#chat-content').append(messageDiv);
        });
    } catch (error) {
        console.error('Error fetching chat data:', error);
    }

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

    // Fetch the latest messages and add them to the list
    $('#messages-list ul').empty(); // clear the previous messages
    fetchContactMessages();
}


async function fetchLastConversationId() {
    const apiUrl = 'http://localhost:8080/lastConversationId';
    try {
        const response = await fetch(apiUrl);
        const conversationId = await response.json();
        return conversationId;
    } catch (error) {
        console.error('Error fetching last conversation id:', error);
        return null;
    }
}

async function startNewMessage() {
    currentConversationId = null;
    // Show the chat and hide the messages list
    $('#messages-list').hide();
    $('#new-message-button').hide(); // Hide the New Message button
    $('#chat').show();
    $('#chat-content').empty(); // Clear the chat content
    $('#message-input').show(); // Show the message input
}

function addMessageToChat(messageText, isUser) {
    const messageDiv = $('<div></div>').addClass(`message ${isUser ? 'user' : 'support'}`);
    const messageTextElement = $('<div></div>').addClass('message-text').text(messageText);
    messageDiv.append(messageTextElement);
    $('#chat-content').append(messageDiv);
}


async function sendMessage() {
    const messageText = $('#message-input input').val().trim();
    if (messageText) {
        // Fetch the conversationId for the new conversation just before sending the message
        if (!currentConversationId) {
            const conversationId = await fetchLastConversationId();
            if (conversationId) {
                currentConversationId = conversationId + 1;
            } else {
                console.error('Error sending a message: could not fetch conversationId');
                return;
            }
        }

        const userId = getUserIdFromCookie();
        const apiUrl = 'http://localhost:8080/signedMessages';
        const signedMessage = {
            conversationId: currentConversationId,
            message: messageText,
            userId: userId
        };
        
        try {
            await fetch(apiUrl, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(signedMessage)
            });
            console.log('Message sent:', messageText);
        } catch (error) {
            console.error('Error sending message:', error);
        }

        // Add the new message to the chat as a user message
        addMessageToChat(messageText, true);

        // Clear the input field
        $('#message-input input').val('');
    }
}




init();

document.addEventListener('DOMContentLoaded', async () => {
    await init();
});