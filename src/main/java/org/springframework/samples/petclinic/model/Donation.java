
package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "donations")
public class Donation extends NamedEntity {

	@NotNull
	@Column(name = "amount")
	@Min(0)
	private Double		amount;

	@NotNull
	@Column(name = "donation_date")
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate	date;

	@NotBlank
	@Column(name = "client")
	private String		client;

	@ManyToOne
	@JoinColumn(name = "cause_id")
	private Cause		cause;


	public Double getAmount() {
		return this.amount;
	}

	public void setAmount(final Double amount) {
		this.amount = amount;
	}

	public LocalDate getDate() {
		return this.date;
	}

	public void setDate(final LocalDate date) {
		this.date = date;
	}

	public String getClient() {
		return this.client;
	}

	public void setClient(final String client) {
		this.client = client;
	}

	public Cause getCause() {
		return this.cause;
	}

	public void setCause(final Cause cause) {
		this.cause = cause;
	}

}
