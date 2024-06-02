# Book Club Tracker

## Overview

Book Club Tracker is a web application that allows users to create, join, and manage book clubs. Users can also schedule meetings, participate in discussions, and post comments. This application is built using Java Servlets for the backend, a PostgreSQL database for data storage, and HTML/CSS/JavaScript for the frontend.

## Features

- User Registration and Login
- Create and Join Book Clubs
- View Book Clubs
- Schedule and View Meetings
- Participate in Discussions
- Post Comments

## Technologies Used

- **Backend:** Java Servlets
- **Frontend:** HTML, CSS, JavaScript
- **Database:** PostgreSQL
- **Build Tool:** Maven
- **Web Server:** Tomcat

## Prerequisites

- JDK 8 or higher
- Apache Tomcat 9 or higher
- PostgreSQL
- Maven

## Setup Instructions

### Step 1: Clone the Repository
```bash
   git clone https://github.com/DanielRDrummond/BookClubTracker.git
   cd BookClubTracker
```

### Step 2: Setup PostgreSQL Database

1. Install PostgreSQL if you haven't already.
2. Create a new database named `BookClub`.
3. Execute the following SQL script to create the necessary tables:


-- This script creates several tables:
```sql
-- users: Stores information about registered users, including their username, hashed password, and email.
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL
);

-- book_clubs: Stores information about book clubs, including the club name, description, creator, and creation date.
CREATE TABLE book_clubs (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    creator_id INT REFERENCES users(id),
    creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- books: Stores information about books, including the title, author, genre, and associated club.
CREATE TABLE books (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    genre VARCHAR(255),
    club_id INT REFERENCES book_clubs(id)
);

-- meetings: Stores information about club meetings, including the meeting date, location, and description.
CREATE TABLE meetings (
    id SERIAL PRIMARY KEY,
    date TIMESTAMP NOT NULL,
    location VARCHAR(255),
    description TEXT,
    club_id INT REFERENCES book_clubs(id)
);

-- club_members: Establishes memberships between users and book clubs, including the user's role (e.g., member or admin).
CREATE TABLE club_members (
    id SERIAL PRIMARY KEY,
    user_id INT REFERENCES users(id),
    club_id INT REFERENCES book_clubs(id),
    role VARCHAR(255)
);

-- discussions: Stores information about discussions within book clubs, including the discussion topic, initiator, and creation date.
CREATE TABLE discussions (
    id SERIAL PRIMARY KEY,
    topic VARCHAR(255) NOT NULL,
    initiator_id INT REFERENCES users(id),
    club_id INT REFERENCES book_clubs(id),
    creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- comments: Stores comments made within discussions, including the content and creation date.
CREATE TABLE comments (
    id SERIAL PRIMARY KEY,
    content TEXT NOT NULL,
    creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    discussion_id INT REFERENCES discussions(id),
    user_id INT REFERENCES users(id)
);

```

### Step 3: Configure the Application

1. Open src/main/resources/application.properties.
2. Configure the database connection settings:
```bash
db.url=jdbc:postgresql://localhost:5432/BookClub
db.username=your_db_username
db.password=your_db_password
```


### Step 4: Build the Project
```bash
mvn clean install
```

### Step 5: Deploy to Tomcat
1. Copy the generated WAR file from the target directory to the webapps directory of your Tomcat installation.
2. Start Tomcat.


### Step 6: Access the Application
Open your web browser and navigate to http://localhost:8080/book-club-tracker.


### Structure
```css
book-club-tracker/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── bookclubtracker/
│   │   │           ├── servlets/
│   │   │           │   ├── AuthenticationServlet.java
│   │   │           │   ├── RegisterServlet.java
│   │   │           │   ├── LogoutServlet.java
│   │   │           │   ├── ProfileServlet.java
│   │   │           │   ├── BookClubsServlet.java
│   │   │           │   ├── CreateBookClubServlet.java
│   │   │           │   ├── JoinClubServlet.java
│   │   │           │   ├── MeetingsServlet.java
│   │   │           │   ├── DiscussionsServlet.java
│   │   │           │   └── CommentsServlet.java
│   │   │           ├── models/
│   │   │           │   ├── User.java
│   │   │           │   ├── Book.java
│   │   │           │   ├── Meeting.java
│   │   │           │   └── Discussion.java
│   │   │           └── utils/
│   │   │               ├── DatabaseUtils.java
│   │   │               └── AuthenticationFilter.java
│   │   ├── resources/
│   │   │   └── application.properties
│   │   └── webapp/
│   │       ├── css/
│   │       │   └── styles.css
│   │       ├── js/
│   │       │   └── scripts.js
│   │       ├── index.html
│   │       ├── register.html
│   │       ├── login.html
│   │       ├── view-clubs.html
│   │       ├── create-club.html
│   │       └── profile.html
├── pom.xml
└── README.md
```

### Security Measures
- User Access Control: Only authenticated users can access certain endpoints.
- HTTPS: Ensure secure communication by using HTTPS.
- Input Validation and Sanitization: All user inputs are validated and sanitized to prevent SQL injection and XSS attacks.

### Contributors
Daniel Drummond

