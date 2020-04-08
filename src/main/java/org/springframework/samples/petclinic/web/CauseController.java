
package org.springframework.samples.petclinic.web;

import java.util.Collection;
import java.util.Map;
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
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/causes")
public class CauseController {

	private final ClinicService clinicService;


	@Autowired
	private CauseController(final ClinicService clinicService) {
		this.clinicService = clinicService;
	}
	
	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
	
	@GetMapping(value = "/new")
	public String initCreationForm(Map<String, Object> model) {
		Cause cause = new Cause();
		model.put("cause", cause);
		return "causes/createCauseForm";
	}

	@PostMapping(value = "/new")
	public String processCreationForm(@Valid Cause cause, BindingResult result) {
		if(this.clinicService.causeNameAlreadyExists(cause.getName())) {
			result.rejectValue("name", "duplicatedCauseName", "This cause has already been created");
		}
		if (result.hasErrors()) {
			return "causes/createCauseForm";
		}
		else {
			this.clinicService.saveCause(cause);
			return "redirect:/causes";
		}
	}

	@GetMapping()
	public String mostrarCauses(final ModelMap modelMap) {
		String vista = "causes/causesList";
		Iterable<Cause> causes = this.clinicService.findCauses();
		modelMap.addAttribute("causes", causes);
		return vista;
	}
	
  @GetMapping("/{causeId}")
  public String showCause(@PathVariable("causeId") int causeId, Map<String, Object> model) {
   Cause cause = this.clinicService.findCauseById(causeId);
   model.put("cause", cause);
   Collection<Donation> donations = this.clinicService.findAllDonationsByCauseId(causeId);
   model.put("donations", donations);
     return "causes/causeDetails";
 }
}
