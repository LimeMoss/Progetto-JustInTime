// match.js

document.addEventListener("DOMContentLoaded", function() {
    const onhandCards = document.querySelectorAll('.onhand-cards .clickable-card');
    const onTableCards = document.getElementById('ontable-cards').querySelector('img');
    const deck = document.getElementById('deck').querySelector('img');
    const onHandContainer = document.getElementById('onhand-cards');

    onhandCards.forEach(card => {
        card.addEventListener('click', function() {
            // Sposta la carta cliccata al tavolo
            onTableCard.src = card.src;
            card.remove(); // Rimuove la carta dalla mano
            updateCardSizes();
        });
    });

    function updateCardSizes() {
        const onhandCards = document.querySelectorAll('.onhand-cards .clickable-card');
        const cardCount = onhandCards.length;
        const containerWidth = onHandContainer.clientWidth;
        const cardWidth = Math.min(containerWidth / cardCount - 10, 150); // 150px è la larghezza massima per carta

        onhandCards.forEach(card => {
            card.style.flexBasis = `${cardWidth}px`;
        });
    }

    deck.addEventListener('click', function() {
        // Crea una nuova carta e aggiungila alla mano
        const newCard = document.createElement('img');
        newCard.src = '../static/images/justcardbase.png'; // Assumi che sia questa l'immagine della nuova carta
        newCard.alt = 'onhand-card';
        newCard.addEventListener('click', function() {
            // Sposta la carta cliccata al tavolo
            const tempSrc = onTableCards.src;
            onTableCards.src = newCard.src;
            newCard.src = tempSrc;
        });
        onHandContainer.appendChild(newCard);
        updateCardSizes();
    });

    window.addEventListener('resize', updateCardSizes); // Aggiorna le dimensioni delle carte al ridimensionamento della finestra
});
