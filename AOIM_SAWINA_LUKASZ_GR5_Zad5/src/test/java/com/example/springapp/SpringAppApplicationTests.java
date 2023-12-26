package com.example.springapp;

import com.example.springapp.DTO.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureMockMvc
@SpringBootTest
class SpringAppApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void shouldReturnOk() throws Exception {

		GroupDto requestDTO = new GroupDto();

		requestDTO.setNazwa("Test");
		requestDTO.setPojemnosc(2);

		mockMvc.perform(MockMvcRequestBuilders.post("/api/group")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(requestDTO)))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("Utworzono nową grupę Test"));
	}

	@Test
	void shouldReturnConflictIfGroupExists() throws Exception {
		GroupDto existingGroup = new GroupDto();

		existingGroup.setPojemnosc(20);
		existingGroup.setNazwa("Istniejaca grupa");

		mockMvc.perform(MockMvcRequestBuilders.post("/api/group")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(existingGroup)));

		mockMvc.perform(MockMvcRequestBuilders.post("/api/group")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(existingGroup)))
				.andExpect(MockMvcResultMatchers.status().isConflict())
				.andExpect(MockMvcResultMatchers.content().string("Grupa już istnieje"));
	}

	@Test
	void shouldReturnBadRequestIfCapacityTooSmall() throws Exception {
		GroupDto groupDto = new GroupDto();
		groupDto.setNazwa("Za mala grupa");
		groupDto.setPojemnosc(-2);

		mockMvc.perform(MockMvcRequestBuilders.post("/api/group")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(groupDto)))
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andExpect(MockMvcResultMatchers.content().string("Pojemność zbyt mała"));
	}

	@Test
	void shouldReturnAllGroups() throws Exception {

		GroupDto requestDTO = new GroupDto();

		requestDTO.setNazwa("Test");
		requestDTO.setPojemnosc(2);

		mockMvc.perform(MockMvcRequestBuilders.post("/api/group")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(requestDTO)))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("Utworzono nową grupę Test"));

		requestDTO.setNazwa("Test2");
		requestDTO.setPojemnosc(2);

		mockMvc.perform(MockMvcRequestBuilders.post("/api/group")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(requestDTO)))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("Utworzono nową grupę Test2"));


		mockMvc.perform(MockMvcRequestBuilders.get("/api/groups")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(result -> {
					String content = result.getResponse().getContentAsString();
					Map<String, EmployeeClass> groups = objectMapper.readValue(content, new TypeReference<Map<String, EmployeeClass>>() {});
					assertEquals(2, groups.size());
				});
	}

	@Test
	void shouldDeleteGroup() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/api/group")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"nazwa\": \"ToDelete\", \"pojemnosc\": 5}"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("Utworzono nową grupę ToDelete"));

		mockMvc.perform(MockMvcRequestBuilders.get("/api/groups")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(result -> {
					String content = result.getResponse().getContentAsString();
					Map<String, EmployeeClass> groups = objectMapper.readValue(content, new TypeReference<Map<String, EmployeeClass>>() {});
					assertTrue(groups.containsKey("ToDelete"), "Grupa powinna istnieć przed usunięciem");
				});

		mockMvc.perform(MockMvcRequestBuilders.delete("/api/group")
						.param("nazwaGrupy", "ToDelete"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("Usunięto grupę!"));

		mockMvc.perform(MockMvcRequestBuilders.get("/api/groups")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(result -> {
					String content = result.getResponse().getContentAsString();
					Map<String, EmployeeClass> groups = objectMapper.readValue(content, new TypeReference<Map<String, EmployeeClass>>() {});
					assertFalse(groups.containsKey("ToDelete"), "Grupa powinna być usunięta");
				});
	}

	@Test
	void shouldAddEmployeeToGroup() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/api/group")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"nazwa\": \"TestGroup\", \"pojemnosc\": 5}"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("Utworzono nową grupę TestGroup"));

		NewEmployeeDto newEmployeeDto = new NewEmployeeDto();
		newEmployeeDto.setGroupName("TestGroup");

		EmployeeDto employeeDto = new EmployeeDto();
		employeeDto.setName("John");
		employeeDto.setSurname("Doe");
		employeeDto.setSalary(5000.0);
		employeeDto.setYearOfBirth(1990);
		employeeDto.setEmployeeStatus(EmployeeStatus.OBECNY);

		newEmployeeDto.setEmployee(employeeDto);

		mockMvc.perform(MockMvcRequestBuilders.post("/api/employee")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(newEmployeeDto)))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("Dodano pracownika"));
	}

	@Test
	void shouldReturnNotFoundForNonexistentGroup() throws Exception {
		NewEmployeeDto newEmployeeDto = new NewEmployeeDto();
		newEmployeeDto.setGroupName("NonexistentGroup");

		EmployeeDto employeeDto = new EmployeeDto();
		employeeDto.setName("John");
		employeeDto.setSurname("Doe");
		employeeDto.setSalary(5000.0);
		employeeDto.setYearOfBirth(1990);
		employeeDto.setEmployeeStatus(EmployeeStatus.OBECNY);

		newEmployeeDto.setEmployee(employeeDto);

		mockMvc.perform(MockMvcRequestBuilders.post("/api/employee")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(newEmployeeDto)))
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andExpect(MockMvcResultMatchers.content().string("Grupa nie istnieje"));
	}

	@Test
	void shouldReturnBadRequestForFullGroup() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/api/group")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"nazwa\": \"FullGroup\", \"pojemnosc\": 1}"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("Utworzono nową grupę FullGroup"));

		NewEmployeeDto newEmployeeDto1 = new NewEmployeeDto();
		newEmployeeDto1.setGroupName("FullGroup");

		EmployeeDto employeeDto1 = new EmployeeDto();
		employeeDto1.setName("John");
		employeeDto1.setSurname("Doe");
		employeeDto1.setSalary(5000.0);
		employeeDto1.setYearOfBirth(1990);
		employeeDto1.setEmployeeStatus(EmployeeStatus.OBECNY);

		newEmployeeDto1.setEmployee(employeeDto1);

		mockMvc.perform(MockMvcRequestBuilders.post("/api/employee")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(newEmployeeDto1)))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("Dodano pracownika"));

		NewEmployeeDto newEmployeeDto2 = new NewEmployeeDto();
		newEmployeeDto2.setGroupName("FullGroup");

		EmployeeDto employeeDto2 = new EmployeeDto();
		employeeDto2.setName("Jane");
		employeeDto2.setSurname("Smith");
		employeeDto2.setSalary(6000.0);
		employeeDto2.setYearOfBirth(1995);
		employeeDto2.setEmployeeStatus(EmployeeStatus.OBECNY);

		newEmployeeDto2.setEmployee(employeeDto2);

		mockMvc.perform(MockMvcRequestBuilders.post("/api/employee")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(newEmployeeDto2)))
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andExpect(MockMvcResultMatchers.content().string("Grupa jest pełna"));
	}

	@Test
	void shouldReturnEmployeesForGroup() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/api/group")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"nazwa\": \"TestGroup\", \"pojemnosc\": 5}"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("Utworzono nową grupę TestGroup"));

		NewEmployeeDto newEmployeeDto = new NewEmployeeDto();
		newEmployeeDto.setGroupName("TestGroup");

		EmployeeDto employeeDto = new EmployeeDto();
		employeeDto.setName("John");
		employeeDto.setSurname("Doe");
		employeeDto.setSalary(5000.0);
		employeeDto.setYearOfBirth(1990);
		employeeDto.setEmployeeStatus(EmployeeStatus.OBECNY);

		newEmployeeDto.setEmployee(employeeDto);

		mockMvc.perform(MockMvcRequestBuilders.post("/api/employee")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(newEmployeeDto)))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("Dodano pracownika"));

		mockMvc.perform(MockMvcRequestBuilders.get("/api/group/TestGroup/employee")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(result -> {
					String content = result.getResponse().getContentAsString();
					List<Employee> employees = objectMapper.readValue(content, new TypeReference<List<Employee>>() {});

					assertNotNull(employees);
					assertFalse(employees.isEmpty());
					assertEquals("John", employees.get(0).getName());
					assertEquals("Doe", employees.get(0).getSurname());
					assertEquals(5000.0, employees.get(0).getSalary(), 0.001);
					assertEquals(1990, employees.get(0).getYearOfBirth());
					assertEquals(EmployeeStatus.OBECNY, employees.get(0).getEmployeeStatus());
				});
	}

	@Test
	void shouldReturnFillPercentageForGroup() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/api/group")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"nazwa\": \"TestGroup\", \"pojemnosc\": 5}"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("Utworzono nową grupę TestGroup"));

		mockMvc.perform(MockMvcRequestBuilders.get("/api/group/TestGroup/fill")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("0.0"));

		NewEmployeeDto newEmployeeDto = new NewEmployeeDto();
		newEmployeeDto.setGroupName("TestGroup");

		EmployeeDto employeeDto = new EmployeeDto();
		employeeDto.setName("John");
		employeeDto.setSurname("Doe");
		employeeDto.setSalary(5000.0);
		employeeDto.setYearOfBirth(1990);
		employeeDto.setEmployeeStatus(EmployeeStatus.OBECNY);

		newEmployeeDto.setEmployee(employeeDto);

		mockMvc.perform(MockMvcRequestBuilders.post("/api/employee")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(newEmployeeDto)))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("Dodano pracownika"));

		mockMvc.perform(MockMvcRequestBuilders.get("/api/group/TestGroup/fill")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("20.0"));
	}

	@Test
	void shouldFillReturnNotFoundForNonexistentGroup() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/group/NonexistentGroup/fill")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	void shouldAddRatingToGroup() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.post("/api/group")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"nazwa\": \"TestGroup\", \"pojemnosc\": 5}"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("Utworzono nową grupę TestGroup"));

		NewRateDto newRateDto = new NewRateDto();
		newRateDto.setGroupName("TestGroup");

		Calendar calendar = Calendar.getInstance();

		Date currentDate = calendar.getTime();

		RateDto rateDto = new RateDto();
		rateDto.setStarNumber(8);
		rateDto.setReviewDate(currentDate);
		rateDto.setComment("Bardzo dobra grupa");

		newRateDto.setRate(rateDto);

		mockMvc.perform(MockMvcRequestBuilders.post("/api/rating")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(newRateDto)))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("Dodano ocene"));
	}

	@Test
	void shouldReturnBadRequestForInvalidRating() throws Exception {
		NewRateDto newRateDto = new NewRateDto();
		newRateDto.setGroupName("TestGroup");
		Calendar calendar = Calendar.getInstance();

		Date currentDate = calendar.getTime();
		RateDto rateDto = new RateDto();
		rateDto.setStarNumber(15);
		rateDto.setReviewDate(currentDate);
		rateDto.setComment("Nieprawidłowa ocena");

		newRateDto.setRate(rateDto);

		mockMvc.perform(MockMvcRequestBuilders.post("/api/rating")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(newRateDto)))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}

	@Test
	void shouldDeleteEmployeeFromGroup() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/api/group")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"nazwa\": \"TestGroup\", \"pojemnosc\": 5}"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("Utworzono nową grupę TestGroup"));

		NewEmployeeDto newEmployeeDto = new NewEmployeeDto();
		newEmployeeDto.setGroupName("TestGroup");

		EmployeeDto employeeDto = new EmployeeDto();
		employeeDto.setName("John");
		employeeDto.setSurname("Doe");
		employeeDto.setSalary(5000.0);
		employeeDto.setYearOfBirth(1990);
		employeeDto.setEmployeeStatus(EmployeeStatus.OBECNY);

		newEmployeeDto.setEmployee(employeeDto);

		mockMvc.perform(MockMvcRequestBuilders.post("/api/employee")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(newEmployeeDto)))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("Dodano pracownika"));

		int employeeId = 0;


		mockMvc.perform(MockMvcRequestBuilders.delete("/api/group/TestGroup/employee/" + employeeId)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("Usunięto osobe!"));

		mockMvc.perform(MockMvcRequestBuilders.get("/api/group/TestGroup/employee")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(result -> {
					String content = result.getResponse().getContentAsString();
					List<Employee> employees = objectMapper.readValue(content, new TypeReference<List<Employee>>() {});

					assertNotNull(employees);
					assertTrue(employees.isEmpty());
				});
	}


}
