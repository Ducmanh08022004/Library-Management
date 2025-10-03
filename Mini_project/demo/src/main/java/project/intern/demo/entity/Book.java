package project.intern.demo.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "title", length = 50)
    private String title;

    @Column(name = "author",length = 50)
    private String author;

    @Column(name = "genre")
    private String genre;

    @Column(name = "available")
    private int available;

    @Nullable
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<BorrowedRecord> borrowedRecords;

    public Book() {
    }

    public Book(int id, String title, String author, String genre, int available, List<BorrowedRecord> borrowedRecords) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.available = available;
        this.borrowedRecords = borrowedRecords;
    }

    public List<BorrowedRecord> getBorrowedRecords() {
        return borrowedRecords;
    }

    public void setBorrowedRecords(List<BorrowedRecord> borrowedRecords) {
        this.borrowedRecords = borrowedRecords;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }
}
