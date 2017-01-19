package be.treasureofnulok.Services;

import be.treasureofnulok.Models.Item;
import be.treasureofnulok.Models.Player;
import be.treasureofnulok.Repositories.ItemRepository;
import be.treasureofnulok.Repositories.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Jerry-Lee on 18/01/2017.
 */
@Service
public class PlayerService {
    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    PlayerItemService playerItemService;

    public PlayerService() {
    }

    public void savePlayer(Player player){
        playerRepository.save(player);
    }
    public Player findPlayerById(Long id){
        return playerRepository.findPlayerById(id);
    }
    public void deletePlayer(Player player){
        playerRepository.delete(player);
    }
    public List<Item> findItemsOfPlayer(Player player) {
        return playerItemService.findItemsOfPlayer(player);
    }
}
