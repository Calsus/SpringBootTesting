package be.treasureofnulok.Repositories;

import be.treasureofnulok.Models.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Jerry-Lee on 18/01/2017.
 */
public interface ItemRepository extends JpaRepository<Item,Long> {
    Page<Item> findAll(Pageable pageable);
    List<Item> findByNameIgnoringCase(String name);
    Item findItemById(Long id);
}
