package org.springframework.samples.petclinic.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.samples.petclinic.model.Cause;
import org.springframework.samples.petclinic.model.Donation;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;


@WebMvcTest(DonationController.class)
public class DonationControllerTests {

	private static final int TEST_CAUSE_ID = 1;
	
	@Autowired
	private DonationController donationController;
	
	@MockBean
	private ClinicService clinicService;

	@Autowired
	private MockMvc mockMvc;

	@BeforeEach
	void setup() {
		
		Cause c = new Cause();
		c.setId(TEST_CAUSE_ID);
		c.setBudgetTarget(2000);
		c.setDescription("Description");
		c.setName("Name1");
		c.setOrganization("O1");
		
		Donation d1 = new Donation();
		d1.setAmount(25);
		d1.setCause(c);
		
		c.addDonation(d1);
		
		Collection<Owner> clients = new ArrayList<>();
		
		Owner client1 = new Owner();
		client1.setFirstName("Enrique");
		
		clients.add(client1);
		
		given(this.clinicService.findCauseById(TEST_CAUSE_ID)).willReturn(c);
		given(this.clinicService.findOwnerByLastName("")).willReturn(clients);		
	}

        @WithMockUser(value = "spring")
        @Test
        void testInitNewDonationForm() throws Exception {
		mockMvc.perform(get("/causes/{causeId}/donations/new", TEST_CAUSE_ID))
		.andExpect(status().isOk())
		.andExpect(view().name("donations/createOrUpdateDonationForm"));
	}
        
        @WithMockUser(value = "spring")
        @Test
        void testProcessNewDonationForm() throws Exception {
        	
        	mockMvc.perform(post("/causes/{causeId}/donations/new" , TEST_CAUSE_ID)
        	.with(csrf())
        	.param("amount","25")
        	.param("client","Enrique2")
        	.param("date","2020/04/15"))
        	.andExpect(status().is3xxRedirection())
        	.andExpect(view().name("redirect:/causes"));
       
        }
        
        @WithMockUser(value = "spring")
        @Test
        void testProcessNewDonationFormHasErrors() throws Exception {
        	
        	mockMvc.perform(post("/causes/{causeId}/donations/new" , TEST_CAUSE_ID)
        	.with(csrf())
        	.param("amount","")
        	.param("client","Enrique2")
        	.param("date","2020/04/15"))
        	.andExpect(status().isOk())
        	.andExpect(view().name("donations/createOrUpdateDonationForm"));
       
        }
}
