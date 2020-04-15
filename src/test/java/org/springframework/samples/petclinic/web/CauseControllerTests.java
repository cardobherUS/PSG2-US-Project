package org.springframework.samples.petclinic.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(CauseController.class)
public class CauseControllerTests {

	@Autowired
	private CauseController causeController;

	@MockBean
	private ClinicService clinicService;

	@Autowired
	private MockMvc mockMvc;
	
	@WithMockUser(value = "spring")
    @Test
    void testInitCauseForm() throws Exception {
	mockMvc.perform(get("/causes/new")).andExpect(status().isOk()).andExpect(model().attributeExists("cause"))
			.andExpect(view().name("causes/createCauseForm"));
}

}
