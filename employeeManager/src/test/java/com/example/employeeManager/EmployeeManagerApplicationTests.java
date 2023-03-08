package com.example.employeeManager;


import com.example.employeeManager.entity.Employee;
import com.example.employeeManager.repository.EmployeeRepo;
import com.example.employeeManager.service.EmployeeServiceImp;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeManagerApplicationTests {

  private MockMvc mockMvc;

  @InjectMocks
  private EmployeeServiceImp employeeServiceImp;

  @Mock
  private EmployeeRepo employeeRepo;



  @Test
  public void getAllEmpTest() throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();

    List<Employee> emp =
        objectMapper.readValue(new File("./src/test/resources/employee.json"),
            new TypeReference<List<Employee>>() {});

    Mockito.when(employeeServiceImp.getAllEmpDetails())
        .thenReturn(emp);

    assertEquals(emp,employeeServiceImp.getAllEmpDetails());


  }

  @Test
  public void getEmpByIDTest() throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();
    Employee emp =
        objectMapper.readValue(new File("./src/test/resources/employee_1.json"),
           Employee.class);
    when(employeeServiceImp.getEmpDetailById(1)).thenReturn(emp);
    Employee savedEmployee = employeeServiceImp.getEmpDetailById(1);
    Assertions.assertNotNull(emp);
  }



}