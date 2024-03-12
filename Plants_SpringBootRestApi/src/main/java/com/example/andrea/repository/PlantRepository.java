package com.example.andrea.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.example.andrea.entity.Plant;

/**
 * Created by: Andrea
 * 
 * PlantRepository interface extends JpaRepository to provide CRUD operations for the Plant entity.
 * JpaRepository<Plant, Integer> indicates that it works with the Plant entity and uses Integer as the type of the primary key.
 */

public interface PlantRepository extends JpaRepository<Plant, Integer>{
	
	 // Method to find a plant by its name
	Optional<Plant> findByName(String name);
	
	 // Custom query to search for plants based on a content string in any field (id, name, instructions, wateringDate)
	@Query("SELECT p FROM Plant p WHERE LOWER(CONCAT(p.id, ' ', p.name, ' ', p.instructions, ' ', p.wateringDate)) LIKE LOWER(CONCAT('%', :content, '%'))")
	List<Plant> findByContentInAnyField(@Param("content") String content);

}
