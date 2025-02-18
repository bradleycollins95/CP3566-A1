  The Book Database Application is a Java-based project that integrates with a MariaDB database to manage books and authors efficiently. This application provides a command-line interface (CLI) for users to view, add, edit, and delete books and authors, while maintaining proper relationships between them in the database. The database structure is managed using HeidiSQL, allowing for an organized and relational approach to storing book and author data.
----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

🌟 Key Features
-----------------------------------------------------------------------------------------------------------------------------------------
📖 Book Management
View a list of all books along with their associated authors.
Add new books while selecting existing or new authors.
Edit book details (title, edition number, copyright).
Delete books, ensuring proper handling of related author data.

✍️ Author Management
View a list of all authors along with the books they have written.
Add new authors independently or while adding a book.
Edit author details (first name, last name).
Delete authors, maintaining database integrity.

🔗 Relationship Handling
Each book can have multiple authors.
Each author can have multiple books.
Relationships between books and authors are synchronized bidirectionally.

💾 Persistent Data Storage
The application connects to MariaDB using JDBC.
Data is persistently stored and updated in the database.
HeidiSQL provides database visualization for better data management.

🖥️ User-Friendly CLI
Text-based menu for easy navigation.
Simple prompts for user input.
Input validation to prevent incorrect entries.


----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------


🛠️ Technology Stack
-----------------------------------------------------------------------------------------------------------------------------------------
Java – Core programming language.
JDBC (Java Database Connectivity) – Connects to MariaDB.
MariaDB – Relational database for storing books and authors.
HeidiSQL – GUI tool for database management.

📝 How It Works
-----------------------------------------------------------------------------------------------------------------------------------------
1️⃣ Database Connection:
The application establishes a JDBC connection to MariaDB.
Database tables (authors, titles, authorISBN) are loaded into memory.

2️⃣ User Interaction via CLI:
The user selects an operation from a menu-driven interface.
Available options:
View books/authors
Add, edit, or delete books/authors
Quit the application

3️⃣ Data Processing:
The application processes user input and executes the corresponding SQL queries.
Changes are automatically saved in the database.

4️⃣ Exit & Data Persistence:
The program closes connections to prevent memory leaks.
All changes remain saved in MariaDB, ensuring persistent storage.


 
 
