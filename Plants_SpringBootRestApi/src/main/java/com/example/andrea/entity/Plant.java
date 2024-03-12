package com.example.andrea.entity;


import org.hibernate.annotations.CreationTimestamp;
import java.sql.Date;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Created by: Andrea
 * 
 *  Plant class represents the entity for plants in the application.
 *  It is annotated with JPA annotations to define its mapping to the database table, it is creating a database table.
 */

@Entity
@Table(name = "plants")
public class Plant {
	
	public Plant() {
		
	}

	// Plant ID, generated automatically
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name = "plant_id")
	private int id;
	@Column(name="plant_name")
	private String name;
	@Column(name="plant_instructions", columnDefinition = "TEXT")
	private String instructions;
	
	// Plant watering date, annotated with @CreationTimestamp to automatically set the creation timestamp
    @CreationTimestamp
    @Column(name = "plant_wateringDate", updatable = false)
	private Date wateringDate;
	

 // Getter and setter methods for the Plant properties
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getInstructions() {
		return instructions;
	}
	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}
	public Date getWateringDate() {
		return wateringDate;
	}
	public void setWateringDate(Date wateringDate) {
		 this.wateringDate = wateringDate;
	}
	

	
	
	

}
