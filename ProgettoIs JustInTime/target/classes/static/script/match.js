document.addEventListener("DOMContentLoaded", function () {
    const onHandContainer = document.getElementById('onhand-cards');
    const onTableCard = document.getElementById('ontable-cards').querySelector('img');
    const deck = document.getElementById('deck').querySelector('img');
    const alertBanner = document.getElementById('alert-banner');
    const popupContainer = document.getElementById('popup-container');

    const timeLeftLabel = document.getElementById('timeLeft');

    let turnoInCorso = true;
    let tempoRimanente = 0;

    function aggiornaTempoRimanente() {
        fetch('/game/timer') // Endpoint per ottenere la durata turno
            .then(response => {
                if (!response.ok) {
                    throw new Error('Errore nella risposta del server');
                }
                return response.json();
            })
            .then(data => {
                // `data` è un valore intero restituito dal backend
                if (typeof data === 'number') {
                    tempoRimanente = data;
                    aggiornaTimer();
                } else {
                    console.log("Formato della risposta non corretto");
                }
            })
            .catch(error => console.error('Errore durante il fetch del tempo turno:', error));
    }

    function aggiornaTimer() {
        const timerInterval = setInterval(() => {
            if (!turnoInCorso) {
                clearInterval(timerInterval);
                return;
            }

            if (tempoRimanente > 0) {
                tempoRimanente--;
                timeLeftLabel.textContent = `${tempoRimanente}s`;
            } else {
                clearInterval(timerInterval);
                turnoInCorso = false;
                timeLeftLabel.textContent = 'Turno scaduto!';
                showPopup('Il tuo tempo è terminato. Hai perso', 'Premi OK per passare il turno', false);
            }
        }, 1000);
    }

    let players = []; // Lista dei giocatori
    let currentPlayerIndex = 0; // Indice del giocatore corrente

    // Recupera la lista dei giocatori configurati
    function fetchPlayers() {
        fetch('/api/game-config/players', {
            method: 'GET',
            credentials: 'include',
        })
            .then(response => {
                if (response.ok) {
                    return response.json();
                } else {
                    throw new Error('Errore durante il recupero dei giocatori configurati.');
                }
            })
            .then(playerNames => {
                players = playerNames;
                console.log(players);
            })
            .catch(error => console.error('Errore:', error));
    }

    function addCardToHand() {
        const onhandCards = document.querySelectorAll('.onhand-cards .clickable-card');
        if (onhandCards.length >= 22) {
            showAlertBanner();
            return;
        }
        const newCard = document.createElement('img');
        newCard.src = '../images/justcardbase.png';
        newCard.alt = 'onhand-card';
        newCard.classList.add('clickable-card');
        newCard.addEventListener('click', handleCardClick);
        onHandContainer.appendChild(newCard);
        updateCardSizes();
        showPopup('Turno completato', 'Premi OK per passare il turno', false);
    }

    function handleCardClick(event) {
        const clickedCard = event.currentTarget;
        onTableCard.src = clickedCard.src;
        clickedCard.remove();
        updateCardSizes();
        closeBanner();
        showPopup('Turno completato', 'Premi OK per passare il turno', false);
    }

    function showPopup(title, message, blurBackground) {
        popupContainer.innerHTML = `
            <div class="popup-content">
                <h1>${title}</h1>
                <p>${message}</p>
                <button id="popup-ok-btn">OK</button>
            </div>
        `;

        if (blurBackground) {
            popupContainer.classList.remove('normal');
            popupContainer.classList.add('blurred');
        } else {
            popupContainer.classList.remove('blurred');
            popupContainer.classList.add('normal');
        }

        popupContainer.style.display = 'flex';

        document.getElementById('popup-ok-btn').addEventListener('click', () => {
            popupContainer.style.display = 'none';
            if (title === 'Turno completato') {
                notifyNextPlayerReady();
            }
        });
    }

    function notifyNextPlayerReady() {
        fetch('/game/nextPlayerReady', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            credentials: 'include',
        })
            .then(response => {
                if (response.ok) {
                    console.log('Next player notified successfully.');
                    passTurnToNextPlayer();
                } else {
                    console.error('Failed to notify next player:', response.statusText);
                }
            })
            .catch(error => console.error('Error notifying next player:', error));
    }

    function passTurnToNextPlayer() {
        // Incrementa l'indice ciclicamente
        currentPlayerIndex = (currentPlayerIndex + 1) % players.length;
        const nextTurnUser = players[currentPlayerIndex];
        showPopup(`Turno di ${nextTurnUser}`, 'Premi OK per iniziare', true);
    }

    function updateCardSizes() {
        const onhandCards = document.querySelectorAll('.onhand-cards .clickable-card');
        const cardCount = onhandCards.length;
        const containerWidth = onHandContainer.clientWidth;
        const minCardWidth = 150;
        const maxCardWidth = 200;
        let cardWidth = Math.min((containerWidth - 10 * (cardCount - 1)) / cardCount, maxCardWidth);

        if (cardWidth < minCardWidth) {
            cardWidth = minCardWidth;
        }

        onhandCards.forEach(card => {
            card.style.width = `${cardWidth}px`;
        });
    }

    function showAlertBanner() {
        alertBanner.style.display = 'block';
    }

    function closeBanner() {
        alertBanner.style.display = 'none';
    }

    deck.addEventListener('click', addCardToHand);
    window.addEventListener('resize', updateCardSizes);

    document.querySelectorAll('.onhand-cards .clickable-card').forEach(card => {
        card.addEventListener('click', handleCardClick);
    });

    window.closeBanner = closeBanner;

    updateCardSizes();
    fetchPlayers(); // Recupera i giocatori al caricamento della pagina
    aggiornaTempoRimanente();
});
