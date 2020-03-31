
package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Cause;
import org.springframework.samples.petclinic.model.Donation;
import org.springframework.stereotype.Repository;

@Repository
public interface CauseRepository extends CrudRepository<Cause, Integer> {

	@Query("select c from Cause c where c.id=:causeId")
	Cause findByCauseId(@Param(value = "causeId") int causeId);
	
	@Query("select d from Donation d where d.cause.id=:causeId")
	Collection<Donation> findDonations(@Param(value = "causeId") int causeId);
	
	@Override
	Iterable<Cause> findAll();

}