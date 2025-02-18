package org.lab1;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code BookDatabaseManager} class is responsible for managing the connection to the database
 * and performing CRUD (Create, Read, Update, Delete) operations on books and authors.
 *
 * <p>This class provides functionality to:
 * <ul>
 *     <li>Connect to a MariaDB database.</li>
 *     <li>Load books, authors, and their relationships from the database.</li>
 *     <li>Add, update, and retrieve books and authors.</li>
 *     <li>Maintain relationships between books and authors.</li>
 * </ul>
 * </p>
 *
 * <p>Database structure:
 * <ul>
 *     <li>{@code titles} table stores book details.</li>
 *     <li>{@code authors} table stores author details.</li>
 *     <li>{@code authorISBN} table maintains relationships between books and authors.</li>
 * </ul>
 * </p>
 */
public class BookDatabaseManager {
    private static final String DB_URL = "jdbc:mariadb://localhost:3306/books";
    private static final String USER = "root";
    private static final String PASSWORD = "1qaz2w";

    private Connection conn;
    private final List<Book> books;
    private final List<Author> authors;

    /**
     * Constructs a {@code BookDatabaseManager} object and initializes the database connection.
     */
    public BookDatabaseManager() {
        books = new ArrayList<>();
        authors = new ArrayList<>();
        try {
            connect();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Establishes a connection to the database.
     *
     * @throws SQLException if a database access error occurs
     */
    public void connect() throws SQLException {
        conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
    }

    /**
     * Loads all books, authors, and their relationships from the database.
     */
    public void loadData() {
        loadBooks();
        loadAuthors();
        loadRelationships();
    }

    /**
     * Loads all books from the 'titles' table.
     */
    private void loadBooks() {
        String sql = "SELECT * FROM titles";

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String isbn = rs.getString("isbn");
                String title = rs.getString("title");
                int editionNumber = rs.getInt("editionNumber");
                String copyright = rs.getString("copyright");
                Book book = new Book(isbn, title, editionNumber, copyright);
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads all authors from the 'authors' table.
     */
    private void loadAuthors() {
        String sql = "SELECT * FROM authors";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int authorID = rs.getInt("authorID");
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                Author author = new Author(authorID, firstName, lastName);
                authors.add(author);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the relationships from the 'authorISBN' table.
     */
    private void loadRelationships() {
        String sql = "SELECT * FROM authorISBN";

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int authorID = rs.getInt("authorID");
                String isbn = rs.getString("isbn");
                Book book = getBookByISBN(isbn);
                Author author = getAuthorByID(authorID);
                if (book != null && author != null) {
                    book.addAuthor(author);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Finds and returns a {@code Book} by its ISBN.
     *
     * @param isbn the ISBN of the book
     * @return the corresponding {@code Book} object, or {@code null} if not found
     */
    public Book getBookByISBN(String isbn) {
        for (Book b : books) {
            if (b.getIsbn().equals(isbn)) {
                return b;
            }
        }
        return null;
    }

    /**
     * Finds and returns an {@code Author} by their authorID.
     *
     * @param authorID the ID of the author
     * @return the corresponding {@code Author} object, or {@code null} if not found
     */
    public Author getAuthorByID(int authorID) {
        for (Author a : authors) {
            if (a.getAuthorID() == authorID) {
                return a;
            }
        }
        return null;
    }

    ////////// CRUD Methods //////////

    /**
     * Inserts a new {@code Book} into the database and adds it to the books list.
     *
     * @param book the {@code Book} object to be added
     * @return {@code true} if the book was added successfully, {@code false} otherwise
     */
    public boolean addBook(Book book) {
        String sql = "INSERT INTO titles (isbn, title, editionNumber, copyright) VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, book.getIsbn());
            ps.setString(2, book.getTitle());
            ps.setInt(3, book.getEditionNumber());
            ps.setString(4, book.getCopyright());
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                books.add(book);
                for (Author author : book.getAuthorList()) {
                    addAuthorISBNRelation(author, book);
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Inserts a row in the {@code authorISBN} table to create a relationship between an author and a book.
     *
     * @param author the {@code Author} to be linked to the book
     * @param book   the {@code Book} to be linked to the author
     * @return {@code true} if the relationship was added successfully, {@code false} otherwise
     */
    private boolean addAuthorISBNRelation(Author author, Book book) {
        String sql = "INSERT INTO authorISBN (authorID, isbn) VALUES (?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, author.getAuthorID());
            ps.setString(2, book.getIsbn());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Inserts a new {@code Author} into the database and adds it to the local authors list.
     *
     * <p>This method attempts to insert a new author record into the {@code authors} table.
     * After a successful insertion, the generated {@code authorID} is retrieved from the
     * database and assigned to the {@code Author} object. The author is then added to the
     * local {@code authors} list to keep the in-memory representation synchronized with
     * the database.</p>
     *
     * @param author the {@code Author} object containing the first and last name to be added
     * @return {@code true} if the author was successfully added, {@code false} otherwise
     */
    public boolean addAuthor(Author author) {
        String sql = "INSERT INTO authors (firstName, lastName) VALUES (?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, author.getFirstName());
            ps.setString(2, author.getLastName());
            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        author.setAuthorID(generatedKeys.getInt(1)); // Retrieve the generated authorID
                    }
                }
                authors.add(author); // Add the author to the in-memory list
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * Updates an existing {@code Book}'s attributes in the database.
     *
     * @param book the {@code Book} object with updated attributes
     * @return {@code true} if the book was updated successfully, {@code false} otherwise
     */
    public boolean updateBook(Book book) {
        String sql = "UPDATE titles SET title = ?, editionNumber = ?, copyright = ? WHERE isbn = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, book.getTitle());
            ps.setInt(2, book.getEditionNumber());
            ps.setString(3, book.getCopyright());
            ps.setString(4, book.getIsbn());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Updates an existing {@code Author}'s attributes in the database.
     *
     * @param author the {@code Author} object with updated attributes
     * @return {@code true} if the author was updated successfully, {@code false} otherwise
     */
    public boolean updateAuthor(Author author) {
        String sql = "UPDATE authors SET firstName = ?, lastName = ? WHERE authorID = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, author.getFirstName());
            ps.setString(2, author.getLastName());
            ps.setInt(3, author.getAuthorID());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Returns the list of all books loaded from the database.
     *
     * @return a list of {@code Book} objects
     */
    public List<Book> getBooks() {
        return books;
    }

    /**
     * Returns the list of all authors loaded from the database.
     *
     * @return a list of {@code Author} objects
     */
    public List<Author> getAuthors() {
        return authors;
    }
}