package cz.osu.weaponeshop.repository;

import cz.osu.weaponeshop.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag,Long> {
}
