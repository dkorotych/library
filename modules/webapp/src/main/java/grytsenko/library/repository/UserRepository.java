package grytsenko.library.repository;

import grytsenko.library.model.User;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository of users.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Find user by its name.
     */
    User findByUsername(String username);

}
