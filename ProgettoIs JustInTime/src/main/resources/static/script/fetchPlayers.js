document.addEventListener('DOMContentLoaded', function() {
    const newGameButton = document.getElementById('initbutton');
    if (newGameButton) {
        newGameButton.addEventListener('click', function(event) {
            // Chiamata per ottenere il primo giocatore dalla sessione
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
                    // Aggiungi il primo giocatore all'array
                    playersPreGame.push(data.username);

                    // Chiamata per ottenere gli altri giocatori
                    return fetch('/api/game-config/players', {
                        method: 'GET',
                        credentials: 'include',
                    });
                })
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Errore nel recupero della lista di giocatori');
                    }
                    return response.json();
                })
                .then(usernames => {
                    // Aggiungi gli altri giocatori all'array
                    for (let i = 0; i < usernames.length; i++) {
                        playersPreGame.push(usernames[i]);
                    }
                    console.log('Lista dei giocatori:', playersPreGame);

                    // Salva l'array in localStorage
                    localStorage.setItem('playersPreGame', JSON.stringify(playersPreGame));
                })
                .catch(error => console.error('Errore:', error));
        });
    }
});

let playersPreGame = [];
