document.addEventListener('DOMContentLoaded', function () {
    const stars = document.querySelectorAll('.star');
    let selectedStars = 0;

    stars.forEach((star, index) => {
        star.addEventListener('click', () => {
            for (let i = 0; i <= index; i++) {
                stars[i].classList.remove('disabled');
            }

            for (let i = index + 1; i < stars.length; i++) {
                stars[i].classList.add('disabled');
            }

            selectedStars = index + 1;
        });
    });

    const submitButton = document.querySelector('.submit-button');
    const feedbackTextbox = document.querySelector('.feedback-textbox');

    submitButton.addEventListener('click', async function () {
        const feedbackDescription = feedbackTextbox.value.trim();

        if (!selectedStars) {
            alert('Per favore, seleziona un punteggio in stelle.');
            return;
        }

        if (!feedbackDescription) {
            alert('Per favore, scrivi il tuo feedback.');
            return;
        }

        const feedbackData = {
            descrizione: feedbackDescription,
            stars: selectedStars
        };

        try {
            const response = await fetch('/feedback', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                },
                body: new URLSearchParams(feedbackData)
            });

            if (response.ok) {
                const feedback = await response.json();
                alert('Feedback inviato con successo!');
                feedbackTextbox.value = '';
                selectedStars = 0;
                stars.forEach(star => star.classList.add('disabled'));

                // Reindirizzamento alla homepage
                window.location.href = '/userHomepage';
            } else {
                const error = await response.text();
                alert(`Errore: ${error}`);
            }
        } catch (error) {
            console.error('Errore durante l\'invio del feedback:', error);
            alert('Si è verificato un errore. Riprova più tardi.');
        }
    });
});
