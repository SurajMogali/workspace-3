package com.demo.spring;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.demo.spring.entity.Employee;
import com.demo.spring.repository.EmployeeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class EmpDataServiceApplicationTests {

	@Autowired
	MockMvc mvc;
	
	@MockBean
	EmployeeRepository empRepository;
	
	@Test
	public void testFind() throws Exception {
		Employee emp = new Employee(130,"Preetam","Noida",90000.0,10);
		when(empRepository.findById(130)).thenReturn(Optional.of(emp));
		mvc.perform(get("/employee/130"))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(jsonPath("$.name").value("Preetam"));
	}
	
	@Test
	public void testSave() throws Exception {
		Employee emp = new Employee(130,"Preetam","Noida",90000.0,10);
		ObjectMapper mapper = new ObjectMapper();//from jackson 
		String empJson = mapper.writeValueAsString(emp);
		
		when(empRepository.existsById(130)).thenReturn(false);
								//Incoming JSON					//outgoing JSON
		mvc.perform(post("/employee/").content(empJson).contentType(MediaType.APPLICATION_JSON_VALUE))//consumes JSON
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(jsonPath("$.status").value("Emp saved"));
	}
	
	/*@Test
	public void testSaveFailureEmp() throws Exception {
		Employee emp = new Emp(102,"Kailash","Dehradun",89000.0);
		ObjectMapper mapper = new ObjectMapper();//from jackson 
		String empJson = mapper.writeValueAsString(emp);
		
		when(empRepository.existsById(102)).thenReturn(true);
								//Incoming JSON					//outgoing JSON
		mvc.perform(post("/save").content(empJson).contentType(MediaType.APPLICATION_JSON_VALUE))//consumes JSON
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(jsonPath("$.status").value("Emp Already Exists"));
	}
	
	@Test
	public void testUpdateSalary() throws Exception {
		Employee emp = new Employee(105,"Kailash","Dehradun",89000.0,10);
		
		
		when(empRepo.findById(105)).thenReturn(Optional.of(emp));
		when(empRepo.updateSalary(105,55000)).thenReturn(1);
		
		
		mvc.perform(patch("/updatesal/105/55000"))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(jsonPath("$.status").value("Salary updated"));
	}
	
	@Test
	public void testUpdateFailureSalary() throws Exception {
		Emp emp = new Emp(105,"Kailash","Dehradun",89000.0);
		
		
		when(empRepo.findById(105)).thenReturn(Optional.empty());
		when(empRepo.updateSalary(105,55000)).thenReturn(1);
		
		
		mvc.perform(patch("/updatesal/105/55000"))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(jsonPath("$.status").value("Emp doesnt exist"));
	}*/

}