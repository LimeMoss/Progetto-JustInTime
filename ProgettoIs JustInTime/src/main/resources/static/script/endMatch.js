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

    function terminaPartita() {
        fetch('/game/termina-partita/', {
            method: 'POST',
            credentials: 'include',
        })
            .then(response => {
                if (!response.ok) {
                    return response.text().then(errorText => {
                        throw new Error(errorText);
                    });
                }
                return response.json();
            })
            .then(data => {
                console.log('Partita terminata con successo:', data);
                updateRanking(data);
            })
            .catch(error => {
                console.error('Errore durante la terminazione della partita:', error.message);
                alert('Non è stato possibile terminare la partita. Riprova più tardi.');
            });
    }

    function updateRanking(players) {
        document.getElementById('name1').textContent = players[0].username;
        document.getElementById('name2').textContent = players[1].username;
        if (players.length > 2) {
            document.getElementById('name3').textContent = players[2].username;
        }else{
            document.getElementById('third').style.display = 'none';
        }

        terminaPartita();
    }
});