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
     * @param name
     *            the name of user.
     */
    User findByName(String name);

}
