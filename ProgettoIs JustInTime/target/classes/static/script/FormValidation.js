document.addEventListener('DOMContentLoaded', (event) => {
    const form = document.querySelector('.signin-form');
    const modifyForm = document.querySelector('.modify-form');
    const phoneNumberInput = document.querySelector('input[name="telefono"]');
    const emailInput = document.querySelector('input[name="email"]');
    const passwordInput = document.querySelector('input[name="password"]');
    const passwordCheckInput = document.querySelector('input[name="password2"]');
    const dateInput = document.querySelector('input[name="dataNascita"]');
    const nameInput = document.querySelector('input[name="nome"]');
    const surnameInput = document.querySelector('input[name="cognome"]');
    const usernameInput = document.querySelector('input[name="username"]');
    const prefisso=document.querySelector('select[name="countryCode"]');

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
        nameInput.value = nameInput.value.charAt(0).toUpperCase() + nameInput.value.slice(1).toLowerCase();
    }

    function formatSurname() {
        surnameInput.value = surnameInput.value.charAt(0).toUpperCase() + surnameInput.value.slice(1).toLowerCase();
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
        const surname = surnameInput.value;
        const surnamePattern = /^[A-zÀ-ù ‘-]{2,30}$/;

        // Check if the input is valid
        if (surnamePattern.test(surname)) {
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
        const phoneNumber = document.querySelector('input[name="countryCode"]').value+" "+phoneNumberInput.value;
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
    nameInput.addEventListener('input', formatName);

    const surnameField = document.getElementById('cognome');
    surnameInput.addEventListener('input', formatSurname);

    const formToUse = form ? form : modifyForm;

    formToUse.addEventListener("submit", async function (event) {
        event.preventDefault();
        if (!validateAll()) {
            return; // Esce prima di eseguire il codice asincrono
        }

        const nome = nameInput.value;
        const cognome = surnameInput.value;
        const username = usernameInput.value;
        const telefono = prefisso.value + " " + phoneNumberInput.value;
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
            if(formToUse === modifyForm){
                const response = await fetch(`/utenze/modificautenza?password2=${encodeURIComponent(password2)}`, {
                    method: 'PUT',
                    credentials: 'include',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(utente) // Invia solo l'oggetto "utente" nel corpo
                });

                if (!response.ok) {
                    const errorMessage = await response.text();
                    alert("Errore durante la modifica: " + errorMessage);
                }else{
                    alert("La modifica ha avuto successo.");
                    window.location.href = "/userHomepage";
                }
            }else{
                const response = await fetch(`/registrazione?password2=${encodeURIComponent(password2)}`, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(utente) // Invia solo l'oggetto "utente" nel corpo
                });

                if (!response.ok) {
                    const errorMessage = await response.text();
                    alert("Errore durante la registrazione: " + errorMessage);
                }else{
                    alert("La registrazione ha avuto successo.");
                    window.location.href = "/login";
                }
            }
        } catch (error) {
            alert("Si è verificato un errore"+error);
        }

    });

    function validateAll(){
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
            alert('Per favore, riempi tutti i campi obbligatori.');
        }

        return isValid;
    }


});
