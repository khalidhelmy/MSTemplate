package com.tsse.employee.service.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import com.tsse.backend.integration.DepartmentRestClient;
import com.tsse.dal.EmployeeRepository;
import com.tsse.entities.Department;
import com.tsse.entities.Employee;
import com.tsse.services.EmployeeService;
import com.tsse.services.EmployeeServiceImp;

@RunWith(SpringRunner.class)
public class EmployeeServiceImplIntegrationTest {
 
    @TestConfiguration
    static class EmployeeServiceImplTestContextConfiguration {
  
        @Bean
        public EmployeeService employeeService() {
            return new EmployeeServiceImp();
        }
    }
    
    private Employee constructEmployee(long id, int age, String firstName, String lastName) {
    	Employee employee = new Employee();
    	employee.setEmpId(id);
    	employee.setAge(age);
    	employee.setFirstName(firstName);
    	employee.setLastName(lastName);
    	
    	return employee;
    }
    
    @Before
    public void setUp() {
        Employee alex = constructEmployee(3L, 18, "Alex", "John");
        List<Employee> emps = new ArrayList<>();
        emps.add(alex);
        Sort sort = new Sort(Sort.Direction.ASC, "firstName");
		
        Mockito.when(employeeRepository.findAll(sort))
          .thenReturn(emps);
        
        CompletableFuture<Department> deptFuture = new CompletableFuture<>();
        Department dept = new Department();
        dept.setName("Software dept.");
        deptFuture.complete(dept);
        Mockito.when(departmentRestClient.getDepartment())
        .thenReturn(deptFuture);
    }
 
    @Autowired
    private EmployeeService employeeService;
 
    @MockBean
    private EmployeeRepository employeeRepository;
 
    @MockBean
    private DepartmentRestClient departmentRestClient;
    // write test cases here
    
    @Test
    public void whenValidName_thenEmployeeShouldBeFound() {
        List<Employee> found = employeeService.getEmployeesAndDepartment("ASC","firstName");
      
         assertThat(found.size())
          .isEqualTo(1);
         
         //another assertions should be done.
     }
}