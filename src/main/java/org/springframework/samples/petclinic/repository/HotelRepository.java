package org.springframework.samples.petclinic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Hotel;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelRepository extends CrudRepository<Hotel, Integer>{

	@Query("select h from Hotel h where h.pet.id=?1")
	List<Hotel> findByPetId(int petId);
	
	Hotel findById(int hotelId);
}
