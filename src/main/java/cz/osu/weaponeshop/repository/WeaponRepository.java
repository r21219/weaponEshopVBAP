package cz.osu.weaponeshop.repository;

import cz.osu.weaponeshop.model.Weapon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeaponRepository extends JpaRepository<Weapon,Long> {
}
