package cz.osu.weaponeshop.repository;

import cz.osu.weaponeshop.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart,Long> {
}
