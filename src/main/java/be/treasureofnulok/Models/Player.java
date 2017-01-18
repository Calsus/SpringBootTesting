package be.treasureofnulok.Models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jerry-Lee on 18/01/2017.
 */
@Entity
public class Player {
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
    private String gender;

    @Column(nullable = false)
    @Setter
    @Getter
    private String race;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="player_item",
            joinColumns = @JoinColumn(name="player_id", referencedColumnName = "id", nullable =  false),
            inverseJoinColumns = @JoinColumn(name = "item_id", referencedColumnName = "id", nullable =  false))
    @Getter
    private List<Item> items;

    protected Player(){

    }

    public Player(String name, String gender, String race) {
        this.name = name;
        this.gender = gender;
        this.race = race;
        this.items = new ArrayList<>();
    }

    public Player addItem(Item item) {
        item.linkToPlayer(this);
        this.items.add(item);
        return this;
    }
    public Player addItems(List<Item> items) {
        items.forEach(item -> this.addItem(item));
        return this;
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", race='" + race + '\'' +
                '}';
    }
}
