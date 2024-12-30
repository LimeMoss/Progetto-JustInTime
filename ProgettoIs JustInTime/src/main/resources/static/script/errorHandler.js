// errorHandler.js

// Funzione per ottenere i parametri della query string
function getQueryParams() {
    const params = {};
    const queryString = window.location.search.substring(1);
    const regex = /([^&=]+)=([^&]*)/g;
    let m;
    while ((m = regex.exec(queryString))) {
        params[decodeURIComponent(m[1])] = decodeURIComponent(m[2]);
    }
    return params;
}

// Visualizza il messaggio di errore se presente
document.addEventListener('DOMContentLoaded', () => {
    const params = getQueryParams();
    if (params.error) {
        document.getElementById('error-message').textContent = params.error;
    }
});
