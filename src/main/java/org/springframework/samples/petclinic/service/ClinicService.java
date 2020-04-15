
/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License")
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.samples.petclinic.exceptions.BudgetMaximumException;
import org.springframework.samples.petclinic.model.Cause;
import org.springframework.samples.petclinic.model.Donation;
import org.springframework.samples.petclinic.model.Hotel;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.Specialty;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.model.Visit;
import org.springframework.samples.petclinic.repository.CauseRepository;
import org.springframework.samples.petclinic.repository.DonationRepository;
import org.springframework.samples.petclinic.repository.HotelRepository;
import org.springframework.samples.petclinic.repository.OwnerRepository;
import org.springframework.samples.petclinic.repository.PetRepository;
import org.springframework.samples.petclinic.repository.VetRepository;
import org.springframework.samples.petclinic.repository.VisitRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Mostly used as a facade for all Petclinic controllers Also a placeholder
 * for @Transactional and @Cacheable annotations
 *
 * @author Michael Isvy
 */
@Service
public class ClinicService {

	private PetRepository		petRepository;

	private VetRepository		vetRepository;

	private OwnerRepository		ownerRepository;

	private VisitRepository		visitRepository;

	private HotelRepository		hotelRepository;

	private CauseRepository		causeRepository;

	private DonationRepository	donationRepository;


	@Autowired
	public ClinicService(final PetRepository petRepository, final VetRepository vetRepository, final OwnerRepository ownerRepository, final VisitRepository visitRepository, final HotelRepository hotelRepository, final CauseRepository causeRepository,
		final DonationRepository donationRepository) {
		this.petRepository = petRepository;
		this.vetRepository = vetRepository;
		this.ownerRepository = ownerRepository;
		this.visitRepository = visitRepository;
		this.hotelRepository = hotelRepository;
		this.causeRepository = causeRepository;
		this.donationRepository = donationRepository;
	}

	@Transactional(readOnly = true)
	public Collection<PetType> findPetTypes() {
		return this.petRepository.findPetTypes();
	}

	@Transactional(readOnly = true)
	public Owner findOwnerById(final int id) {
		return this.ownerRepository.findById(id);
	}

	@Transactional(readOnly = true)
	public Collection<Owner> findOwnerByLastName(final String lastName) {
		return this.ownerRepository.findByLastName(lastName);
	}

	@Transactional
	public void saveOwner(final Owner owner) {
		this.ownerRepository.save(owner);
	}

	@Transactional
	public void deletePet(final Pet pet) {
		this.visitRepository.deleteAll(pet.getVisits());
		this.hotelRepository.deleteAll(pet.getHotels());
		this.petRepository.delete(pet);
	}

	@Transactional
	public void saveVisit(final Visit visit) {
		this.visitRepository.save(visit);
	}

	@Transactional(readOnly = true)
	public Pet findPetById(final int id) {
		return this.petRepository.findById(id);
	}

	@Transactional
	public void savePet(final Pet pet) {
		this.petRepository.save(pet);
	}

	@Transactional(readOnly = true)
	@Cacheable(value = "vets")
	public Collection<Vet> findVets() {
		return this.vetRepository.findAll();
	}

	public Collection<Visit> findVisitsByPetId(final int petId) {
		return this.visitRepository.findByPetId(petId);
	}

	@Transactional
	public void saveVet(final Vet vet) {
		this.vetRepository.save(vet);
	}

	public Vet findVetById(final int vetId) {
		return this.vetRepository.findVetById(vetId);
	}

	@Transactional(readOnly = true)
	public Collection<Specialty> findSpecialities() {
		return this.vetRepository.findSpecialityTypes();
	}

	@Transactional
	public void saveHotel(final Hotel hotel) {
		this.hotelRepository.save(hotel);
	}

	public Collection<Hotel> findHotelsByPetId(final int petId) {
		return this.hotelRepository.findByPetId(petId);
	}

	public void deleteOwner(final Owner owner) {
		owner.getPets().forEach(x -> this.visitRepository.deleteAll(x.getVisits()));
		owner.getPets().forEach(x -> this.hotelRepository.deleteAll(x.getHotels()));
		this.ownerRepository.delete(owner);
	}

	public void deleteVet(final Vet vet) {
		this.vetRepository.delete(vet);

	}

	public Visit findById(final int vetId) {
		return this.visitRepository.findById(vetId);
	}

	public void deleteVisit(final Visit visit) {
		this.visitRepository.delete(visit);
	}

	public boolean isDuplicatedDni(final String dni) {
		return this.ownerRepository.isDuplicatedDniOwner(dni) || this.vetRepository.isDuplicatedDniVet(dni);
	}

	public void deleteHotel(final Hotel hotel) {
		this.hotelRepository.delete(hotel);
	}

	public Hotel findHotelById(final int hotelId) {
		return this.hotelRepository.findById(hotelId);
	}

	@Transactional(readOnly = true)
	public Iterable<Cause> findCauses() {
		return this.causeRepository.findAll();
	}

	public boolean canHotelBook(final int petId) {
		return this.hotelRepository.canBookHotelByPetId(petId);
	}

	public Cause findCauseById(final int causeId) {
		return this.causeRepository.findByCauseId(causeId);
	}

	public Collection<Donation> findAllDonationsByCauseId(final int causeId) {
		return this.donationRepository.findAllDonationsByCauseId(causeId);
	}

	@Transactional
	public void saveCause(final Cause cause) {
		this.causeRepository.save(cause);
	}

	public boolean causeNameAlreadyExists(final String name) {
		return this.causeRepository.findCauseWithName(name).orElse(null) != null;
	}

	public Double totalBudget(final int causeId) {
		return this.causeRepository.totalBudget(causeId);
	}

	public void saveDonation(final Donation donation, Cause cause) throws BudgetMaximumException {
		if(donation.getAmount()+cause.getTotalAmount()>cause.getBudgetTarget()) {
			throw new BudgetMaximumException();
		}else {
		this.donationRepository.save(donation);
		}
	}
}
