document.addEventListener("DOMContentLoaded", function () {
    const onHandContainer = document.getElementById('onhand-cards');
    const onTableCard = document.getElementById('ontable-cards').querySelector('img');
    const deck = document.getElementById('deck').querySelector('img');
    const alertBanner = document.getElementById('alert-banner');
    const popupContainer = document.getElementById('popup-container');
    var currentPlayerIndex = 1;
    const timeLeftLabel = document.getElementById('timeLeft');

    let turnoInCorso = true;
    let tempoRimanente = 0;
    let player_name;

    function fetchLastDiscardedCard() {
        fetch('/game/last-discarded-card/', {
            method: 'GET',
            credentials: 'include',
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Errore nella risposta del server');
                }
                return response.text(); // Otteniamo il testo come stringa
            })
            .then(dataString => {
                try {
                    // Parsing della stringa JSON
                    const data = JSON.parse(dataString);
                    console.log('Dati ricevuti:', data);

                    // Controlla che `data` contenga `tipo` e `valore`
                    if (data.tipo && data.valore !== undefined) {
                        const tipo = data.tipo;
                        const valore = data.valore;
                        onTableCard.src = getCardImagePath(tipo, valore);
                        onTableCard.alt = `Carta ${tipo}`;
                    } else {
                        console.log('Dati ricevuti errati');
                        // Nessuna carta scartata o dati incompleti
                        onTableCard.src = '../images/justcardbase.png';
                        onTableCard.alt = 'Nessuna carta scartata';
                    }
                } catch (error) {
                    console.error('Errore nel parsing dei dati JSON:', error);
                    onTableCard.src = '../images/justcardbase.png';
                    onTableCard.alt = 'Errore nella carta scartata';
                }
            })
            .catch(error => console.error('Errore durante il fetch della carta scartata:', error));
    }

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
        fetch('/game/nameIndexPlayer', {
            method: 'GET',
            credentials: 'include',
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Errore nel recupero del giocatore corrente');
                }
                return response.text(); 
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

        cards.forEach((card, index) => {
            const newCard = document.createElement('img');
            newCard.src = getCardImagePath(card.tipo, card.valore);
            newCard.alt = `Carta ${card.tipo}`;
            newCard.classList.add('clickable-card');
            newCard.setAttribute('data-index', index); // Aggiungi l'indice come attributo
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
        const cardIndex = clickedCard.getAttribute('data-index'); // Ottieni l'indice della carta
        console.log('Index carta ' + cardIndex);
        // Invia richiesta al server per giocare la carta
        fetch(`/game/play-card/${cardIndex}`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            credentials: 'include',
        })
            .then(response => {
                if (!response.ok) {
                    return response.text().then(errorText => {
                        throw new Error(errorText);
                    });
                }
                console.log("Carta giocata!")
                return response.json();
            })
            .then(() => {
                // Rimuovi la carta dal DOM
                onTableCard.src = clickedCard.src;
                clickedCard.remove();
                if(document.querySelectorAll('.onhand-cards .clickable-card').length===0){
                    closeBanner();
                    showPopup('Hai vinto!', 'Premi ok per vedere la classifica', false);
                    terminaPartita();
                }
                updateCardSizes();
                closeBanner();
                showPopup('Turno completato', 'Premi OK per passare il turno', false);
            })
            .catch(error => console.error('Errore:', error.message));
    }

    function terminaPartita() {
        fetch('/game/termina-partita/', {
            method: 'POST',
            credentials: 'include',
        })
            .then(response => {
                if (!response.ok) {
                    return response.text().then(errorText => {
                        throw new Error(errorText);
                    });
                }
                return response.json();
            })
            .then(data => {
                console.log('Partita terminata con successo:', data);
                window.location.href = '/endMatch';
            })
            .catch(error => {
                console.error('Errore durante la terminazione della partita:', error.message);
                showPopup('Errore', 'Non è stato possibile terminare la partita. Riprova più tardi.', false);
            });
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
            if (title === `Turno di ${player_name}`) {
                aggiornaTempoRimanente();
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
        fetchCurrentPlayer();
        fetchPlayerHand();
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


    deck.addEventListener('click', () => {
        const cardCount = document.querySelectorAll('.onhand-cards img').length;

        if (cardCount >= 22) {
            showPopup('Limite di carte raggiunto', 'Hai già 22 carte in mano. Non puoi pescarne altre.', false);
        } else {
            drawCard();
        }
    });


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
    fetchLastDiscardedCard();
});
