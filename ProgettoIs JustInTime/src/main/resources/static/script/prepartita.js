document.addEventListener('DOMContentLoaded', function() {
    const maxPlayers = 4;
    const minPlayers = 2;
    const playersForm = document.querySelector('.players-form');
    const addPlayerButton = document.getElementById('addplayer');
    const removePlayerButton = document.getElementById('removeplayer');
    document.getElementById('registrationB1').disabled = true;
    const newGameButton = document.getElementById('initbutton');
    const errorMessage = document.getElementById('error-message-start');
    let number=0;

    // Impostare il nome del giocatore 1 dalla sessione
    fetch('/utenze/trovaUtenza', {
        method: 'GET',
        credentials: 'include'
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Errore nel recupero dei dati giocatore.');
            }
            return response.json();
        })
        .then(data => {
            document.getElementById("player1").value = data.username || "Username_N/A";
        })
        .catch(error => console.error('Errore:', error));

    // Funzione per aggiornare i pulsanti di aggiunta e rimozione dei giocatori
    function updateButtons() {
        const currentPlayers = playersForm.querySelectorAll('.input-group').length;
        addPlayerButton.disabled = currentPlayers >= maxPlayers;
        removePlayerButton.disabled = currentPlayers <= minPlayers;
    }

    // Funzione per aggiungere un giocatore
    addPlayerButton.addEventListener('click', function() {
        const currentPlayers = playersForm.querySelectorAll('.input-group').length;
        if (currentPlayers < maxPlayers) {
            const newPlayerNumber = currentPlayers + 1;
            const newInputGroup = document.createElement('div');
            newInputGroup.classList.add('input-group');
            newInputGroup.innerHTML = `
                <label for="player${newPlayerNumber}">Giocatore ${newPlayerNumber}</label>
                <div class="input-selections">
                    <input type="text" id="player${newPlayerNumber}" placeholder="Username" readonly>
                    <button class="registeredplayerbuttons" id="registrationB${newPlayerNumber}">Effettua il login</button>
                </div>
            `;
            playersForm.appendChild(newInputGroup);
            updateButtons();
            attachEventListeners(); // Aggiungi event listener per i nuovi pulsanti
        }
    });

    // Funzione per rimuovere un giocatore
    removePlayerButton.addEventListener('click', function() {
        const currentPlayers = playersForm.querySelectorAll('.input-group').length;
        if (currentPlayers > minPlayers) {
            playersForm.removeChild(playersForm.lastElementChild);
            updateButtons();
        }
    });

    // Funzione per aprire il modal di login
    function openLoginModal(playerId) {
        resetLoginForm(); // Reset dei campi del form
        document.getElementById('login-modal').style.display = 'block';
        document.getElementById('login-form').dataset.player = playerId;
    }

    // Funzione per toggle del login
    function toggleRegistration(button) {
        if (button.innerText === 'Login effettuato!') {
            button.innerText = '';
        } else {
            const playerId = button.dataset.player;
            openLoginModal(playerId);
        }
    }

    // Aggiungi event listener ai pulsanti di login
    function attachEventListeners() {
        const buttons = playersForm.querySelectorAll('.registeredplayerbuttons');
        buttons.forEach(function(button) {
            button.addEventListener('click', function(event) {
                event.preventDefault();
                number=button.getAttribute('name');
                toggleRegistration(button);
            });
        });
    }

    // Funzione per resettare il form di login
    function resetLoginForm() {
        document.getElementById('username').value = '';
        document.getElementById('password').value = '';
        document.getElementById('error-message').style.display = 'none';
    }

    attachEventListeners();
    updateButtons();

    // Chiudi il modal
    document.getElementById('close-modal').addEventListener('click', function() {
        document.getElementById('login-modal').style.display = 'none';
    });

    // Gestisci il submit del form di login
    document.getElementById('login-form').addEventListener('submit', function(event) {
        event.preventDefault();

        const username = document.getElementById('username').value;
        const password = document.getElementById('password').value;

        // Invia la richiesta al backend per il login del giocatore
        fetch('/api/game-config/add-player-login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: new URLSearchParams({
                usernameOrEmail: username,
                password: password
            })
        })
            .then(response => response.json())
            .then(data => {
                if (data.message === "Login e aggiunta giocatore effettuati con successo") {
                    // Login riuscito, aggiorna il nome del giocatore
                    document.getElementById('player' + number).value = username;
                    document.querySelector(`#registrationB${number}`).textContent = 'Login effettuato!';
                    document.getElementById('login-modal').style.display = 'none';
                    document.getElementById('player' + number).value = username;

                } else {
                    // Login fallito
                    document.getElementById('error-message').textContent = data.message;
                    document.getElementById('error-message').style.display = 'block';
                }
            })
            .catch(error => {
                console.error('Errore:', error);
                document.getElementById('error-message').textContent = 'Nome utente o password errati';
                document.getElementById('error-message').style.display = 'block';
            });
    });

    // Chiudi la finestra di dialogo quando si clicca fuori
    window.onclick = function(event) {
        if (event.target === document.getElementById('login-modal')) {
            document.getElementById('login-modal').style.display = 'none';
        }
    }

    // Inizia la partita (controlla se tutti i giocatori sono registrati)
    newGameButton.addEventListener('click', function(event) {
        const visibleButtons = playersForm.querySelectorAll('.input-group .registeredplayerbuttons');
        let allRegistered = true;

        visibleButtons.forEach(function(button) {
            if (button.innerText !== 'Login effettuato!') {
                allRegistered = false;
            }
        });

        if (!allRegistered) {
            event.preventDefault();
            errorMessage.textContent = 'Tutti i giocatori devono essere registrati.';
            errorMessage.style.display = 'block';
        }
    });
});
