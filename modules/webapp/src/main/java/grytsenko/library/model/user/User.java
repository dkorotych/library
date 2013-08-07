package grytsenko.library.model.user;

import static grytsenko.library.util.StringUtils.isNullOrEmpty;

import java.io.Serializable;
import java.text.MessageFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * User of library.
 */
@Entity
@Table(name = "users")
public class User implements Serializable {

    private static final long serialVersionUID = -1054189841782371536L;

    public static final int NAME_LENGTH_MAX = 20;
    public static final int MAIL_LENGTH_MAX = 50;

    /**
     * Creates the new user with the given name.
     */
    public static User create(String username) {
        User user = new User();
        user.setUsername(username);
        user.setRole(UserRole.USER);
        return user;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "username", length = NAME_LENGTH_MAX)
    private String username;

    @Column(name = "firstname", length = NAME_LENGTH_MAX)
    private String firstname;
    @Column(name = "lastname", length = NAME_LENGTH_MAX)
    private String lastname;

    @Column(name = "mail", length = MAIL_LENGTH_MAX)
    private String mail;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", length = UserRole.LENGTH_MAX)
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
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (id == null) {
            return false;
        }

        if (!(obj instanceof User)) {
            return false;
        }
        User other = (User) obj;

        return id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return id == null ? 0 : id.hashCode();
    }

    /**
     * Determines that user is manager.
     */
    public boolean isManager() {
        return role == UserRole.MANAGER;
    }

    /**
     * Synchronizes user with the same user from directory service.
     */
    public void syncWith(DsUser dsUser) {
        firstname = dsUser.getFirstname();
        lastname = dsUser.getLastname();
        mail = dsUser.getMail();
    }

    /**
     * Returns the readable name of user.
     * 
     * <p>
     * If first name and last name are defined, then returns the full name. For
     * example, 'John Doe'.
     * 
     * <p>
     * In other case returns username.
     */
    public String getReadableName() {
        if (isNullOrEmpty(firstname) || isNullOrEmpty(lastname)) {
            return username;
        }

        return MessageFormat.format("{0} {1}", firstname, lastname);
    }

}
