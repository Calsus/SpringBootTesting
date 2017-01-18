package be.treasureofnulok.Services;

import be.treasureofnulok.Models.Item;
import be.treasureofnulok.Repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Jerry-Lee on 18/01/2017.
 */
@Service
public class ItemService {
    ItemRepository repository;

    @Autowired
    public ItemService(ItemRepository repository) {
        this.repository = repository;
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
}
