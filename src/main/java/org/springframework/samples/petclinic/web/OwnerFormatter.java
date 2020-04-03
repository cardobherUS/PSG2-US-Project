
package org.springframework.samples.petclinic.web;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.stereotype.Component;

@Component
public class OwnerFormatter implements Formatter<Owner> {

	private final ClinicService clinicService;


	@Autowired
	public OwnerFormatter(final ClinicService clinicService) {
		this.clinicService = clinicService;
	}

	@Override
	public String print(final Owner owner, final Locale locale) {
		return owner.getFirstName();
	}

	@Override
	public Owner parse(final String text, final Locale locale) throws ParseException {
		Collection<Owner> owners = this.clinicService.findOwnerByLastName("");
		for (Owner owner : owners) {
			if (owner.getFirstName().equals(text)) {
				return owner;
			}
		}
		throw new ParseException("type not found: " + text, 0);
	}

}
