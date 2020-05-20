
package org.springframework.samples.petclinic.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;

@Entity
@Table(name = "causes")
public class Cause extends NamedEntity {

	@NotEmpty
	@Column(name = "description")
	private String	description;

	@NotNull
	@Range(min=1,max=1000000)
	@Column(name = "budget_target")
	private Integer	budgetTarget;

	@NotEmpty
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


	@OneToMany(cascade = CascadeType.ALL, mappedBy = "cause")
	private Set<Donation> donations;


	protected Set<Donation> getDonationsInternal() {
		if (this.donations == null) {
			this.donations = new HashSet<>();
		}
		return this.donations;
	}

	public List<Donation> getDonations() {
		List<Donation> sortedDonations = new ArrayList<>(this.getDonationsInternal());
		PropertyComparator.sort(sortedDonations, new MutableSortDefinition("date", false, false));
		return Collections.unmodifiableList(sortedDonations);
	}

	public Integer getTotalAmount() {
		return this.donations.stream().mapToInt(Donation::getAmount).sum();
		
	}

	public void addDonation(final Donation donation) {
		this.getDonationsInternal().add(donation);
		donation.setCause(this);
	}
}
