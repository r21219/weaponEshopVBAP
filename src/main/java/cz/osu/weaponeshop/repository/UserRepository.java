package cz.osu.weaponeshop.repository;

import cz.osu.weaponeshop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    boolean existsByUserName(String name);
    boolean existsByUserNameAndPassword(String name, String password);
    User findByUserNameAndPassword(String name, String password);

}
