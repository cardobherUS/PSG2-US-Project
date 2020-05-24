/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
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

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
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
import org.springframework.samples.petclinic.util.EntityUtils;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration test of the Service and the Repository layer.
 * <p>
 * ClinicServiceSpringDataJpaTests subclasses benefit from the following services provided
 * by the Spring TestContext Framework:
 * </p>
 * <ul>
 * <li><strong>Spring IoC container caching</strong> which spares us unnecessary set up
 * time between test execution.</li>
 * <li><strong>Dependency Injection</strong> of test fixture instances, meaning that we
 * don't need to perform application context lookups. See the use of
 * {@link Autowired @Autowired} on the <code>{@link
 * ClinicServiceTests#clinicService clinicService}</code> instance variable, which uses
 * autowiring <em>by type</em>.
 * <li><strong>Transaction management</strong>, meaning each test method is executed in
 * its own transaction, which is automatically rolled back by default. Thus, even if tests
 * insert or otherwise change database state, there is no need for a teardown or cleanup
 * script.
 * <li>An {@link org.springframework.context.ApplicationContext ApplicationContext} is
 * also inherited and can be used for explicit bean lookup if necessary.</li>
 * </ul>
 *
 * @author Ken Krebs
 * @author Rod Johnson
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @author Michael Isvy
 * @author Dave Syer
 */

@ExtendWith(SpringExtension.class)
@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class ClinicServiceTests {

	@Autowired
	protected ClinicService clinicService;


	@Test
	void shouldFindOwnersByLastName() {
		Collection<Owner> owners = this.clinicService.findOwnerByLastName("Davis");
		Assertions.assertThat(owners.size()).isEqualTo(2);

		owners = this.clinicService.findOwnerByLastName("Daviss");
		Assertions.assertThat(owners.isEmpty()).isTrue();
	}

	@Test
	void shouldFindSingleOwnerWithPet() {
		Owner owner = this.clinicService.findOwnerById(1);
		Assertions.assertThat(owner.getLastName()).startsWith("Franklin");
		Assertions.assertThat(owner.getPets().size()).isEqualTo(1);
		Assertions.assertThat(owner.getPets().get(0).getType()).isNotNull();
		Assertions.assertThat(owner.getPets().get(0).getType().getName()).isEqualTo("cat");
	}

//	@Test
//	@Transactional
//	public void shouldInsertOwner() {
//		Collection<Owner> owners = this.clinicService.findOwnerByLastName("Schultz");
//		int found = owners.size();
//
//		Owner owner = new Owner();
//		owner.setFirstName("Sam");
//		owner.setLastName("Schultz");
//		owner.setAddress("4, Evans Street");
//		owner.setCity("Wollongong");
//		owner.setDni("54933465A");
//		owner.setTelephone("691758496");
//		this.clinicService.saveOwner(owner);
//		Assertions.assertThat(owner.getId().longValue()).isNotEqualTo(0);
//
//		owners = this.clinicService.findOwnerByLastName("Schultz");
//		Assertions.assertThat(owners.size()).isEqualTo(found + 1);
//	}

//	@Test
//	@Transactional
//	void shouldUpdateOwner() {
//		Owner owner = this.clinicService.findOwnerById(1);
//		String oldLastName = owner.getLastName();
//		String newLastName = oldLastName + "X";
//
//		owner.setLastName(newLastName);
//		this.clinicService.saveOwner(owner);
//
//		// retrieving new name from database
//		owner = this.clinicService.findOwnerById(1);
//		Assertions.assertThat(owner.getLastName()).isEqualTo(newLastName);
//	}

	@Test
	void shouldFindPetWithCorrectId() {
		Pet pet7 = this.clinicService.findPetById(7);
		Assertions.assertThat(pet7.getName()).startsWith("Samantha");
		Assertions.assertThat(pet7.getOwner().getFirstName()).isEqualTo("Jean");

	}

	@Test
	void shouldFindAllPetTypes() {
		Collection<PetType> petTypes = this.clinicService.findPetTypes();

		PetType petType1 = EntityUtils.getById(petTypes, PetType.class, 1);
		Assertions.assertThat(petType1.getName()).isEqualTo("cat");
		PetType petType4 = EntityUtils.getById(petTypes, PetType.class, 4);
		Assertions.assertThat(petType4.getName()).isEqualTo("snake");
	}

	@Test
	@Transactional
	public void shouldInsertPetIntoDatabaseAndGenerateId() {
		Owner owner6 = this.clinicService.findOwnerById(6);
		int found = owner6.getPets().size();

		Pet pet = new Pet();
		pet.setName("bowser");
		Collection<PetType> types = this.clinicService.findPetTypes();
		pet.setType(EntityUtils.getById(types, PetType.class, 2));
		pet.setBirthDate(LocalDate.now());
		owner6.addPet(pet);
		Assertions.assertThat(owner6.getPets().size()).isEqualTo(found + 1);

		this.clinicService.savePet(pet);
		this.clinicService.saveOwner(owner6);

		owner6 = this.clinicService.findOwnerById(6);
		Assertions.assertThat(owner6.getPets().size()).isEqualTo(found + 1);
		// checks that id has been generated
		Assertions.assertThat(pet.getId()).isNotNull();
	}

	@Test
	@Transactional
	public void shouldUpdatePetName() throws Exception {
		Pet pet7 = this.clinicService.findPetById(7);
		String oldName = pet7.getName();

		String newName = oldName + "X";
		pet7.setName(newName);
		this.clinicService.savePet(pet7);

		pet7 = this.clinicService.findPetById(7);
		Assertions.assertThat(pet7.getName()).isEqualTo(newName);
	}

	@Test
	void shouldFindVets() {
		Collection<Vet> vets = this.clinicService.findVets();

		Vet vet = EntityUtils.getById(vets, Vet.class, 3);
		Assertions.assertThat(vet.getLastName()).isEqualTo("Douglas");
		Assertions.assertThat(vet.getNrOfSpecialties()).isEqualTo(2);
		Assertions.assertThat(vet.getSpecialties().get(0).getName()).isEqualTo("dentistry");
		Assertions.assertThat(vet.getSpecialties().get(1).getName()).isEqualTo("surgery");
	}

	@Test
	@Transactional
	public void shouldAddNewVisitForPet() {
		Pet pet7 = this.clinicService.findPetById(7);
		int found = pet7.getVisits().size();
		Visit visit = new Visit();
		pet7.addVisit(visit);
		visit.setDescription("test");
		this.clinicService.saveVisit(visit);
		this.clinicService.savePet(pet7);

		pet7 = this.clinicService.findPetById(7);
		Assertions.assertThat(pet7.getVisits().size()).isEqualTo(found + 1);
		Assertions.assertThat(visit.getId()).isNotNull();
	}

	@Test
	void shouldFindVisitsByPetId() throws Exception {
		Collection<Visit> visits = this.clinicService.findVisitsByPetId(7);
		Assertions.assertThat(visits.size()).isEqualTo(2);
		Visit[] visitArr = visits.toArray(new Visit[visits.size()]);
		Assertions.assertThat(visitArr[0].getPet()).isNotNull();
		Assertions.assertThat(visitArr[0].getDate()).isNotNull();
		Assertions.assertThat(visitArr[0].getPet().getId()).isEqualTo(7);
	}

	@Test
	void shouldDeletePet() throws Exception {
		Pet pet = this.clinicService.findPetById(1);
		Owner owner = this.clinicService.findOwnerById(1);
		List<Pet> petsIni = owner.getPets();
		int tamInicial = petsIni.size();
		this.clinicService.deletePet(pet);
		owner = this.clinicService.findOwnerById(1);
		List<Pet> pets = owner.getPets();
		Assertions.assertThat(pets.size()).isEqualTo(tamInicial - 1);
	}

	@Test
	@Transactional
	public void shouldSaveVisit() {
		Collection<Visit> visits = this.clinicService.findVisitsByPetId(1);
		int found = visits.size();

		Visit visit = new Visit();
		Pet pet = this.clinicService.findPetById(1);
		visit.setDate(LocalDate.of(2020, 07, 10));
		visit.setDescription("This is a text");
		visit.setPet(pet);

		this.clinicService.saveVisit(visit);
		Assertions.assertThat(visit.getId().longValue()).isNotEqualTo(0);

		visits = this.clinicService.findVisitsByPetId(1);
		Assertions.assertThat(visits.size()).isEqualTo(found + 1);
	}

	@Test
	@Transactional
	public void shouldSaveHotel() {
		Collection<Hotel> hotels = this.clinicService.findHotelsByPetId(1);
		int found = hotels.size();
		Pet pet = this.clinicService.findPetById(1);

		Hotel hotel = new Hotel();
		hotel.setPet(pet);
		hotel.setStartDate(LocalDate.of(2020, 07, 20));
		hotel.setFinishDate(LocalDate.of(2020, 07, 25));

		this.clinicService.saveHotel(hotel);
		Assertions.assertThat(hotel.getId().longValue()).isNotEqualTo(0);

		hotels = this.clinicService.findHotelsByPetId(1);
		Assertions.assertThat(hotels.size()).isEqualTo(found + 1);
	}

	@Test
	public void shouldFindHotelsByPetId() {
		Collection<Hotel> hotels = this.clinicService.findHotelsByPetId(1);
		Assertions.assertThat(hotels.size()).isEqualTo(1);
	}

	@Test
	public void shouldDeleteOwner() {
		Owner owner = this.clinicService.findOwnerById(1);
		Collection<Owner> ownersIni = this.clinicService.findOwnerByLastName("");
		int tamInicial = ownersIni.size();
		this.clinicService.deleteOwner(owner);
		Collection<Owner> owners = this.clinicService.findOwnerByLastName("");
		Assertions.assertThat(owners.size()).isEqualTo(tamInicial - 1);
	}

	@Test
	public void shouldDeleteVet() {
		Vet vet = this.clinicService.findVetById(1);
		Collection<Vet> vetsIni = this.clinicService.findVets();
		int tamInicial = vetsIni.size();
		this.clinicService.deleteVet(vet);
		Collection<Vet> vets = this.clinicService.findVets();
		Assertions.assertThat(vets.size()).isEqualTo(tamInicial - 1);
	}

	@Test
	void shouldFindVisit() {
		Visit visit = this.clinicService.findById(1);
		Assertions.assertThat(visit.getDate()).isEqualTo("2013-01-01");
		Assertions.assertThat(visit.getDescription()).isEqualTo("rabies shot");
		Assertions.assertThat(visit.getPet()).isNotNull();
	}

	@Test
	public void shouldDeleteVisit() {
		Visit visit = this.clinicService.findById(1);
		Collection<Visit> visitsIni = this.clinicService.findVisitsByPetId(7);
		int tamInicial = visitsIni.size();
		this.clinicService.deleteVisit(visit);
		Collection<Visit> visits = this.clinicService.findVisitsByPetId(7);
		Assertions.assertThat(visits.size()).isEqualTo(tamInicial - 1);
	}

	@Test
	public void shouldDeleteHotel() {
		Hotel hotel = this.clinicService.findHotelById(1);
		Collection<Hotel> hotelsIni = this.clinicService.findHotelsByPetId(1);
		int tamInicial = hotelsIni.size();
		this.clinicService.deleteHotel(hotel);
		Collection<Hotel> hotels = this.clinicService.findHotelsByPetId(1);
		Assertions.assertThat(hotels.size()).isEqualTo(tamInicial - 1);
	}

	@Test
	void shouldFindHotel() {
		Hotel hotel = this.clinicService.findHotelById(1);
		Assertions.assertThat(hotel.getFinishDate()).isEqualTo("2020-05-12");
		Assertions.assertThat(hotel.getStartDate()).isEqualTo("2020-05-10");
		Assertions.assertThat(hotel.getPet()).isNotNull();
	}

	@Test
	void shouldFindCauses() {
		Collection<Cause> causes = (Collection<Cause>) this.clinicService.findCauses();
		Assertions.assertThat(causes.size()).isEqualTo(4);
	}

	@Test
	void shouldFindCauseById() {
		Cause cause = this.clinicService.findCauseById(1);
		Assertions.assertThat(cause.getBudgetTarget()).isEqualTo(500);
		Assertions.assertThat(cause.getDescription()).isEqualTo("This is a description");
		Assertions.assertThat(cause.getName()).isEqualTo("Cause1");
		Assertions.assertThat(cause.getOrganization()).isEqualTo("Organization1");
	}

	@Test
	void shouldFindDonationsByCauseId() {
		Collection<Donation> donations = this.clinicService.findAllDonationsByCauseId(1);
		Assertions.assertThat(donations.size()).isEqualTo(2);
	}

	@Test
	void shouldFindSpecialties() {
		Collection<Specialty> specialties = this.clinicService.findSpecialities();
		Assertions.assertThat(specialties.size()).isEqualTo(3);
	}

	@Test
	void shouldBeDuplicatedDni() {
		String dni = "12345678A";
		Boolean res = this.clinicService.isDuplicatedDni(dni);
		Assertions.assertThat(res).isEqualTo(true);
		dni = "89735039F";
		res = this.clinicService.isDuplicatedDni(dni);
		Assertions.assertThat(res).isEqualTo(false);
	}

	@Test
	void shouldCanBookHotel() {
		Boolean res = this.clinicService.canHotelBook(1);
		Assertions.assertThat(res).isEqualTo(true);
	}

	@Test
	void shouldCauseWithTheSameName() {
		Boolean res = this.clinicService.causeNameAlreadyExists("Cause1");
		Assertions.assertThat(res).isEqualTo(true);
	}

	@Test
	void shouldTotalBudget() {
		Integer res = this.clinicService.totalBudget(1);
		
		Assertions.assertThat(res).isEqualTo(114);
	}

	@Test
	@Transactional
	public void shouldSaveVet() {
		Collection<Vet> vets = this.clinicService.findVets();
		int found = vets.size();
		List<Specialty> specialties = (List<Specialty>) this.clinicService.findSpecialities();

		Vet vet = new Vet();
		vet.setDni("58393485G");
		vet.setFirstName("David");
		vet.setLastName("Lewis");
		vet.setSpecialties(specialties);

		this.clinicService.saveVet(vet);
		Assertions.assertThat(vet.getId().longValue()).isNotEqualTo(0);

		vets = this.clinicService.findVets();
		Assertions.assertThat(vets.size()).isEqualTo(found + 1);
	}

	@Test
	@Transactional
	public void shouldSaveCause() {
		Collection<Cause> causes = (Collection<Cause>) this.clinicService.findCauses();
		int found = causes.size();

		Cause cause = new Cause();
		cause.setBudgetTarget(500);
		cause.setDescription("This is a text");
		cause.setName("Cause");
		cause.setOrganization("Organization");

		this.clinicService.saveCause(cause);
		Assertions.assertThat(cause.getId().longValue()).isNotEqualTo(0);

		causes = (Collection<Cause>) this.clinicService.findCauses();
		Assertions.assertThat(causes.size()).isEqualTo(found + 1);
	}

	@Test
	@Transactional
	public void shouldSaveDonation() {
		Collection<Donation> donations = this.clinicService.findAllDonationsByCauseId(1);
		int found = donations.size();
		Cause cause = this.clinicService.findCauseById(1);

		Donation donation = new Donation();
		donation.setAmount(20);
		donation.setCause(cause);
		donation.setClient("George");
		donation.setDate(LocalDate.of(2020, 03, 14));
		donation.setName("Donation");

		try {
			this.clinicService.saveDonation(donation,cause);
		} catch (BudgetMaximumException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Assertions.assertThat(donation.getId().longValue()).isNotEqualTo(0);

		donations = this.clinicService.findAllDonationsByCauseId(1);
		Assertions.assertThat(donations.size()).isEqualTo(found + 1);
	}

}
