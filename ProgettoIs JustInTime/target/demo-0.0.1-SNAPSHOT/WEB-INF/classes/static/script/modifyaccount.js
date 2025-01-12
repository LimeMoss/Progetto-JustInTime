document.addEventListener("DOMContentLoaded", () => {
    fetch('/utenze/trovaUtenzaPsw', {
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
            document.getElementById("username").value = data.username || "NomeUtente";
            document.getElementById("nome").value = data.name || "placeholder";
            document.getElementById("cognome").value = data.cognome || "placeholder";
            document.getElementById("email").value = data.email || "placeholder";
            document.querySelector("select[name='paese']").value = data.paese || "placeholder";
            document.getElementById("password").value = data.password || "placeholder";
            document.getElementById("confirmpassword").value = data.password || "placeholder";

            const rawDate = data.dataNascita.split('T')[0];
            document.getElementById("datanascita").value = rawDate;

            const parts = data.telefono.split(" ");
            const codicePaese = parts[0]; // Primo elemento come codice paese
            const numero = parts.slice(1).join("").replace(/(\d{3})(\d{3})(\d{4})/, "$1 $2 $3"); // Formatta come xx xx xxx
            document.querySelector("select[name='countryCode']").value = codicePaese;
            document.getElementById("telefono").value = numero;
        })
        .catch(error => console.error('Errore:', error));
});