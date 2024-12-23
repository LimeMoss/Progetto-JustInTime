document.addEventListener("DOMContentLoaded", () => {
    const stars = document.querySelectorAll('.star');

    stars.forEach((star, index) => {
        star.addEventListener('click', () => {
            // Abilita la stella cliccata e tutte quelle precedenti
            for (let i = 0; i <= index; i++) {
                stars[i].classList.remove('disabled');
            }

            for (let i = index + 1; i < stars.length; i++) {
                stars[i].classList.add('disabled');
            }
        });
    });
});