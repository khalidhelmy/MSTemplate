package com.tsse.services;

import java.util.List;

import com.tsse.controller.exceptions.EntityNotFoundException;
import com.tsse.controller.exceptions.GeneralExcpetion;
import com.tsse.entities.Employee;

public interface EmployeeService {

	
	public Employee getEmployeeAndDepartment(Long id) throws EntityNotFoundException, GeneralExcpetion;
	
	public List<Employee> getEmployeesAndDepartment(String direction, String orderBy) throws GeneralExcpetion;

	public Employee updateEmployeeAndDepartment(Long empId, Employee updatedEmployee) throws EntityNotFoundException, GeneralExcpetion;

	public Employee saveEmployee(Employee employee);
}
