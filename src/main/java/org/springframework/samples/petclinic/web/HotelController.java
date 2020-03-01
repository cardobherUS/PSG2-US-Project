package org.springframework.samples.petclinic.web;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Hotel;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HotelController {

	private final ClinicService clinicService;
	
	@Autowired
	public HotelController(ClinicService clinicService) {
		this.clinicService = clinicService;
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
	
	@ModelAttribute("hotel")
	public Hotel loadPetWithVisit(@PathVariable("petId") int petId) {
		Pet pet = this.clinicService.findPetById(petId);
		Hotel hotel = new Hotel();
		pet.addHotel(hotel);
		return hotel;
	}
	
	@GetMapping(value = "/owners/*/pets/{petId}/hotels/new")
	public String initNewVisitForm(@PathVariable("petId") int petId, Map<String, Object> model) {
		return "pets/createOrUpdateHotelForm";
	}
	
	@PostMapping(value = "/owners/{ownerId}/pets/{petId}/hotels/new")
	public String processNewHotelForm(@Valid Hotel hotel, BindingResult result) {
		if(hotel.getFinishDate().isBefore(hotel.getStartDate())) {
			result.addError(new ObjectError("finishDate", "Finish date must be after than start date"));
		}
		if (result.hasErrors()) {
			return "pets/createOrUpdateHotelForm";
		}
		else {
			this.clinicService.saveHotel(hotel);
			return "redirect:/owners/{ownerId}";
		}
	}
	
	@GetMapping(value = "/owners/*/pets/{petId}/hotels")
	public String showHotels(@PathVariable int petId, Map<String, Object> model) {
		model.put("hotels", this.clinicService.findPetById(petId).getHotels());
		return "hotelList";
	}
}
