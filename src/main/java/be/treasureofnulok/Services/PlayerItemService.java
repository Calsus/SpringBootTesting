package be.treasureofnulok.Services;

import be.treasureofnulok.Models.Item;
import be.treasureofnulok.Models.Player;
import be.treasureofnulok.Models.PlayerItem;
import be.treasureofnulok.Repositories.PlayerItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Jerry-Lee on 19/01/2017.
 */
@Service
public class PlayerItemService {
    @Autowired
    PlayerItemRepository repository;

    public void savePlayerItem(PlayerItem playerItem) {
        repository.save(playerItem);
    }

    public List<Item> findItemsOfPlayer(Player player) {
        return repository.findPlayerItemsByPlayer(player).stream().map(PlayerItem::getItem).collect(Collectors.toList());
    }
}
