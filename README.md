# 📚 Book Database Project

## 📖 Overview
This project sets up and manages a **book database** using **MariaDB** and **Java**. The application allows users to **view, edit, add, and delete books and authors** while maintaining their relationships.

---

## 📌 Table of Contents
- [📂 Database Setup](#database-setup)
- [📚 Book Database Setup](#book-database-setup)
- [🔧 Using the Book Database in Java](#using-the-book-database-in-java)
- [🚀 BookApplication](#bookapplication)
- [💻 Usage](#usage)
- [📜 License](#license)

---

## 📂 Database Setup

Ensure **MariaDB** is installed and configured on your system. If not, follow the installation guide provided on **D2L**.

---

## 📚 Book Database Setup

1. **Open HeidiSQL** (or your preferred database management tool).  
2. **Create a new database** named **`books`**.  
3. **Open a query window** in the **`books`** database.  
4. **Copy and execute** all SQL statements from the `books.sql` file.  
   - 🚨 *Remove `DROP` statements on the first execution to avoid errors.*  
   - 📝 *You may also remove `SELECT` statements at the end if not needed.*  
5. **Confirm data insertion** by running the `SELECT` statements at the end of the script.

---

## 🔧 Using the Book Database in Java

### 📖 Classes Overview

#### `📗 Book` Class
- Represents books from the **`titles`** table.
- **Attributes:**
  - `title`
  - `isbn`
  - `publisher`
  - `List<Author> authorList`
- Ensures that each book maintains its relationship with **authors**.

#### `✍️ Author` Class
- Represents authors from the **`authors`** table.
- **Attributes:**
  - `authorID`
  - `name`
  - `List<Book> bookList`
- Ensures that each author maintains their relationship with **books**.

#### `🗄️ BookDatabaseManager` Class
- **Handles** database connections and queries.
- **Stores** lists of books and authors.
- Uses **PreparedStatements** for CRUD operations:
  - 🆕 **Create** – Store new books/authors and their relationships.
  - 📖 **Read** – Load all or specific books/authors.
  - ✏️ **Update** – Modify books/authors and sync changes to the database.
  - 🗑️ **Delete** – Remove books/authors from the database.

> ⚠️ **Note:** Avoid duplicate objects when loading relationships between books and authors.

---

## 🚀 BookApplication

### 🎯 Features
✔️ **Print all books** (including their authors)  
✔️ **Print all authors** (including their books)  
✔️ **Edit a book's or author's attributes**  
✔️ **Add a new book** (for existing or new authors)  
✔️ **Maintain relationships** between books and authors  
✔️ **Quit application**  

### 📌 Application Flow
- Users **continue making selections** until they choose to **quit**.

---

## 💻 Usage

1. Run **`BookApplication.java`**.  
2. Follow the **on-screen prompts** to interact with the database.  
3. Choose an option from the menu:
   - 📚 **View books and authors**
   - ✏️ **Edit book/author details**
   - ➕ **Add new books/authors**
   - 🗑️ **Delete books/authors**
   - ❌ **Exit the program**

---

## 📜 License

This project is licensed under the **MIT License**.  



 
 
