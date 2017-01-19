package be.treasureofnulok.Services;

import be.treasureofnulok.Models.Storage;
import be.treasureofnulok.Repositories.StorageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Jerry-Lee on 19/01/2017.
 */
@Service
public class StorageService {
    @Autowired
    StorageRepository repository;

    public Storage findStorageById(String id) {
        return repository.findStorageById(id);
    }
}
