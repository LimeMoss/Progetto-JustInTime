document.addEventListener("DOMContentLoaded", function() {
    const onHandContainer = document.getElementById('onhand-cards');
    const onTableCard = document.getElementById('ontable-cards').querySelector('img');
    const deck = document.getElementById('deck').querySelector('img');
    const alertBanner = document.getElementById('alert-banner');

    function addCardToHand() {
        const onhandCards = document.querySelectorAll('.onhand-cards .clickable-card');
        if (onhandCards.length >= 22) {
            showAlertBanner();
            return;
        }
        // Crea una nuova carta e aggiungila alla mano
        const newCard = document.createElement('img');
        newCard.src = '../static/images/justcardbase.png'; // Assumi che sia questa l'immagine della nuova carta
        newCard.alt = 'onhand-card';
        newCard.classList.add('clickable-card');
        newCard.addEventListener('click', function() {
            // Sposta la carta cliccata al tavolo
            onTableCard.src = newCard.src;
            newCard.remove(); // Rimuove la carta dalla mano
            updateCardSizes();
            closeBanner(); // Chiudi il banner se una carta viene cliccata
        });
        onHandContainer.appendChild(newCard);
        updateCardSizes();
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

    // Aggiungi l'event listener a tutte le carte attuali in mano
    document.querySelectorAll('.onhand-cards .clickable-card').forEach(card => {
        card.addEventListener('click', function() {
            // Sposta la carta cliccata al tavolo
            onTableCard.src = card.src;
            card.remove(); // Rimuove la carta dalla mano
            updateCardSizes();
            closeBanner(); // Chiudi il banner se una carta viene cliccata
        });
    });

    window.closeBanner = closeBanner; // Assicura che la funzione closeBanner sia accessibile globalmente

    updateCardSizes(); // Assicura che le dimensioni delle carte siano aggiornate all'avvio
});
