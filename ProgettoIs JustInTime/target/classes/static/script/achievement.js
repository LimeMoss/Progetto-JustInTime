document.addEventListener('DOMContentLoaded', () => {
    document.querySelectorAll('.achievement').forEach(achievement => {
        const isCompleted = achievement.getAttribute('data-completed') === 'true';
        const card = achievement.querySelector('.achievement-card');
        const trophy = achievement.querySelector('.trophy');

        if (isCompleted) {
            card.classList.add('completed');
            achievement.classList.add('completed');
            trophy.disabled = false;
        } else {
            card.classList.remove('completed');
            achievement.classList.remove('completed');
            trophy.disabled = true;
            trophy.style.display = "none";
        }
    });
});
