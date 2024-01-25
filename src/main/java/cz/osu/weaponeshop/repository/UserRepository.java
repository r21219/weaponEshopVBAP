package cz.osu.weaponeshop.repository;

import cz.osu.weaponeshop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUserName(String userName);
    boolean existsByUserName(String name);
    boolean existsByUserNameAndPassword(String name, String password);

}
