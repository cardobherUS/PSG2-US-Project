
package org.springframework.samples.petclinic.web;

import java.time.LocalDate;
import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Cause;
import org.springframework.samples.petclinic.model.Donation;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/causes/{causeId}")
public class DonationController {
	
	public ClinicService clinicService;
	
	@Autowired
	public DonationController(ClinicService clinicService) {
		this.clinicService = clinicService;
	}
	
	@GetMapping("/donations/new")
	public String initDonationForm(@PathVariable("causeId") final int causeId, Model model) {
		
		Collection<Owner> clients = this.clinicService.findOwnerByLastName("");
		
		Cause cause = this.clinicService.findCauseById(causeId);
		Donation donation = new Donation();
		donation.setDate(LocalDate.now());
		
		model.addAttribute("cause",cause);
		model.addAttribute("donation",donation);
		model.addAttribute("clients",clients);
		model.addAttribute("maxDonation",findMaxDonation(causeId));
		
		return "donations/createOrUpdateDonationForm";
	}
	
	@PostMapping("/donations/new")
	public String processDonationForm(@Valid Donation donation, BindingResult result,@PathVariable("causeId") final int causeId, ModelMap model) {
		
		Cause cause = this.clinicService.findCauseById(causeId);
		donation.setCause(cause);
		model.put("cause", cause);

		if (result.hasErrors()) {
			
			Collection<Owner> clients = this.clinicService.findOwnerByLastName("");
			
			model.put("donation", donation);
			model.put("clients", clients);
			model.addAttribute("maxDonation",findMaxDonation(causeId));
			
			return "donations/createOrUpdateDonationForm";
		} else {
			try {
				this.clinicService.saveDonation(donation, cause);
				
			}catch(Exception ex) {
				
				Collection<Owner> clients = this.clinicService.findOwnerByLastName("");
				
				model.put("clients", clients);
				model.addAttribute("maxDonation",findMaxDonation(causeId));
				
				result.rejectValue("amount", "error.amount", "You can't exceed the budget target of the cause");
				
				return "donations/createOrUpdateDonationForm";
			}
			return "redirect:/causes";
		}
	
	}
	
	//Derivated methods
	public Integer findMaxDonation(int causeId) {
		Cause cause = this.clinicService.findCauseById(causeId);
		return cause.getBudgetTarget() - cause.getTotalAmount();
		//return maxDonation;
	}
	
}