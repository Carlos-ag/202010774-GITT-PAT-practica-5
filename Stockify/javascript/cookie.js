// module.js

// Función para establecer una cookie
function setCookie(name, value, days) {
    let expires = "";
    if (days) {
        let date = new Date();
        date.setTime(date.getTime() + (days * 24 * 60 * 60 * 1000));
        expires = "; expires=" + date.toUTCString();
    }
    document.cookie = name + "=" + (value || "") + expires + "; path=/";
}

// Función para obtener una cookie
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

// Función establecer una cookie de userId
function saveUserIdInCookie(userId) {

    const cookieName = "userId";
    const cookieDurationInDays = 30;

    setCookie(cookieName, userId, cookieDurationInDays);
}

// Función para obtener el userId de la cookie
function getUserIdFromCookie() {
    return 1;
    // return getCookie("userId");
}

export { setCookie, getCookie, saveUserIdInCookie, getUserIdFromCookie };
