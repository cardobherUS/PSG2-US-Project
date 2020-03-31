package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Donation;
import org.springframework.stereotype.Repository;

@Repository
public interface DonationRepository extends CrudRepository<Donation, Integer> {

	@Query("select d from Donation d where d.cause.id=:causeId")
	Collection<Donation> findAllDonationsByCauseId(@Param(value = "causeId") int causeId);
	
}
