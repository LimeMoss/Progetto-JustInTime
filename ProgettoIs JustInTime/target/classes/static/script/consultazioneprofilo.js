document.addEventListener("DOMContentLoaded", () => {
    // Fetch dati giocatore
    fetch('/classifica/singlePlayerData', {
        method: 'GET',
        credentials: 'include' // Include cookies per HttpSession
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Errore nel recupero dei dati giocatore.');
            }
            return response.json();
        })
        .then(data => {
            document.getElementById("top-gamecounts").textContent = data.partiteGiocate || "0";
            document.getElementById("top-wins").textContent = data.vittorie || "0";
        })
        .catch(error => console.error('Errore:', error));

    // Fetch dati utenza
    fetch('/utenze/trovaUtenza', {
        method: 'GET',
        credentials: 'include' // Include cookies per HttpSession
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Errore nel recupero dei dati utenza.');
            }
            return response.json();
        })
        .then(data => {
            document.getElementById("username").textContent = data.username || "NomeUtente";
            document.getElementById("nome").textContent = data.name || "placeholder";
            document.getElementById("surname").textContent = data.cognome || "placeholder";
            document.getElementById("email").textContent = data.email || "placeholder";
            document.getElementById("phoneNumber").textContent = data.telefono || "placeholder";
            document.getElementById("country").textContent = data.paese || "placeholder";
            const rawDate = data.dataNascita || "placeholder";
            // Prendi solo la parte della data
            document.getElementById("datanascita").textContent = rawDate.split('T')[0];
        })
        .catch(error => console.error('Errore:', error));

    // Gestione del bottone "Modifica i tuoi dati"
    document.getElementById("modifybutton").addEventListener("click", () => {
        window.location.href = "/modifyaccount";  // Redirect alla pagina di modifica
    });

    // Gestione del bottone "Elimina account"
    document.getElementById("deletebutton").addEventListener("click", () => {
        if (confirm("Sei sicuro di voler eliminare il tuo account? Questa azione è irreversibile.")) {
            fetch('/utenze/rimuoviUtenza', {
                method: 'DELETE',
                credentials: 'include'  // Include i cookie per gestire la sessione
            })
                .then(response => {
                    if (response.ok) {
                        alert("Il tuo account è stato eliminato. Verrai reindirizzato alla home.");
                        window.location.href = "/";  // Redirect alla home
                    } else {
                        alert("Si è verificato un errore nell'eliminazione dell'account.");
                    }
                })
                .catch(error => {
                    console.error("Errore:", error);
                    alert("Si è verificato un errore.");
                });
        }
    });
});