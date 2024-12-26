document.addEventListener('DOMContentLoaded', function() {
    const maxPlayers = 4;
    const minPlayers = 2;
    const playersForm = document.querySelector('.players-form');
    const addPlayerButton = document.getElementById('addplayer');
    const removePlayerButton = document.getElementById('removeplayer');
    document.getElementById('registrationB1').disabled = true;

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
                    <button class="registeredplayerbuttons"></button>
                    <label class="registeredplayerlabels">Registrato</label>
                </div>
            `;
            playersForm.appendChild(newInputGroup);
            updateButtons();
        }
    });

    removePlayerButton.addEventListener('click', function() {
        const currentPlayers = playersForm.querySelectorAll('.input-group').length;
        if (currentPlayers > minPlayers) {
            playersForm.removeChild(playersForm.lastElementChild);
            updateButtons();
        }
    });

    function toggleRegistration(button) {
        if (button.innerText === 'X') {
            button.innerText = ''; // Rimuove la X
        } else {
            button.innerText = 'X'; // Aggiunge la X
        }
    }

    const initialButtons = playersForm.querySelectorAll('.registeredplayerbuttons');
    initialButtons.forEach(function(button) {
        button.addEventListener('click', function() {
            event.preventDefault()
            toggleRegistration(button);
        });
    });

    updateButtons();
});
