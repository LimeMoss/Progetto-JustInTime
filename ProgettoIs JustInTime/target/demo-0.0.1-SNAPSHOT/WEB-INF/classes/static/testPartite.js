
let currentPlayerIndex = 0;
let gameState = {};
let playerNames = [];
let gameId;
let currentPlayerId;
let players = [];
let turnDuration = 10;  // Tempo in secondi per ogni turno (da configurare in base alla logica della partita)
let gameInterval;

// Funzione per creare i giocatori
function createPlayers() {
    const player1Name = document.getElementById("player1").value;
    const player2Name = document.getElementById("player2").value;
    const player3Name = document.getElementById("player3").value;
    const player4Name = document.getElementById("player4").value;

    const playersToCreate = [
        { name: player1Name, maxScore: 100 },
        { name: player2Name, maxScore: 100 },
        { name: player3Name, maxScore: 100 },
        { name: player4Name, maxScore: 100 }
    ].filter(player => player.name.trim() !== "");

    if (playersToCreate.length < 1 || playersToCreate.length > 4) {
        alert("Devi inserire almeno 1 giocatore e al massimo 4.");
        return;
    }

    playersToCreate.forEach((player, index) => {
        fetch(`/giocatore/crea?name=${player.name}&maxScore=${player.maxScore}`, {
            method: 'POST'
        })
        .then(response => response.json())
        .then(data => {
            players.push(data);
            if (index === playersToCreate.length - 1) {
                startGame();
            }
        });
    });
}

// Funzione per avviare la partita
function startGame() {
    document.getElementById("playerInputSection").classList.add("hidden");
    document.getElementById("gameSection").classList.remove("hidden");

    nextTurn();
}

// Funzione per passare al prossimo turno
function nextTurn() {
    if (currentPlayerIndex >= players.length) {
        currentPlayerIndex = 0;
    }

    const currentPlayer = players[currentPlayerIndex];
    document.getElementById("playerTurn").innerText = `Turno di: ${currentPlayer.name}`;
    document.getElementById("gameStatus").innerText = "";
    startTimer();

    currentPlayerId = currentPlayer.id;
    displayCards(currentPlayer);
    
    currentPlayerIndex++;
}

// Funzione per visualizzare le carte del giocatore
function displayCards(player) {
    const cardSelectionDiv = document.getElementById("cardSelection");
    cardSelectionDiv.innerHTML = "";

    player.carte.forEach(card => {
        const cardDiv = document.createElement("div");
        cardDiv.classList.add("player-card");
        cardDiv.innerText = card.tipo === "numerata" ? `Carta ${card.valore}` : `Carta speciale: ${card.tipo}`;
        cardDiv.onclick = () => playCard(card);
        cardSelectionDiv.appendChild(cardDiv);
    });
}

// Funzione per giocare una carta
function playCard(card) {
    fetch(`/partita/${gameId}/giocatori/${currentPlayerId}/gioca`, {
        method: 'POST',
        body: JSON.stringify({ carta: card }),
        headers: { 'Content-Type': 'application/json' }
    })
    .then(response => {
        if (response.ok) {
            alert("Carta giocata con successo!");
            nextTurn();
        } else {
            alert("Carta non valida, prova di nuovo.");
        }
    });
}

// Funzione per pescare una carta
function drawCard() {
    fetch(`/partita/${gameId}/giocatori/${currentPlayerId}/pesca`, { method: 'POST' })
        .then(response => response.json())
        .then(data => {
            displayCards
        });
}

// Funzione per avviare il timer per ogni turno
function startTimer() {
    let timeRemaining = turnDuration;
    document.getElementById("timer").innerText = `Tempo restante: ${timeRemaining}s`;

    gameInterval = setInterval(() => {
        timeRemaining--;
        document.getElementById("timer").innerText = `Tempo restante: ${timeRemaining}s`;

        if (timeRemaining <= 0) {
            clearInterval(gameInterval);
            alert("Tempo scaduto! Passando al prossimo turno...");
            nextTurn();
        }
    }, 1000);
}
