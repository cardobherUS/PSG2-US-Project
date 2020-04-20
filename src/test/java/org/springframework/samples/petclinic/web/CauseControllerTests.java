package org.springframework.samples.petclinic.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.model.Cause;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(value = CauseController.class,
includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE))
public class CauseControllerTests {

	private static final int TEST_CAUSE_ID = 1;

	@Autowired
	private CauseController causeController;

	@MockBean
	private ClinicService clinicService;

	@Autowired
	private MockMvc mockMvc;


	@BeforeEach
	void setup() {
		Cause c = new Cause();
		c.setName("prueba");
		this.clinicService.saveCause(c);
		given(this.clinicService.findCauseById(TEST_CAUSE_ID)).willReturn(c);
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/causes/new")).andExpect(status().isOk())
		.andExpect(view().name("causes/createCauseForm")).andExpect(model().attributeExists("cause"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/causes/new").param("name", "Solidaridad")
				.param("description", "Seamos buenos").param("budgetTarget", "2015").param("organization", "Casa Paco"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/causes"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testProcessCreationFormErrors() throws Exception {
		mockMvc.perform(post("/causes/new").param("name", "")
				.param("description", "Seamos buenos").param("budgetTarget", "xd").param("organization", "Casa Paco"))
				.andExpect(status().isOk())
				.andExpect(view().name("causes/createCauseForm"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testProcessCreationFormErrorDuplicatedName() throws Exception {		
		given(this.clinicService.causeNameAlreadyExists("Cause1")).willReturn(true);
		mockMvc.perform(post("/causes/new").param("name", "Cause1")
				.param("description", "Seamos buenísimos").param("budgetTarget", "20115").param("organization", "Casa Pepe"))
				.andExpect(status().isOk())
				.andExpect(view().name("causes/createCauseForm"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testMostrarCauses() throws Exception {
		mockMvc.perform(get("/causes")).andExpect(status().isOk())
		.andExpect(model().attributeExists("causes")).andExpect(view().name("causes/causesList"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testShowCause() throws Exception {
		mockMvc.perform(get("/causes/{causeId}", TEST_CAUSE_ID)).andExpect(status().isOk())
		.andExpect(model().attributeExists("cause")).andExpect(view().name("causes/causeDetails"));
	}

}
