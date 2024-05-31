document.addEventListener("DOMContentLoaded", function() {
    fetch('/api/bookclubs')
        .then(response => response.json())
        .then(data => {
            const bookClubsList = document.getElementById('book-clubs-list');
            data.forEach(club => {
                const listItem = document.createElement('li');
                listItem.innerHTML = `
                    <h3>${club.name}</h3>
                    <p>${club.description}</p>
                    <p><strong>Created by:</strong> ${club.creator}</p>
                `;
                bookClubsList.appendChild(listItem);
            });
        })
        .catch(error => {
            console.error('Error fetching book clubs:', error);
        });
});
