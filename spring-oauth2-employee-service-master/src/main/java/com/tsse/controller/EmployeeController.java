package com.tsse.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.tsse.controller.exceptions.ConstraintsViolationException;
import com.tsse.controller.exceptions.EntityNotFoundException;
import com.tsse.controller.exceptions.GeneralExcpetion;
import com.tsse.entities.Employee;
import com.tsse.services.EmployeeService;

@RestController
@RequestMapping(path = "/api/employees")
@Slf4j
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	/**
	 * 
	 * @param empId
	 * @return
	 * @throws EntityNotFoundException
	 * @throws GeneralExcpetion
	 */
	@GetMapping("/{empId}")
	@PreAuthorize("hasAnyAuthority('ROLE_READ_EMPLOYEE')")
	public Resources<Employee> getEmployeeAndDepartment(@Valid @PathVariable("empId") final Long empId){
		log.info("Employee Controller - Get - for employee with id {} ", empId);
		Employee emp = employeeService.getEmployeeAndDepartment(empId);
		log.info("Employee Controller - Get - employee to be returned {} ", emp);
		// adding to list because of cache problem
		List<Employee> emps = new ArrayList<>();
		emps.add(emp);
		Link link = linkTo(methodOn(EmployeeController.class).getEmployeeAndDepartment(empId)).withSelfRel();
		Resources<Employee> result = new Resources<>(emps, link);
		return result;
		// return new ResponseEntity<>(emp, HttpStatus.OK);

	}

	/**
	 * 
	 * @param orderBy
	 * @param direction
	 * @return
	 * @throws EntityNotFoundException
	 * @throws GeneralExcpetion
	 * @throws ConstraintsViolationException
	 */
	@GetMapping(params = { "orderBy", "direction" })
	@PreAuthorize("hasAnyAuthority('ROLE_READ_EMPLOYEE')")
	public Resources<Employee> getEmployees(@RequestParam("orderBy") String orderBy,
			@RequestParam("direction") String direction) {

		/*
		 * if (direction != null &&
		 * !(direction.equals(Direction.ASCENDING.getDirectionCode()) ||
		 * direction.equals(Direction.DESCENDING.getDirectionCode()))) { throw new
		 * ConstraintsViolationException("direction param is invalid"); }
		 * 
		 * if (orderBy != null && !(orderBy.equals(OrderBy.ID.getOrderByCode()) ||
		 * orderBy.equals(OrderBy.FIRSTNAME.getOrderByCode()))) { throw new
		 * ConstraintsViolationException("orderBy param is invalid"); }
		 */

		List<Employee> emps = employeeService.getEmployeesAndDepartment(direction, orderBy);

		for (final Employee emp : emps) {
			Link selfLink = linkTo(methodOn(EmployeeController.class).getEmployeeAndDepartment(emp.getEmpId()))
					.withSelfRel();
			emp.add(selfLink);
		}

		Link link = linkTo(methodOn(EmployeeController.class).getEmployees(orderBy, direction)).withSelfRel();
		Resources<Employee> result = new Resources<>(emps, link);
		return result;
		// return new ResponseEntity<>(emps, HttpStatus.OK);
	}

	/**
	 * 
	 * @param employee
	 * @param empId
	 * @return
	 * @throws EntityNotFoundException
	 * @throws GeneralExcpetion
	 */
	@PutMapping("/{empId}")
	@PreAuthorize("hasAnyAuthority('ROLE_READ_EMPLOYEE')")
	@ResponseStatus(code=HttpStatus.ACCEPTED)
	public void updateResource(@Valid @RequestBody Employee employee, @PathVariable("empId") Long empId) {
		employeeService.updateEmployeeAndDepartment(empId, employee);
		
	}

	/**
	 * 
	 * @param employee
	 * @return
	 * @throws EntityNotFoundException
	 */
	@PostMapping()
	@PreAuthorize("hasAnyAuthority('ROLE_READ_EMPLOYEE')")
	@ResponseStatus(code=HttpStatus.CREATED)
	public Resources<Employee> saveResource(@Valid @RequestBody Employee employee) {
		Employee emp = employeeService.saveEmployee(employee);
		Link link = linkTo(methodOn(EmployeeController.class).getEmployeeAndDepartment(emp.getEmpId())).withSelfRel();
		emp.add(link);
		List<Employee> emps = new ArrayList<>();
		emps.add(emp);
		Resources<Employee> result = new Resources<>(emps, link);
		return result;
	}
}
