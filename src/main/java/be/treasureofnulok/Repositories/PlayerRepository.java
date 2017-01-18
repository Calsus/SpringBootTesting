package be.treasureofnulok.Repositories;

import be.treasureofnulok.Models.Item;
import be.treasureofnulok.Models.Player;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

/**
 * Created by Jerry-Lee on 18/01/2017.
 */
public interface PlayerRepository extends JpaRepository<Player,Long> {
    Page<Player> findAll(Pageable pageable);
    List<Player> findByNameIgnoringCase(String name);
    Player findPlayerById(Long id);
}
