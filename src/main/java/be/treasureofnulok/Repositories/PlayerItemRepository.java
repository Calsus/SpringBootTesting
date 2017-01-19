package be.treasureofnulok.Repositories;

import be.treasureofnulok.Models.Player;
import be.treasureofnulok.Models.PlayerItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Jerry-Lee on 19/01/2017.
 */
public interface PlayerItemRepository extends JpaRepository<PlayerItem,Long> {
    PlayerItem findPlayerItemById(Long id);
    List<PlayerItem> findPlayerItemsByPlayer(Player player);
}
