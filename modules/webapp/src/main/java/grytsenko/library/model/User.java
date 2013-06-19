package grytsenko.library.model;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

/**
 * User of library.
 */
@Entity(name = "users")
public class User implements Serializable {

    private static final long serialVersionUID = -1054189841782371536L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Basic
    @Column(name = "username", length = 20)
    private String username;

    @Basic
    @Column(name = "firstname", length = 20)
    private String firstname;
    @Basic
    @Column(name = "lastname", length = 20)
    private String lastname;

    @Basic
    @Column(name = "mail", length = 50)
    private String mail;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", length = 10)
    private UserRole role;

    @Version
    private Integer version;

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    /**
     * Determines that this user is identical to other user, i.e. it has the
     * same identifier.
     */
    public boolean identicalTo(User other) {
        if (other == null) {
            return false;
        }

        if (id == null) {
            return false;
        }

        return id.equals(other.id);
    }

    /**
     * Synchronizes user with the same user from directory service.
     */
    public void syncWith(DsUser dsUser) {
        firstname = dsUser.getFirstname();
        lastname = dsUser.getLastname();
        mail = dsUser.getMail();
    }

}
