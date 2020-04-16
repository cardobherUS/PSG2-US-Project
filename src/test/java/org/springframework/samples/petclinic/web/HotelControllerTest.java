package org.springframework.samples.petclinic.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDate;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.samples.petclinic.model.Hotel;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(HotelController.class)
public class HotelControllerTest {

	private static final int TEST_PET_ID_1 = 1;
	private static final int TEST_PET_ID_2 = 2;
	private static final int TEST_HOTEL_ID_1 = 1;
	
	@Autowired
	private HotelController hotelController;

	@MockBean
	private ClinicService clinicService;

	@Autowired
	private MockMvc mockMvc;

	@BeforeEach
	void setup() {
		Pet pet2 = new Pet();
		pet2.setId(TEST_PET_ID_2);
		
		Pet pet1 = new Pet();
		pet1.setId(TEST_PET_ID_1);
		
		Hotel hotel1 = new Hotel();
		hotel1.setFinishDate(LocalDate.of(2020, 7, 20));
		hotel1.setStartDate(LocalDate.of(2020, 7, 14));
		hotel1.setId(TEST_HOTEL_ID_1);
		
		pet1.addHotel(hotel1);
		
		
		given(this.clinicService.findPetById(TEST_PET_ID_1)).willReturn(pet1);
		given(this.clinicService.findPetById(TEST_PET_ID_2)).willReturn(pet2);
		given(clinicService.canHotelBook(TEST_PET_ID_1)).willReturn(true);
		given(clinicService.canHotelBook(TEST_PET_ID_2)).willReturn(false);
		given(clinicService.findHotelsByPetId(TEST_PET_ID_1)).willReturn(Lists.list(hotel1));
		given(clinicService.findHotelById(TEST_HOTEL_ID_1)).willReturn(hotel1);
		

	}

	@WithMockUser(value = "spring")
	@Test
	void testInitNewHotelForm() throws Exception {
		mockMvc.perform(get("/owners/*/pets/{petId}/hotels/new", TEST_PET_ID_1)).andExpect(status().isOk())
				.andExpect(view().name("pets/createOrUpdateHotelForm"));
	}
	
	@Test
	void testThrowExceptionInitHotelForm() throws Exception {
		mockMvc.perform(get("/owners/*/pets/{petId}/hotels/new", TEST_PET_ID_2)).andExpect(status().isOk())
				.andExpect(view().name("/exception"));
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testProcessNewHotelFormSuccess() throws Exception {
		mockMvc.perform(post("/owners/{ownerId}/pets/{petId}/hotels/new", 1, TEST_PET_ID_1).param("startDate", "2020/06/12").param("finishDate", "2020/06/16"))                                
                .andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/owners/{ownerId}"));
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testProcessNewHotelFormHasErrorsBadDates() throws Exception {
		mockMvc.perform(post("/owners/{ownerId}/pets/{petId}/hotels/new", 1, TEST_PET_ID_1).param("startDate", "2020/07/12").param("finishDate", "2020/07/10"))                                
                .andExpect(status().isOk()).andExpect(model().attributeHasErrors("hotel")).andExpect(model().attributeHasFieldErrorCode("hotel", "finishDate", "wrongDate"))
				.andExpect(view().name("pets/createOrUpdateHotelForm"));
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testProcessNewHotelFormHasErrorsCanNotBook() throws Exception {
		mockMvc.perform(post("/owners/{ownerId}/pets/{petId}/hotels/new", 1, TEST_PET_ID_2).param("startDate", "2020/06/12").param("finishDate", "2020/06/16"))                                
                .andExpect(status().isOk())
				.andExpect(view().name("/exception"));
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testProcessNewHotelFormHasErrorsNullDate() throws Exception {
		mockMvc.perform(post("/owners/{ownerId}/pets/{petId}/hotels/new", 1, TEST_PET_ID_1).param("startDate", "2020/07/12"))                                
                .andExpect(status().isOk()).andExpect(model().attributeHasErrors("hotel"))
				.andExpect(view().name("pets/createOrUpdateHotelForm"));
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testProcessNewHotelFormHasErrorsDateSamePeriod() throws Exception {
		mockMvc.perform(post("/owners/{ownerId}/pets/{petId}/hotels/new", 1, TEST_PET_ID_1).param("startDate", "2020/07/12").param("finishDate", "2020/07/16"))                                
                .andExpect(status().isOk()).andExpect(model().attributeHasErrors("hotel")).andExpect(model().attributeHasFieldErrorCode("hotel", "finishDate", "alreadyBookWithSamePeriod"))
				.andExpect(view().name("pets/createOrUpdateHotelForm"));
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testShowHotels() throws Exception {
		mockMvc.perform(get("/owners/*/pets/{petId}/hotels", TEST_PET_ID_1)).andExpect(status().isOk())
		.andExpect(model().attributeExists("hotels"))
				.andExpect(view().name("hotelList"));
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testDeleteHotel() throws Exception {
		mockMvc.perform(get("/owners/{ownerId}/pets/{petId}/hotels/{hotelId}/delete", 1, TEST_PET_ID_1, TEST_HOTEL_ID_1)).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/owners/{ownerId}"));
	}
	
}
