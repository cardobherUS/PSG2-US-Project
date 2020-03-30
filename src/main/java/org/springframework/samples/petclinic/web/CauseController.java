
package org.springframework.samples.petclinic.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Cause;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/causes")
public class CauseController {

	private final ClinicService clinicService;


	@Autowired
	private CauseController(final ClinicService clinicService) {
		this.clinicService = clinicService;
	}

	@GetMapping()
	public String mostrarCauses(final ModelMap modelMap) {

		String vista = "causes/causesList";
		Iterable<Cause> causes = this.clinicService.findCauses();
		modelMap.addAttribute("causes", causes);
		return vista;
	}
}
