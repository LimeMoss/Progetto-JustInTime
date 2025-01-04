async function loadNazionalita() {
    try {
        const response = await fetch('/utenze/trovaUtenzaPaese');
        const paeseUtenzaDTO = await response.json();

        console.log("Utente trovato:", paeseUtenzaDTO);


        if (paeseUtenzaDTO && paeseUtenzaDTO.paese) {
            const nazionalitaElement = document.getElementById('nazionalita');
            nazionalitaElement.textContent = `Nazionalità: ${paeseUtenzaDTO.paese}`;
        }
    } catch (error) {
        console.error('Errore durante il recupero dei dati dell\'utente:', error);
    }
}

async function loadClassificaLocale() {
    try {
        const response = await fetch('/classifica/locale');
        const players = await response.json();

        console.log("Risposta JSON ricevuta:", players);

        const highscoreList = document.getElementById('highscore-list');
        highscoreList.innerHTML = '';

        players.forEach((player, index) => {
            const playerDiv = document.createElement('div');
            playerDiv.classList.add('highscore-players');

            playerDiv.innerHTML = `
                <p class="position">${index + 1}</p>
                <p class="name">${player.nome}</p>
                <p class="points">${player.maxScore} pt</p> 
            `;

            highscoreList.appendChild(playerDiv);
        });
    } catch (error) {
        console.error('Errore durante il caricamento della classifica:', error);
    }
}

document.addEventListener('DOMContentLoaded', () => {
    loadNazionalita();

    loadClassificaLocale();
});
