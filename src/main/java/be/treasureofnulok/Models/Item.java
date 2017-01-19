package be.treasureofnulok.Models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jerry-Lee on 18/01/2017.
 */
@Entity
public class Item implements Serializable {
    @Id
    @GeneratedValue
    @Getter
    private long id;

    @Column(nullable = false, unique = true)
    @Setter
    @Getter
    private String name;

    @Column(nullable = false)
    @Setter
    @Getter
    @Enumerated(EnumType.STRING)
    private Rarity rarity;

    public Item(){

    }

    public Item(String name, Rarity rarity) {
        this.name = name;
        this.rarity = rarity;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", rarity='" + rarity + '\'' +

                '}';
    }
}
