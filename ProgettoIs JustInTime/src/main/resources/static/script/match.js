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
    }

    function handleCardClick(event) {
        const clickedCard = event.currentTarget;
        onTableCard.src = clickedCard.src;
        clickedCard.remove();
        updateCardSizes();
        closeBanner();
        showPopup();
    }

    function showPopup() {
        popupContainer.innerHTML = `<div class="popup-content"><h1>Turno completato</h1><p>Prossimo turno in corso...</p></div>`;
        popupContainer.style.display = 'flex';

        setTimeout(() => {
            popupContainer.style.display = 'none';
            showNextTurnUser();
        }, 2500);
    }

    function showNextTurnUser() {
        const nextTurnUser = "Prossimo Utente"; // Sostituisci con logica per ottenere il prossimo utente
        popupContainer.innerHTML = `<div class="popup-content"><h1>Turno di ${nextTurnUser}</h1></div>`;
        popupContainer.style.display = 'flex';

        setTimeout(() => {
            popupContainer.style.display = 'none';
        }, 2500);
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
