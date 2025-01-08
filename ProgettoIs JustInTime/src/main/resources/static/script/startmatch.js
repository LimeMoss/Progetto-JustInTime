document.addEventListener('DOMContentLoaded', function() {
    const startButton = document.getElementById("start-button");
    
    function getSessionUser() {
        fetch('/api/game-config/getSessionUser', {
            method: 'GET',
            credentials: 'include'
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Errore nel recupero del nome utente della sessione.');
            }
            return response.text();
        })
        .then(username => {
            document.getElementById('username').textContent = username;
        })
        .catch(error => {
            console.error('Errore:', error);
            document.getElementById('username').textContent = 'Errore nel caricamento del nome utente';
        });
    }

    getSessionUser();

    startButton.addEventListener("click", () => {
        startButton.disabled = true; // Disabilita il pulsante per evitare ulteriori clic
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
                alert('Errore nel segnalare il giocatore come pronto.');
            }
        })
        .catch(error => {
            console.error('Error notifying next player:', error);
            alert('Errore nel connettersi al server.');
        })
        .finally(() => {
            startButton.disabled = false; // Riabilita il pulsante dopo la risposta
        });
    });
});

