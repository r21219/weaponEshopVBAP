package cz.osu.weaponeshop.repository;

import cz.osu.weaponeshop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
