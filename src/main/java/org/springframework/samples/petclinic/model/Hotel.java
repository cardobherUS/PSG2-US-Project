package org.springframework.samples.petclinic.model;

import java.time.LocalDate;




import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;


@Entity
@Table(name = "hotels")
public class Hotel extends BaseEntity{

	@DateTimeFormat(pattern = "yyyy/MM/dd")
	@Column(name = "start_date")
	@NotNull
	@FutureOrPresent
	private LocalDate startDate;
	
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	@Column(name = "finish_date")
	@NotNull
	@FutureOrPresent
	private LocalDate finishDate;
	
	@ManyToOne
	@JoinColumn(name = "pet_id")
	private Pet pet;
	
	public Pet getPet() {
		return this.pet;
	}
	
	public void setPet(Pet pet) {
		this.pet = pet;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(LocalDate finishDate) {
		this.finishDate = finishDate;
	}

}
