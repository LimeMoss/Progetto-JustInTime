document.addEventListener('DOMContentLoaded', function() {
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
});
