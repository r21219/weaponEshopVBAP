package cz.osu.weaponeshop.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class WeaponOrderLine {
    @Id
    private Long id;
    @ManyToOne
    private Cart cart;
    @OneToOne
    private Weapon weapon;
    private int unit;
}
