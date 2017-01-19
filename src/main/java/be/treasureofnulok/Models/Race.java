package be.treasureofnulok.Models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Jerry-Lee on 19/01/2017.
 */
@Entity
public class Race implements Serializable {

    @Id
    @GeneratedValue
    @Getter
    private long id;

    @Column(nullable = false, unique = true)
    @Getter
    @Setter
    private String name;

    public Race(){

    }

    public Race(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("Race{ id=%d, name=\'%s\'}",id,name);
    }
}
