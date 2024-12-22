document.addEventListener('DOMContentLoaded', (event) => {
    const phoneNumberInput = document.querySelector('input[name="phoneNumber"]');
    const emailInput = document.querySelector('input[name="email"]');
    const passwordInput = document.querySelector('input[name="password"]');
    const passwordCheckInput = document.querySelector('input[name="password-check"]');
    const dateInput = document.querySelector('input[name="birth-date"]');
    dateInput.addEventListener('change', validateDate);
    passwordCheckInput.addEventListener('change', validatePasswordCheck);
    passwordInput.addEventListener('change', validatePassword);
    phoneNumberInput.addEventListener('change', validatePhoneNumber);
    emailInput.addEventListener('change', validateEmail);
    const nameInput = document.querySelector('input[name="nome"]');
    const surnameInput = document.querySelector('input[name="cognome"]');
    const usernameInput = document.querySelector('input[name="username"]');
    usernameInput.addEventListener('change', validateUsername);
    nameInput.addEventListener('change', validateName);
    surnameInput.addEventListener('change', validateSurname);

    function formatPhoneNumber() {
        const phoneNumberField = document.getElementById('telefono');
        const value = phoneNumberField.value.replace(/\D/g, '').substring(0, 10);
        phoneNumberField.value = value.replace(/(\d{3})(\d{3})(\d{0,4})/, '$1 $2 $3');
    }

    function formatName() {
        const nameField = document.getElementById('nome');
        nameField.value = value.charAt(0).toUpperCase() + value.slice(1).toLowerCase();
    }

    function formatSurname() {
        const nameField = document.getElementById('cognome');
        nameField.value = value.charAt(0).toUpperCase() + value.slice(1).toLowerCase();
    }

    function validateUsername() {
        const name = usernameInput.value;
        const namePattern = /^[A-z0-9]{2,30}$/;

        // Check if the input is valid
        if (namePattern.test(name)) {
            usernameInput.setCustomValidity('');
        } else {
            usernameInput.setCustomValidity('Inserisci un username valido. (fra 2 e 30 caratteri senza caratteri speciali)');
        }

        usernameInput.reportValidity();
    }

    function validateSurname() {
        const name = surnameInput.value;
        const namePattern = /^[A-zÀ-ù ‘-]{2,30}$/;

        // Check if the input is valid
        if (namePattern.test(name)) {
            surnameInput.setCustomValidity('');
        } else {
            surnameInput.setCustomValidity('Inserisci un cognome valido. (fra 2 e 30 caratteri senza caratteri speciali)');
        }

        surnameInput.reportValidity();
    }

    function validateName() {
        const name = nameInput.value;
        const namePattern = /^[A-zÀ-ù ‘-]{2,30}$/;

        // Check if the input is valid
        if (namePattern.test(name)) {
            nameInput.setCustomValidity('');
        } else {
            nameInput.setCustomValidity('Inserisci un nome valido. (fra 2 e 30 caratteri senza caratteri speciali)');
        }

        nameInput.reportValidity();
    }

    function validatePhoneNumber() {
        const phoneNumber = phoneNumberInput.value;
        const phoneNumberPattern = /^[0-9]{3}\s[0-9]{3}\s[0-9]{4}$/;

        // Check if the input is valid
        if (phoneNumberPattern.test(phoneNumber)) {
            phoneNumberInput.setCustomValidity('');
        } else {
            phoneNumberInput.setCustomValidity('Inserisci un numero di telefono valido. Formato accettato: 111 111 1111.');
        }

        // Check for exact 10 digits without considering formatting
        const digitsOnly = phoneNumber.replace(/\D/g, '');
        if (digitsOnly.length !== 10) {
            phoneNumberInput.setCustomValidity('Il numero di telefono deve contenere esattamente 10 cifre.');
        }

        // If all validations pass
        phoneNumberInput.reportValidity();
    }

    function validateEmail() {
        const email = emailInput.value;
        const emailPattern = /^[A-z0-9._%+-]+@[A-z0-9.-]+\.[A-z]{2}$/;

        // Check if the input is valid
        if (emailPattern.test(email)) {
            emailInput.setCustomValidity('');
        } else {
            emailInput.setCustomValidity('Inserisci un indirizzo email valido.');
        }

        // If all validations pass
        emailInput.reportValidity();
    }

    function validateDate() {
        const dateInput = document.getElementById('datanascita');
        const dateValue = dateInput.value;
        const inputDate = new Date(dateValue);
        const currentDate = new Date();

        // Check if the input date is before the current date
        if (inputDate < currentDate) {
            dateInput.setCustomValidity('');
        } else {
            dateInput.setCustomValidity('Inserisci una data di nascita valida.');
        }

        // If all validations pass
        dateInput.reportValidity();
    }

    function validatePassword() {
        const password = passwordInput.value;
        const passwordPattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;

        // Check if the input is valid
        if (passwordPattern.test(password)) {
            passwordInput.setCustomValidity('');
        } else {
            passwordInput.setCustomValidity('Inserisci una password valida.');
        }

        passwordInput.reportValidity();

    }

    function validatePasswordCheck() {
        const password = passwordInput.value;
        const passwordCheck = passwordCheckInput.value;

        if (password!==passwordCheck)
            passwordCheckInput.setCustomValidity('Le password non corrispondono.');
        else
            passwordCheckInput.setCustomValidity('');

        passwordCheckInput.reportValidity();
    }

    const phoneField = document.getElementById('telefono');
    phoneField.addEventListener('input', formatPhoneNumber);

    const nameField = document.getElementById('nome');
    nameField.addEventListener('input', formatName);

    const surnameField = document.getElementById('cognome');
    surnameField.addEventListener('input', formatSurname);
});
