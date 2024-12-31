document.addEventListener('DOMContentLoaded', (event) => {
    const form = document.querySelector('.signin-form');
    const phoneNumberInput = document.querySelector('input[name="telefono"]');
    const emailInput = document.querySelector('input[name="email"]');
    const passwordInput = document.querySelector('input[name="password"]');
    const passwordCheckInput = document.querySelector('input[name="password2"]');
    const dateInput = document.querySelector('input[name="dataNascita"]');
    const nameInput = document.querySelector('input[name="nome"]');
    const surnameInput = document.querySelector('input[name="cognome"]');
    const usernameInput = document.querySelector('input[name="username"]');

    // Event listeners for validations
    dateInput.addEventListener('change', validateDate);
    passwordCheckInput.addEventListener('change', validatePasswordCheck);
    passwordInput.addEventListener('change', validatePassword);
    phoneNumberInput.addEventListener('change', validatePhoneNumber);
    emailInput.addEventListener('change', validateEmail);
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
        const phoneNumber = document.querySelector('input[name="countryCode"]').value+" "+phoneNumberInput.value+document.querySelector('input[name="countryCode"]').value;
        const phoneNumberPattern = /^\+[0-9]{1,3}\s[0-9]{3}\s[0-9]{3}\s[0-9]{4}$/;

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
        const emailPattern = /^[A-z0-9._%+-]+@[A-z0-9.-]+\.[A-z]{2,4}$/;

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
        const dateInput = document.getElementById('dataNascita');
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

    form.addEventListener("submit", async function (event) {
        // Validazione dei campi obbligatori
        let isValid = true;

        const requiredFields = [
            emailInput,
            passwordInput,
            passwordCheckInput,
            dateInput,
            nameInput,
            surnameInput,
            usernameInput
        ];

        requiredFields.forEach(field => {
            if (!field.value.trim()) {
                isValid = false;
                field.setCustomValidity('Questo campo è obbligatorio');
            } else {
                field.setCustomValidity('');
            }
            field.reportValidity();
        });

        if (!isValid) {
            // Interrompe il flusso se ci sono errori
            event.preventDefault();
            alert('Per favore, riempi tutti i campi obbligatori.');
            return; // Esce prima di eseguire il codice asincrono
        }

        // Se i campi sono validi, prosegue con l'invio asincrono
        event.preventDefault();

        const nome = nameInput.value;
        const cognome = surnameInput.value;
        const username = usernameInput.value;
        const telefono = phoneNumberInput.value;
        const dataNascita = dateInput.value;
        const paese = document.getElementById("country").value;
        const email = emailInput.value;
        const password = passwordInput.value;
        const password2 = passwordCheckInput.value;

        const utente = {
            nome,
            cognome,
            username,
            telefono,
            dataNascita,
            paese,
            email,
            password
        };

        try {
            const response = await fetch('/modifyaccount', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ utente, password2 })
            });

            if (response.ok) {
                window.location.href = "/login";
            } else {
                const errorMessage = await response.text();
                alert("Errore durante la registrazione");
            }
        } catch (error) {
            alert("Si è verificato un errore");
        }
    });


});
