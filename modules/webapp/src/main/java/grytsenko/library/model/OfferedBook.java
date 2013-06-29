package grytsenko.library.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

/**
 * Users can vote for offered book.
 */
@Entity(name = "offered_books")
public class OfferedBook implements Serializable {

    private static final long serialVersionUID = 6232675600589204785L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "details_id", nullable = false)
    private BookDetails details;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "offered_books_votes", joinColumns = @JoinColumn(name = "book_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> votedUsers;

    @Basic
    @Column(name = "deleted")
    private boolean deleted;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "deleted_at")
    private Date deletedAt;

    @Version
    private Integer version;

    public OfferedBook() {
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

    public List<User> getVotedUsers() {
        return votedUsers;
    }

    public void setVotedUsers(List<User> votedUsers) {
        this.votedUsers = votedUsers;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Date getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    /**
     * Returns total number of votes.
     */
    public int getVotesNum() {
        return votedUsers.size();
    }

    /**
     * Checks that book has vote from user.
     */
    public boolean hasVoteFrom(User user) {
        for (User votedUser : votedUsers) {
            if (votedUser.identicalTo(user)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Adds vote from user.
     */
    public void addVote(User user) {
        if (hasVoteFrom(user)) {
            throw new IllegalStateException("User can vote once.");
        }
        votedUsers.add(user);
    }

    /**
     * Performs a soft delete of book.
     */
    public void delete(Date deletedAt) {
        if (deleted) {
            throw new IllegalArgumentException("Book is already deleted.");
        }

        deleted = true;
        this.deletedAt = deletedAt;
    }

}
