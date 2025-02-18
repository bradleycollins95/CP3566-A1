package org.lab1;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@code Book} class represents a book in the books database.
 * It contains details about the book, such as its ISBN, title, edition number,
 * copyright information, and a list of authors associated with the book.
 *
 * <p>This class provides methods to:
 * <ul>
 *     <li>Get and set book attributes.</li>
 *     <li>Maintain the bidirectional relationship between books and authors.</li>
 *     <li>Provide a string representation of the book, including its authors.</li>
 * </ul>
 * </p>
 */
public class Book {
    private String isbn;
    private String title;
    private int editionNumber;
    private String copyright;
    private List<Author> authorList;

    /**
     * Constructs a {@code Book} object with the given details.
     *
     * @param isbn          the International Standard Book Number (ISBN) of the book
     * @param title         the title of the book
     * @param editionNumber the edition number of the book
     * @param copyright     the copyright information of the book
     */
    public Book(String isbn, String title, int editionNumber, String copyright) {
        this.isbn = isbn;
        this.title = title;
        this.editionNumber = editionNumber;
        this.copyright = copyright;
        this.authorList = new ArrayList<>();
    }

    /**
     * Returns the ISBN of the book.
     *
     * @return the ISBN of the book
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * Sets the ISBN of the book.
     *
     * @param isbn the ISBN to set
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    /**
     * Returns the title of the book.
     *
     * @return the title of the book
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the book.
     *
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Returns the edition number of the book.
     *
     * @return the edition number of the book
     */
    public int getEditionNumber() {
        return editionNumber;
    }

    /**
     * Sets the edition number of the book.
     *
     * @param editionNumber the edition number to set
     */
    public void setEditionNumber(int editionNumber) {
        this.editionNumber = editionNumber;
    }

    /**
     * Returns the copyright information of the book.
     *
     * @return the copyright information of the book
     */
    public String getCopyright() {
        return copyright;
    }

    /**
     * Sets the copyright information of the book.
     *
     * @param copyright the copyright information to set
     */
    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    /**
     * Returns the list of authors associated with the book.
     *
     * @return the list of authors
     */
    public List<Author> getAuthorList() {
        return authorList;
    }

    /**
     * Adds an author to this book's list if it is not already present.
     * Also ensures bidirectional consistency by adding the book to the author's list.
     *
     * @param author the {@code Author} object to be added
     */
    public void addAuthor(Author author) {
        if (!authorList.contains(author)) {
            authorList.add(author);
            if (!author.getBookList().contains(this)) {
                author.addBook(this);
            }
        }
    }

    /**
     * Returns a string representation of the book, including the list of authors.
     *
     * @return a formatted string with book details and associated authors
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ISBN: ").append(isbn)
                .append(", Title: ").append(title)
                .append(", Edition: ").append(editionNumber)
                .append(", Copyright: ").append(copyright);

        if (!authorList.isEmpty()) {
            sb.append("\nAuthors: ");
            for (Author author : authorList) {
                sb.append(author.getFirstName()).append(" ")
                        .append(author.getLastName()).append(", ");
            }
            // Remove the trailing comma and space
            sb.setLength(sb.length() - 2);
        }
        return sb.toString();
    }
}



