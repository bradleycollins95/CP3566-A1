package org.lab1;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@code Author} class represents an author in the books database.
 * It stores information such as the author's ID, first name, last name,
 * and a list of books they have authored.
 *
 * This class also provides methods to manage the relationship between authors and books.
 */
public class Author {
    private int authorID;
    private String firstName;
    private String lastName;
    private List<Book> bookList;

    /**
     * Constructs an {@code Author} object with the given details.
     *
     * @param authorID  the unique identifier for the author
     * @param firstName the first name of the author
     * @param lastName  the last name of the author
     */
    public Author(int authorID, String firstName, String lastName) {
        this.authorID = authorID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.bookList = new ArrayList<>();
    }

    /**
     * Returns the author's ID.
     *
     * @return the author ID
     */
    public int getAuthorID() {
        return authorID;
    }

    /**
     * Sets the author's ID.
     *
     * @param authorID the author ID to set
     */
    public void setAuthorID(int authorID) {
        this.authorID = authorID;
    }

    /**
     * Returns the author's first name.
     *
     * @return the first name of the author
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the author's first name.
     *
     * @param firstName the first name to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Returns the author's last name.
     *
     * @return the last name of the author
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the author's last name.
     *
     * @param lastName the last name to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Returns the list of books associated with the author.
     *
     * @return the list of books
     */
    public List<Book> getBookList() {
        return bookList;
    }

    /**
     * Adds a book to the author's list if it is not already present.
     * Also ensures bidirectional consistency by adding the author to the book.
     *
     * @param book the book to add
     */
    public void addBook(Book book) {
        if (!bookList.contains(book)) {
            bookList.add(book);
            if (!book.getAuthorList().contains(this)) {
                book.addAuthor(this);
            }
        }
    }

    /**
     * Returns a string representation of the author, including the list of books.
     *
     * @return a formatted string with author details and associated books
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Author ID: ").append(authorID)
                .append(", Name: ").append(firstName).append(" ").append(lastName);

        if (!bookList.isEmpty()) {
            sb.append("\nBooks: ");
            for (Book book : bookList) {
                sb.append(book.getTitle()).append(" (").append(book.getIsbn()).append("), ");
            }
            sb.setLength(sb.length() - 2);
        }
        return sb.toString();
    }
}


