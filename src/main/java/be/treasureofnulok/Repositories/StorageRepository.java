package be.treasureofnulok.Repositories;

import be.treasureofnulok.Models.Storage;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Jerry-Lee on 19/01/2017.
 */
public interface StorageRepository extends JpaRepository<Storage, String> {
    Storage findStorageById(String id);
}
