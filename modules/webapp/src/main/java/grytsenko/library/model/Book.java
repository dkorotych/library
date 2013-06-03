package grytsenko.library.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
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

/**
 * Information about hard copy of book.
 */
@Entity(name = "books")
public class Book implements Serializable {

    private static final long serialVersionUID = 6759600794860542365L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "info_id", nullable = false)
    private BookInfo info;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 10)
    private BookStatus status;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "status_changed")
    private Date statusChanged;

    @Basic
    @Column(name = "reserved_by", length = 20)
    private String reservedBy;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "reserved_since")
    private Date reservedSince;

    @Basic
    @Column(name = "borrowed_by", length = 20)
    private String borrowedBy;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "borrowed_since")
    private Date borrowedSince;

    @Basic
    @Column(name = "managed_by", length = 20)
    private String managedBy;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "managed_since")
    private Date managedSince;

    public Book() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BookInfo getInfo() {
        return info;
    }

    public void setInfo(BookInfo info) {
        this.info = info;
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

    public String getReservedBy() {
        return reservedBy;
    }

    public void setReservedBy(String reservedBy) {
        this.reservedBy = reservedBy;
    }

    public Date getReservedSince() {
        return reservedSince;
    }

    public void setReservedSince(Date reservedSince) {
        this.reservedSince = reservedSince;
    }

    public String getBorrowedBy() {
        return borrowedBy;
    }

    public void setBorrowedBy(String borrowedBy) {
        this.borrowedBy = borrowedBy;
    }

    public Date getBorrowedSince() {
        return borrowedSince;
    }

    public void setBorrowedSince(Date borrowedSince) {
        this.borrowedSince = borrowedSince;
    }

    public String getManagedBy() {
        return managedBy;
    }

    public void setManagedBy(String managedBy) {
        this.managedBy = managedBy;
    }

    public Date getManagedSince() {
        return managedSince;
    }

    public void setManagedSince(Date managedSince) {
        this.managedSince = managedSince;
    }

}
