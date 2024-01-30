package cz.osu.weaponeshop.repository;

import cz.osu.weaponeshop.model.WeaponOrderLine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeaponOrderLineRepository extends JpaRepository<WeaponOrderLine,Long> {
}
