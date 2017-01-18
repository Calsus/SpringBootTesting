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
    private Long id;

    @Column(nullable = false)
    @Setter
    @Getter
    private String name;

    @Column(nullable = false)
    @Setter
    @Getter
    private String rarity;

    @ManyToMany(mappedBy = "items")
    @Getter
    private List<Player> players;

    protected Item(){

    }

    public Item(String name, String rarity) {
        this.name = name;
        this.rarity = rarity;
        this.players = new ArrayList<>();
    }

    public Item linkToPlayer(Player player) {
        this.players.add(player);
        return this;
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
