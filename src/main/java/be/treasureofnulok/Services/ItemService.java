package be.treasureofnulok.Services;

import be.treasureofnulok.Models.Item;
import be.treasureofnulok.Models.Player;
import be.treasureofnulok.Models.PlayerItem;
import be.treasureofnulok.Models.Storage;
import be.treasureofnulok.Repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Jerry-Lee on 18/01/2017.
 */
@Service
public class ItemService {
    @Autowired
    ItemRepository repository;
    @Autowired
    PlayerItemService playerItemService;
    @Autowired
    StorageService storageService;

    public ItemService() {

    }

    public void saveItem(Item item){
        repository.save(item);
    }
    public Item findItemById(Long id){
        return repository.findItemById(id);
    }
    public void deleteItem(Item item){
        repository.delete(item);
    }

    public void storeItemInPlayerInventory(Player player, Item item, int amount) {
        Storage inventory = storageService.findStorageById("Inventory");
        this.playerItemService.savePlayerItem(new PlayerItem(player, item, amount, inventory));
    }
}
