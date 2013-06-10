package grytsenko.library.repository;

import grytsenko.library.model.User;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository of users.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finds user by its name.
     * 
     * @param username
     *            the name of user.
     */
    User findByUsername(String username);

}
