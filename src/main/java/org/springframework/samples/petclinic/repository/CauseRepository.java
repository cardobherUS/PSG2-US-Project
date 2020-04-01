
package org.springframework.samples.petclinic.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Cause;
import org.springframework.stereotype.Repository;

@Repository
public interface CauseRepository extends CrudRepository<Cause, Integer> {

	@Override
	Iterable<Cause> findAll();

	@Query("select c from Cause c where c.name = ?1")
	Optional<Cause> findCauseWithName(String name);

}
