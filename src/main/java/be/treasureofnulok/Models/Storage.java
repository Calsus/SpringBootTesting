package be.treasureofnulok.Models;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by Jerry-Lee on 19/01/2017.
 */
@Entity
@Data
public class Storage implements Serializable {

    @Id
    String id;

    @Column(nullable = false)
    int capacity;

    @Column
    boolean isFull;

    public Storage() {

    }

    public Storage(String id, Integer capacity) {
        this.id = id;
        this.capacity = capacity;
    }

    @Override
    public String toString() {
        return "Storage{" +
                "id='" + id + '\'' +
                ", capacity=" + capacity +
                ", isFull=" + isFull +
                '}';
    }
}
