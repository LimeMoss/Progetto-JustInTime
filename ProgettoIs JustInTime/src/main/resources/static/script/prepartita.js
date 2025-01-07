document.addEventListener('DOMContentLoaded', function() {
    const maxPlayers = 4;
    const minPlayers = 1;
    const playersForm = document.querySelector('.players-form');
    const addPlayerButton = document.getElementById('addplayer');
    const removePlayerButton = document.getElementById('removeplayer');
    document.getElementById('registrationB1').disabled = true;
    const newGameButton = document.getElementById('initbutton');
    const errorMessage = document.getElementById('error-message-start');
    let currentPlayerId = 0;

    // Imposta il nome del giocatore 1 dalla sessione
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

    // Funzione per aggiornare i pulsanti di aggiunta e rimozione giocatori
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
                    <input type="text" id="player${newPlayerNumber}" name="player${newPlayerNumber}" placeholder="Username" readonly>
                    <button class="registeredplayerbuttons" id="registrationB${newPlayerNumber}">Effettua il login</button>
                </div>
            `;
            playersForm.appendChild(newInputGroup);
            updateButtons();
            attachEventListeners(); // Aggiunge event listener per i nuovi pulsanti
        }
    });

    // Funzione per rimuovere un giocatore
    removePlayerButton.addEventListener('click', function() {
        const currentPlayers = playersForm.querySelectorAll('.input-group').length;

        if (currentPlayers > minPlayers) {
            fetch(`/api/game-config/remove-player`, {
                method: 'DELETE',
                credentials: 'include',
            })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Errore durante la rimozione del giocatore.');
                }
                return response.text();
            })
            .then(removedUsername => {
                console.log('Giocatore rimosso:', removedUsername);

                // Trova il gruppo da rimuovere
                const playerToRemove = Array.from(playersForm.querySelectorAll('.input-group')).find(group => {
                    const input = group.querySelector('input[type="text"]');
                    return input && input.value === removedUsername;
                });

                if (playerToRemove) {
                    playersForm.removeChild(playerToRemove);
                    shiftPlayers(); // Aggiorna gli ID e le etichette
                    updateButtons(); // Aggiorna i pulsanti
                }
            })
            .catch(error => console.error('Errore:', error));
        }
    });

    // Funzione per aggiornare etichette, ID e stati dei giocatori
    function shiftPlayers() {
        const playerGroups = Array.from(playersForm.querySelectorAll('.input-group'));

        playerGroups.forEach((group, index) => {
            const newPlayerNumber = index + 1;
            const label = group.querySelector('label');
            const input = group.querySelector('input');
            const button = group.querySelector('button');

            // Aggiorna etichetta, ID e nome
            label.textContent = `Giocatore ${newPlayerNumber}`;
            input.id = `player${newPlayerNumber}`;
            input.name = `player${newPlayerNumber}`;
            button.id = `registrationB${newPlayerNumber}`;

            // Reinizializza campi vuoti
            if (!input.value || input.value.trim() === "") {
                input.value = "";
                button.textContent = "Effettua il login";
                button.disabled = false;
            }
        });
    }

    // Funzione per aprire il modal di login
    function openLoginModal(playerId) {
        resetLoginForm(); // Resetta il form del modal
        document.getElementById('login-modal').style.display = 'block';
        document.getElementById('login-form').dataset.player = playerId;
    }

    // Toggle del login
    function toggleRegistration(button) {
        if (button.innerText !== "Login effettuato!") {
            const playerId = button.id.replace("registrationB", "");
            openLoginModal(playerId);
        }
    }

    // Aggiunge event listener ai pulsanti di login
    function attachEventListeners() {
        const buttons = playersForm.querySelectorAll('.registeredplayerbuttons');
        buttons.forEach(button => {
            button.addEventListener('click', function(event) {
                event.preventDefault();
                currentPlayerId = button.id.replace("registrationB", "");
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

    // Chiude il modal di login
    document.getElementById('close-modal').addEventListener('click', function() {
        document.getElementById('login-modal').style.display = 'none';
    });

    // Gestisce il submit del form di login
    document.getElementById('login-form').addEventListener('submit', function(event) {
        event.preventDefault();

        const username = document.getElementById('username').value;
        const password = document.getElementById('password').value;

        fetch('/api/game-config/add-player-login', {
            method: 'POST',
            headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
            body: new URLSearchParams({ usernameOrEmail: username, password: password })
        })
        .then(response => response.json())
        .then(data => {
            if (data.message === "Login e aggiunta giocatore effettuati con successo") {
                const input = document.querySelector(`#player${currentPlayerId}`);
                const button = document.querySelector(`#registrationB${currentPlayerId}`);

                input.value = username;
                button.textContent = "Login effettuato!";
                button.disabled = true;

                document.getElementById('login-modal').style.display = 'none';
            } else {
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

    // Inizia la partita
    newGameButton.addEventListener('click', function(event) {
        const visibleButtons = playersForm.querySelectorAll('.input-group .registeredplayerbuttons');
        let registeredPlayersCount = 0;

        visibleButtons.forEach(button => {
            if (button.innerText === "Login effettuato!") {
                registeredPlayersCount++;
            }
        });



        if (registeredPlayersCount <= minPlayers || registeredPlayersCount > maxPlayers) {
            event.preventDefault();
            errorMessage.textContent = 'È necessario avere almeno 2 e massimo 4 giocatori registrati per avviare la partita.';
            errorMessage.style.display = 'block';
            
        }  else {
            errorMessage.style.display = 'none';
        
            fetch('/api/game-config/create-and-start', {
                method: 'POST',
                credentials: 'include'
            })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Errore durante la creazione della partita.');
                }
                return response.text();
            })
            .then(responseText => {
                if (responseText === 'success') {
                    window.location.href = '/startmatch'; // Reindirizzamento alla pagina della partita
                }
            })
            .catch(error => {
                console.error('Errore:', error);
                errorMessage.textContent = 'Errore durante l\'avvio della partita.';
                errorMessage.style.display = 'block';
            });
        }
    });

    attachEventListeners();
    updateButtons();
    
    window.onbeforeunload = function() {

        fetch('/resetIsPageOpen', {
            method: 'POST',
            credentials: 'include'  
        })
        .then(response => {
            if (response.ok) {
                console.log('IsPageOpen resettato nel backend');
            }
        })
        .catch(error => {
            console.error('Errore nel resettare IsPageOpen nel backend:', error);
        });
    
        sessionStorage.setItem("IsPageOpen", "false"); 
    };
});

   