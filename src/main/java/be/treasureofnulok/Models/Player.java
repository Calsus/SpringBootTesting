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
public class Player implements Serializable {
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
    private String gender;

    @ManyToOne
    @JoinColumn(name="race_id", nullable = false)
    private Race race;

    public Player(){

    }

    public Player(String name, String gender, Race race) {
        this.name = name;
        this.gender = gender;
        this.race = race;
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", race=" + race +
                '}';
    }
}
