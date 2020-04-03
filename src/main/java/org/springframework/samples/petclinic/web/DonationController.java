
package org.springframework.samples.petclinic.web;

import java.time.LocalDate;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Cause;
import org.springframework.samples.petclinic.model.Donation;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/causes/{causeId}")
public class DonationController {

	private static final String	VIEWS_DONATION_CREATE_OR_UPDATE_FORM	= "donations/createOrUpdateDonationForm";
	private final ClinicService	clinicService;


	@Autowired
	public DonationController(final ClinicService clinicService) {
		this.clinicService = clinicService;
	}

	@ModelAttribute("cause")
	public Cause findOwner(@PathVariable("causeId") final int causeId) {
		return this.clinicService.findCauseById(causeId);
	}

	@InitBinder("cause")
	public void initCauseBinder(final WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@GetMapping(value = "/donations/new")
	public String initCreationForm(final Cause cause, final ModelMap model) {
		Donation donation = new Donation();
		cause.addDonation(donation);
		donation.setDate(LocalDate.now());
		model.put("donation", donation);
		return DonationController.VIEWS_DONATION_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/donations/new")
	public String processCreationForm(@ModelAttribute final Cause cause, @Valid final Donation donation, final BindingResult result, final ModelMap model) {
		donation.setCause(cause);

		if (cause.getTotalAmount() + donation.getAmount() >= cause.getBudgetTarget()) {
			result.rejectValue("amount", "error.amount", "You cant exceed the budget target of the cause");
		}
		if (result.hasErrors()) {
			model.put("donation", donation);
			return DonationController.VIEWS_DONATION_CREATE_OR_UPDATE_FORM;
		} else {
			this.clinicService.saveDonation(donation);
			cause.addDonation(donation);
			//if (cause.getBudgetTarget() <= this.clinicService.totalBudget(donation.getCause().getId())) {
			this.clinicService.saveCause(cause);
			//}
			return "redirect:/causes";
		}
	}

}
