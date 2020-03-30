
package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "causes")
public class Cause extends NamedEntity {

	@Column(name = "description")
	private String	description;

	@Column(name = "budget_target")
	private Integer	budgetTarget;

	@Column(name = "organization")
	private String	organization;


	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public Integer getBudgetTarget() {
		return this.budgetTarget;
	}

	public void setBudgetTarget(final Integer budgetTarget) {
		this.budgetTarget = budgetTarget;
	}

	public String getOrganization() {
		return this.organization;
	}

	public void setOrganization(final String organization) {
		this.organization = organization;
	}
}
