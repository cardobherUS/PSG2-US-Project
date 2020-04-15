package org.springframework.samples.petclinic.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class CauseTests {
	
	private static Cause cause;
	
	@BeforeAll
	static void setup() {
		cause = new Cause();
		cause.setId(1);
		cause.setBudgetTarget(1000);
		cause.setDescription("This is a description...");
		cause.setName("Name");
		cause.setOrganization("Organization");
	}
	
	@Test
	void shouldGetDescription() {
		assertThat(cause.getDescription()).isEqualTo("This is a description...");
	}
	
	@Test
	void shouldGetBudgetTarget() {
		assertThat(cause.getBudgetTarget()).isEqualTo(1000);
	}
	
	@Test
	void shouldGetName() {
		assertThat(cause.getName()).isEqualTo("Name");
	}
	
	@Test
	void shouldGetOrganization() {
		assertThat(cause.getOrganization()).isEqualTo("Organization");
	}
	
}
