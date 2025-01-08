document.addEventListener('DOMContentLoaded', function() {
    const startButton = document.getElementById("start-button");
    // Funzione per ottenere il nome utente dalla sessione
    function getSessionUser() {
        fetch('/api/game-config/getSessionUser', {
            method: 'GET',
            credentials: 'include' // Include i cookie per ottenere la sessione corretta
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Errore nel recupero del nome utente della sessione.');
                }
                return response.text(); // Assumiamo che il nome utente sia restituito come testo semplice
            })
            .then(username => {
                // Sostituisci il placeholder con il nome utente effettivo
                document.getElementById('username').textContent = username;
            })
            .catch(error => {
                console.error('Errore:', error);
                document.getElementById('username').textContent = 'Errore nel caricamento del nome utente';
            });
    }

    // Chiamare la funzione per caricare il nome utente
    getSessionUser();

    startButton.addEventListener("click", () => {
        fetch('/game/PlayerReady', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            credentials: 'include'
        })
            .then(response => {
                if (response.ok) {
                    console.log('Next player notified successfully.');
                    window.location.href = "/match";
                } else {
                    console.error('Failed to notify next player:', response.statusText);
                }
            })
            .catch(error => console.error('Error notifying next player:', error));
    })
});
