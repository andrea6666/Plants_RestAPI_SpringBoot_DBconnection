package com.example.andrea.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.example.andrea.entity.Plant;
import com.example.andrea.repository.PlantRepository;


/**
 * Created by: Andrea
 * 
 *  PlantController class is a Spring RestController that handles HTTP requests related to plants.
 * It includes methods for CRUD operations (Create, Read, Update, Delete) on the Plant entity.
 */

@RestController
@RequestMapping("/plants/")
public class PlantController {
	
	 // Autowire the PlantRepository to interact with the database
	@Autowired
	PlantRepository repo;
	
	//get all the plants
	//http://localhost:8080/plants/
	@GetMapping()
	public ResponseEntity<Object>  getAllPlants(){
		
		// Retrieve all plants from the repository
		 List<Plant> plants = repo.findAll();

	        if (plants.isEmpty()) {
	            // Return a string message with a 404 status if the list is empty
	            return new ResponseEntity<>("No plants found", HttpStatus.NOT_FOUND);
	        } else {
	        
	            // Return a string message with a 200 status
	        	return ResponseEntity.ok(plants);
	        }
		
	}
	


	
 
	
	//get plant by id
	//http://localhost:8080/plants/{id}
	@GetMapping("{id}")
	public ResponseEntity<Object> getPlantById(@PathVariable int id){
		
        // Retrieve a plant by its ID from the repository
		  Optional<Plant> optionalPlant = repo.findById(id);
		  
		   if (optionalPlant.isPresent()) {
	            // If the plant is found, returns it with a 200 status
	            Plant plant = optionalPlant.get();
	            return ResponseEntity.ok(plant);
	        } else {
	            // Return a 404 status with a message indicating that the plant with the specified ID is not found
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Plant not found with id: " + id);
	        }
		
		
	}
	
	//get plant by name
	//http://localhost:8080/plants/name/"name"
	 @GetMapping("name/{name}")
	    public ResponseEntity<Object> getPlantByName(@PathVariable String name) {
	        // Retrieve a plant by its name from the repository
	        Optional<Plant> optionalPlant = repo.findByName(name);

	        if (optionalPlant.isPresent()) {
	            // If the plant is found, returns it with a 200 status
	            Plant plant = optionalPlant.get();
	            return ResponseEntity.ok(plant);
	        } else {
	            // Return a 404 status with a message indicating that the plant with the specified name is not found
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Plant not found with name: " + name);
	        }
	    }
	 
	//get plant by content  (searches in any field)
	//http://localhost:8080/plants/search/{content}
	@GetMapping("search/{content}")
	public ResponseEntity<Object> getPlantByContent(@PathVariable String content) {
        // Retrieve plants that match the specified content from the repository
	    List<Plant> matchingPlants = repo.findByContentInAnyField(content);

	    if (!matchingPlants.isEmpty()) {
            // If matching plants are found, return them with a 200 status
	        return ResponseEntity.ok(matchingPlants);
	    } else {
            // Return a 404 status with a message indicating that no plants were found containing the specified content
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No plants found containing: " + content);
	    }
	}

	
	//create plant a new plant
	//http://localhost:8080/plants/add
	@PostMapping("add")
	@ResponseStatus(code = HttpStatus.CREATED)
	public ResponseEntity<String> createPlant(@RequestBody Plant plant) {
		// Save the new plant to the repository
	    Plant savedPlant = repo.save(plant);
	    return ResponseEntity.status(HttpStatus.CREATED).body("Plant with ID " + savedPlant.getId() + " is created");
	}
	
	
	
	//update plants an existing plant
	//http://localhost:8080/plants/update/id
	@ResponseStatus(code = HttpStatus.OK)
	@PutMapping("{id}")
	public ResponseEntity<String> updatePlant(@PathVariable int id, @RequestBody Plant updatedPlant) {
	    Optional<Plant> optionalPlant = repo.findById(id);

	    if (optionalPlant.isPresent()) {
	        Plant plant = optionalPlant.get();

	        // Update the plant fields with the values from the request body
	        plant.setName(updatedPlant.getName());
	        plant.setWateringDate(updatedPlant.getWateringDate());
	        plant.setInstructions(updatedPlant.getInstructions());

	        // Save the updated plant
	        repo.save(plant);

	        return ResponseEntity.status(HttpStatus.OK).body("Plant with ID " + id + " is updated");
	    } else {
	        // Return a 404 status if the plant with the specified ID is not found
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Plant with ID " + id + " not found");
	    }
	}

	
	//deletes existing plant plant chosen by id
	//http://localhost:8080/plants/delete/id
	@DeleteMapping("{id}")
	public ResponseEntity<String> deletePlant(@PathVariable int id) {
	    Optional<Plant> optionalPlant = repo.findById(id);

	    if (optionalPlant.isPresent()) {
	        Plant plant = optionalPlant.get();

	        // Delete the plant
	        repo.delete(plant);

	        // Return a 200 status with a message indicating that the plant is deleted
	        return ResponseEntity.status(HttpStatus.OK).body("Plant with ID " + id + " is deleted");
	    } else {
	        // Return a 404 status with a message indicating that the plant with the specified ID is not found
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Plant with ID " + id + " not found");
	    }
	}
	
	// deletes the all plants
	// http://localhost:8080/plants/delete/all
	@DeleteMapping("delete/all")
	public ResponseEntity<String> deleteAllPlants() {
	    List<Plant> plants = repo.findAll();

	    if (!plants.isEmpty()) {
	        // Delete all the plants
	        repo.deleteAll();

	        // Return a 200 status with a message indicating that all the plants are deleted
	        return ResponseEntity.status(HttpStatus.OK).body("All plants are deleted");
	    } else {
	        // Return a 404 status with a message indicating that there are no plants
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No plants found");
	    }
	}



}
