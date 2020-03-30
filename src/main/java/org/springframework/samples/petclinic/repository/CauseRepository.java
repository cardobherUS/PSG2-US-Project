
package org.springframework.samples.petclinic.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Cause;
import org.springframework.stereotype.Repository;

@Repository
public interface CauseRepository extends CrudRepository<Cause, Integer> {

	@Override
	Iterable<Cause> findAll();

}
