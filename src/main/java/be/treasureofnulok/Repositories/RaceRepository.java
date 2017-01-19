package be.treasureofnulok.Repositories;

import be.treasureofnulok.Models.Item;
import be.treasureofnulok.Models.Race;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Jerry-Lee on 18/01/2017.
 */
public interface RaceRepository extends JpaRepository<Race,Long> {
    Page<Race> findAll(Pageable pageable);
    Race findByNameIgnoringCase(String name);
    Race findRaceById(Long id);
}
