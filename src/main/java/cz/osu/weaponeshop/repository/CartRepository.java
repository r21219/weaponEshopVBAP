package cz.osu.weaponeshop.repository;

import cz.osu.weaponeshop.model.Cart;
import cz.osu.weaponeshop.model.CartStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUser_UserNameAndCartStatus(String username, CartStatus cartStatus);
}
