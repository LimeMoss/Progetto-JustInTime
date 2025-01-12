document.addEventListener('DOMContentLoaded', () => {
    fetch('/classifica/singlePlayerData', {
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
            document.getElementById("top-scorepoint").textContent = data.maxScore || "0000";
        })
        .catch(error => console.error('Errore:', error));
});