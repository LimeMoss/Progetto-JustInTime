document.addEventListener('DOMContentLoaded', function() {
    const playAgainButton = document.getElementById("playagainbutton");

    playAgainButton.addEventListener('click', function() {
        // Verifica se l'utente è loggato
        fetch('/utenze/trovaUtenza', {
            method: 'GET',
            credentials: 'include',
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Sessione scaduta o utente non loggato.');
                }
                return response.json();
            })
            .then(data => {
                // Se l'utente è loggato, fai la richiesta per giocare di nuovo
                fetch('/api/game-config/play-again', {
                    method: 'POST',
                    credentials: 'include',
                })
                    .then(response => {
                        if (!response.ok) {
                            throw new Error('Errore durante l\'avvio di una nuova partita.');
                        }
                        return response.json();
                    })
                    .then(data => {
                        console.log('Nuova partita avviata:', data);
                        window.location.href = '/startmatch';
                    })
                    .catch(error => {
                        console.error('Errore:', error);
                        alert('Si è verificato un errore durante l\'avvio della nuova partita.');
                    });
            })
            .catch(error => {
                console.error('Errore:', error);
                alert('Devi essere loggato per avviare una nuova partita.');
            });
    });
    updateRanking();
});

    function updateRanking() {
        fetch("/api/game-config/players")
            .then(response => {
                if (!response.ok) {
                    throw new Error("Errore durante il recupero dei dati dei giocatori.");
                }
                return response.json();
            })
            .then(players => {
                 //prendere il numero di carte

                playerData.sort((a, b) => a.cards - b.cards);

                renderRanking(playerData);
            })
            .catch(error => {
                console.error("Errore:", error);
            });
    }

    function renderRanking(players) {
        players.forEach(player => {
            const playerContainer = document.getElementById(`name${players.findIndex(player)}`);
        });
    }