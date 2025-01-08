document.addEventListener("DOMContentLoaded", function() {
    const onHandContainer = document.getElementById('onhand-cards');
    const onTableCard = document.getElementById('ontable-cards').querySelector('img');
    const deck = document.getElementById('deck').querySelector('img');
    const alertBanner = document.getElementById('alert-banner');
    const popupContainer = document.getElementById('popup-container');

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
        showPopup('Turno completato', 'Premi OK per passare il turno', false); // Mostra il popup "Turno completato"
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
            credentials: 'include'
        })
            .then(response => {
                if (response.ok) {
                    console.log('Next player notified successfully.');
                    showNextTurnUser();
                } else {
                    console.error('Failed to notify next player:', response.statusText);
                }
            })
            .catch(error => console.error('Error notifying next player:', error));
    }

    function showNextTurnUser() {
        const nextTurnUser = "Prossimo Utente"; // Sostituisci con logica per ottenere il prossimo utente
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
});
