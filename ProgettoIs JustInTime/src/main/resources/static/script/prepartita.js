document.addEventListener('DOMContentLoaded', function() {
    const maxPlayers = 4;
    const minPlayers = 2;
    const playersForm = document.querySelector('.players-form');
    const addPlayerButton = document.getElementById('addplayer');
    const removePlayerButton = document.getElementById('removeplayer');
    document.getElementById('registrationB1').disabled = true;
    const newGameButton = document.getElementById('initbutton');
    const errorMessage = document.getElementById('error-message-start');

    function updateButtons() {
        const currentPlayers = playersForm.querySelectorAll('.input-group').length;
        addPlayerButton.disabled = currentPlayers >= maxPlayers;
        removePlayerButton.disabled = currentPlayers <= minPlayers;
    }

    addPlayerButton.addEventListener('click', function() {
        const currentPlayers = playersForm.querySelectorAll('.input-group').length;
        if (currentPlayers < maxPlayers) {
            const newPlayerNumber = currentPlayers + 1;
            const newInputGroup = document.createElement('div');
            newInputGroup.classList.add('input-group');
            newInputGroup.innerHTML = `
                <label for="player${newPlayerNumber}">Giocatore ${newPlayerNumber}</label>
                <div class="input-selections">
                    <input type="text" id="player${newPlayerNumber}" placeholder="Username">
                    <button class="registeredplayerbuttons" data-player="${newPlayerNumber}">Registrato</button>
                    <label class="registeredplayerlabels">Registrato</label>
                </div>
            `;
            playersForm.appendChild(newInputGroup);
            updateButtons();
            attachEventListeners(); // Attach event listeners to new buttons
        }
    });

    removePlayerButton.addEventListener('click', function() {
        const currentPlayers = playersForm.querySelectorAll('.input-group').length;
        if (currentPlayers > minPlayers) {
            playersForm.removeChild(playersForm.lastElementChild);
            updateButtons();
        }
    });

    function openLoginModal(playerId) {
        resetLoginForm(); // Reset the form fields
        document.getElementById('login-modal').style.display = 'block';
        document.getElementById('login-form').dataset.player = playerId;
    }

    function toggleRegistration(button) {
        if (button.innerText === 'X') {
            button.innerText = ''; // Rimuove la X
        } else {
            const playerId = button.dataset.player;
            openLoginModal(playerId); // Apre la finestra di dialogo solo se la X non è presente
        }
    }

    function attachEventListeners() {
        const buttons = playersForm.querySelectorAll('.registeredplayerbuttons');
        buttons.forEach(function(button) {
            button.addEventListener('click', function(event) {
                event.preventDefault();
                toggleRegistration(button);
            });
        });
    }

    function resetLoginForm() {
        document.getElementById('username').value = '';
        document.getElementById('password').value = '';
        document.getElementById('error-message').style.display = 'none';
    }

    attachEventListeners();
    updateButtons();

    // Chiudi la finestra di dialogo
    document.getElementById('close-modal').addEventListener('click', function() {
        document.getElementById('login-modal').style.display = 'none';
    });

    // Gestisci il submit del form di login
    document.getElementById('login-form').addEventListener('submit', function(event) {
        event.preventDefault();

        const username = document.getElementById('username').value;
        const password = document.getElementById('password').value;
        const email = "default@example.com"; // Assume an email for now
        const playerId = this.dataset.player;

        // Invia la richiesta al backend
        fetch('/auth/register', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ username, password, email })
        })
            .then(response => response.json())
            .then(data => {
                if (response.ok) {
                    // Login riuscito
                    document.getElementById('player' + playerId).value = username;
                    document.querySelector(`[data-player="${playerId}"]`).textContent = 'X';
                    document.getElementById('login-modal').style.display = 'none';
                } else {
                    // Login fallito
                    document.getElementById('error-message').textContent = data;
                    document.getElementById('error-message').style.display = 'block';
                }
            })
            .catch(error => {
                console.error('Errore:', error);
                document.getElementById('error-message').textContent = 'An error occurred during registration.';
                document.getElementById('error-message').style.display = 'block';
            });
    });

    // Chiudi la finestra di dialogo quando si clicca fuori di essa
    window.onclick = function(event) {
        if (event.target == document.getElementById('login-modal')) {
            document.getElementById('login-modal').style.display = 'none';
        }
    }

    newGameButton.addEventListener('click', function(event) {
        const visibleButtons = playersForm.querySelectorAll('.input-group .registeredplayerbuttons');
        let allRegistered = true;

        visibleButtons.forEach(function(button) {
            if (button.innerText !== 'X') {
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
