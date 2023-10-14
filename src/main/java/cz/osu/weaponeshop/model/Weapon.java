package cz.osu.weaponeshop.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Weapon {
    @Id
    private Long id;
    private String name;
    private String description;
    @ManyToMany
    private List<Tag> tags;
    private int price;
}
