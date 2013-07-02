package grytsenko.library.model.book;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

/**
 * Information about book.
 */
@Entity(name = "books_details")
public class BookDetails implements Serializable {

    private static final long serialVersionUID = 341757671464199966L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Basic
    @Column(name = "authors", length = 100)
    private String authors;
    @Basic
    @Column(name = "title", length = 100)
    private String title;

    @Basic
    @Column(name = "publisher")
    private String publisher;
    @Basic
    @Column(name = "year")
    private Integer year;

    @Basic
    @Column(name = "language", length = 20)
    private String language;
    @Basic
    @Column(name = "pages")
    private Integer pages;

    @Basic
    @Column(name = "isbn", length = 20)
    private String isbn;

    @Basic
    @Column(name = "thumbnail_url", length = 200)
    private String thumbnailUrl;
    @Basic
    @Column(name = "image_url", length = 200)
    private String imageUrl;

    @Version
    private Integer version;

    public BookDetails() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

}
