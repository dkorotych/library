package grytsenko.library.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

/**
 * Information about a copy of book.
 */
@Entity(name = "books")
public class Book implements Serializable {

    private static final long serialVersionUID = 6759600794860542365L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "details_id", nullable = false)
    private BookDetails details;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 10)
    private BookStatus status;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "status_changed")
    private Date statusChanged;

    @ManyToOne
    @JoinColumn(name = "reserved_by")
    private User reservedBy;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "reserved_since")
    private Date reservedSince;

    @ManyToOne
    @JoinColumn(name = "borrowed_by")
    private User borrowedBy;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "borrowed_since")
    private Date borrowedSince;

    @ManyToOne
    @JoinColumn(name = "managed_by", nullable = false)
    private User managedBy;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "managed_since")
    private Date managedSince;

    @Version
    private Integer version;

    public Book() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BookDetails getDetails() {
        return details;
    }

    public void setDetails(BookDetails details) {
        this.details = details;
    }

    public BookStatus getStatus() {
        return status;
    }

    public void setStatus(BookStatus status) {
        this.status = status;
    }

    public Date getStatusChanged() {
        return statusChanged;
    }

    public void setStatusChanged(Date statusChanged) {
        this.statusChanged = statusChanged;
    }

    public User getReservedBy() {
        return reservedBy;
    }

    public void setReservedBy(User reservedBy) {
        this.reservedBy = reservedBy;
    }

    public Date getReservedSince() {
        return reservedSince;
    }

    public void setReservedSince(Date reservedSince) {
        this.reservedSince = reservedSince;
    }

    public User getBorrowedBy() {
        return borrowedBy;
    }

    public void setBorrowedBy(User borrowedBy) {
        this.borrowedBy = borrowedBy;
    }

    public Date getBorrowedSince() {
        return borrowedSince;
    }

    public void setBorrowedSince(Date borrowedSince) {
        this.borrowedSince = borrowedSince;
    }

    public User getManagedBy() {
        return managedBy;
    }

    public void setManagedBy(User managedBy) {
        this.managedBy = managedBy;
    }

    public Date getManagedSince() {
        return managedSince;
    }

    public void setManagedSince(Date managedSince) {
        this.managedSince = managedSince;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

}
