
package org.springframework.samples.petclinic.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class DonationTests {

	public static Donation donation;
	private static Cause cause;
	


	@BeforeAll
	static void setup() {
		cause = new Cause();
		cause.setId(1);
		cause.setBudgetTarget(1000);
		cause.setDescription("This is a description...");
		cause.setName("Name");
		cause.setOrganization("Organization");
		
		donation = new Donation();
		donation.setAmount(18.0);
		donation.setCause(cause);
		donation.setClient("Client 1");
		donation.setDate(LocalDate.of(2020, 01, 21));
		donation.setId(1);
		donation.setName("Donation 1");
	}
	
//	@Test
//	void shouldGetDescription() {
//		assertThat(cause.getDescription()).isEqualTo("This is a description...");
//	}
	
	@Test
	void shouldGetAmount() {
		assertThat(donation.getAmount()).isEqualTo(18.0);
	}
	
	@Test
	void shouldGetDate() {
		assertThat(donation.getDate()).isEqualTo(LocalDate.of(2020, 01, 21));
	}
	
	@Test
	void shouldGetName() {
		assertThat(donation.getName()).isEqualTo("Donation 1");
	}
	
	@Test
	void shouldGetCause() {
		assertThat(donation.getCause()).isEqualTo(cause);
	}
	
	@Test
	void shouldGetClient() {
		assertThat(donation.getClient()).isEqualTo("Client 1");
	}

}
