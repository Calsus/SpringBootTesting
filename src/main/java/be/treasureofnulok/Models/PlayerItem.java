package be.treasureofnulok.Models;

import lombok.Data;
import lombok.Getter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Jerry-Lee on 19/01/2017.
 */
@Entity
@Data
public class PlayerItem implements Serializable {

    @Id
    @GeneratedValue
    @Getter
    long id;

    @ManyToOne
    @JoinColumn(name="player_id", referencedColumnName = "id", nullable = false)
    Player player;

    @ManyToOne
    @JoinColumn(name="item_id", referencedColumnName = "id", nullable = false)
    Item item;

    @Column
    int amount;

    @ManyToOne
    @JoinColumn(name="storedIn", referencedColumnName = "id", nullable = false)
    Storage storedIn;

    public PlayerItem(){

    }

    public PlayerItem(Player player, Item item, int amount, Storage storedIn) {
        this.player = player;
        this.item = item;
        this.amount = amount;
        this.storedIn = storedIn;
    }

    @Override
    public String toString() {
        return "PlayerItem{" +
                "id=" + id +
                ", player=" + player +
                ", item=" + item +
                ", amount=" + amount +
                ", storedIn=" + storedIn +
                '}';
    }
}
