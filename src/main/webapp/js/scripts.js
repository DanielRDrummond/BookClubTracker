document.addEventListener("DOMContentLoaded", function() {
    // Fetch book clubs when the page loads
    fetchBookClubs();
});

function fetchBookClubs() {
    fetch('/view-clubs.html')
        .then(response => response.json())
        .then(data => {
            displayBookClubs(data);
        })
        .catch(error => {
            console.error('Error fetching book clubs:', error);
        });
}

function displayBookClubs(bookClubs) {
    const bookClubsSection = document.getElementById('book-clubs-section');
    bookClubsSection.innerHTML = ''; // Clear previous content
    
    bookClubs.forEach(club => {
        const clubDiv = document.createElement('div');
        clubDiv.classList.add('club');
        
        const nameElement = document.createElement('h2');
        nameElement.textContent = club.name;
        
        const descriptionElement = document.createElement('p');
        descriptionElement.textContent = club.description;
        
        clubDiv.appendChild(nameElement);
        clubDiv.appendChild(descriptionElement);
        
        bookClubsSection.appendChild(clubDiv);
    });
}

// Add similar functions for fetching and displaying meetings, discussions, and comments
