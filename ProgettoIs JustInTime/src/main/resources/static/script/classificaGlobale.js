// Funzione per caricare la classifica globale
async function loadGlobalHighscores() {
    try {
        const response = await fetch('/classifica');
        const players = await response.json();

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

document.addEventListener('DOMContentLoaded', loadGlobalHighscores);
