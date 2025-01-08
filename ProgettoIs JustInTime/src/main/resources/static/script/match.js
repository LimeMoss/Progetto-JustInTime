document.addEventListener("DOMContentLoaded", function () {
    const onHandContainer = document.getElementById('onhand-cards');
    const onTableCard = document.getElementById('ontable-cards').querySelector('img');
    const deck = document.getElementById('deck').querySelector('img');
    const alertBanner = document.getElementById('alert-banner');
    const popupContainer = document.getElementById('popup-container');

    const timeLeftLabel = document.getElementById('timeLeft');

    let turnoInCorso = true;
    let tempoRimanente = 0;
    let player_name;

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

    function fetchCurrentPlayer() {
        fetch('/nameIndexPlayer', {
            method: 'GET',
            credentials: 'include',
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Errore nel recupero del giocatore corrente');
                }
                return response.text(); // Il backend restituisce una stringa (nome utente del giocatore corrente)
            })
            .then(username => {
                player_name=username;
            })
            .catch(error => console.error('Errore nel recupero del giocatore corrente:', error));
    }

    function fetchPlayerHand() {
        fetch('/game/playerMano', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            credentials: 'include'
        })
            .then(response => response.json())
            .then(cards => {
                updateHand(cards);
            })
            .catch(error => console.error('Errore nel recuperare la mano del giocatore:', error));
    }

    function updateHand(cards) {
        onHandContainer.innerHTML = ''; // Svuota il contenitore delle carte

        cards.forEach(card => {
            const newCard = document.createElement('img');
            newCard.src = getCardImagePath(card.tipo, card.valore);
            newCard.alt = `Carta ${card.tipo}`;
            newCard.classList.add('clickable-card');
            newCard.addEventListener('click', handleCardClick);
            onHandContainer.appendChild(newCard);
        });

        updateCardSizes();
    }

    function getCardImagePath(tipo, valore) {
        if (tipo === 'numerata') {
            return `../images/justcard${valore}.png`;
        } else if (tipo === 'Accelera') {
            return '../images/accelera_card.png';
        } else if (tipo === 'Rallenta') {
            return '../images/rallenta_card.png';
        } else if (tipo === 'Jolly') {
            return '../images/jolly_card.png';
        }
        return '../images/justcardbase.png';
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
                <button id="popup-ok-btn" style="background-color: #D9773B">OK</button>
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
        showPopup(`Turno di ${player_name}`, 'Premi OK per iniziare', true);
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

    window.addEventListener('resize', updateCardSizes);

    document.querySelectorAll('.onhand-cards .clickable-card').forEach(card => {
        card.addEventListener('click', handleCardClick);
    });

    window.closeBanner = closeBanner;


    deck.addEventListener('click', drawCard);

    function drawCard() {
        fetch('/game/pesca-carta/', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            credentials: 'include'
        })
            .then(response => {
                if (!response.ok) {
                    // Se la risposta non è OK, prova a leggere il testo dell'errore
                    return response.text().then(errorText => {
                        throw new Error(errorText);
                    });
                }
                return response.json();
            })
            .then(newCard => {
                if (newCard) {
                    addCardToHand(newCard);
                    updateCardSizes();
                    showPopup('Turno completato', 'Premi OK per passare il turno', false);
                } else {
                    console.error('Errore: Nessuna carta restituita dal server');
                }
            })
            .catch(error => console.error('Errore nel pescare la carta:', error.message));
    }



    function addCardToHand(card) {
        const newCardElement = document.createElement('img');
        newCardElement.src = getCardImagePath(card.tipo, card.valore);
        newCardElement.alt = `Carta ${card.tipo}`;
        newCardElement.classList.add('clickable-card');
        newCardElement.addEventListener('click', handleCardClick);
        onHandContainer.appendChild(newCardElement);
    }

    updateCardSizes();
    fetchCurrentPlayer(); // Recupera i giocatori al caricamento della pagina
    fetchPlayerHand(); // Inizializza il caricamento delle carte
    aggiornaTempoRimanente();
});
