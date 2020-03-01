package org.springframework.samples.petclinic.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Hotel;
import org.springframework.samples.petclinic.model.Visit;

public interface HotelRepository {

	void save(Hotel hotel) throws DataAccessException;
	
	List<Hotel> findByPetId(Integer petId);
}
