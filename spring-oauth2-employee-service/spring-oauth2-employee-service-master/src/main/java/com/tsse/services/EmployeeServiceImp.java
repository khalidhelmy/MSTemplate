package com.tsse.services;

import org.springframework.beans.factory.annotation.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tsse.backend.integration.DepartmentRestClient;
import com.tsse.controller.exceptions.EntityNotFoundException;
import com.tsse.controller.exceptions.GeneralExcpetion;
import com.tsse.dal.EmployeeRepository;
import com.tsse.entities.Department;
import com.tsse.entities.Employee;

@Service
@Slf4j
//@CacheConfig(cacheNames={"employees"})
public class EmployeeServiceImp implements EmployeeService {

	@Autowired
	private DepartmentRestClient departmentRestClient;

	@Autowired
	private EmployeeRepository employeeRepository;

	@Value("${cache.name.employee}")
	private String employeesCacheName;
	
	@Cacheable(value = "employees", key = "#id", cacheManager="timeOutCacheManager")
	@Transactional(readOnly = true)
	public Employee getEmployeeAndDepartment(Long id) throws EntityNotFoundException, GeneralExcpetion {
		CompletableFuture<Employee> emp;
		CompletableFuture<Department> dept;
		try {
			emp = employeeRepository.findByEmpId(id);
			dept = departmentRestClient.getDepartment();
		} catch (Exception e) {
			throw new GeneralExcpetion();
		}
		//EmployeeDO empDo = null; 
		Department deptartment = null;
		Employee employee = null;
		try {
			deptartment = dept.get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
			throw new GeneralExcpetion();
			
		}
		try {
			if (null != emp.get()) {
				employee = emp.get();
			} else {
				throw new EntityNotFoundException("Employee You are refering to is not found");
			}
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
			throw new GeneralExcpetion();
		}
		employee.setDepartment(deptartment);
		//empDo = EmployeeDO.builder().department(deptartment).emp(employee).build();
		log.info("getEmployeeAndDepartment method - employee returned: {}",  employee);
		return employee;

	}

	@Override
	@Cacheable(value = "employees", cacheManager="timeOutCacheManager")
	@Transactional(readOnly = true)
	public List<Employee> getEmployeesAndDepartment(String direction, String orderBy) throws GeneralExcpetion {

		Sort sort = new Sort(Sort.Direction.DESC, orderBy);
		if (direction.equals("ASC")) {
			sort = new Sort(Sort.Direction.ASC, orderBy);
		}

		List<Employee> emp = (List<Employee>) employeeRepository.findAll(sort);

		CompletableFuture<Department> dept = departmentRestClient.getDepartment();
		List<Employee> employees = new ArrayList<>();
		for (Iterator iterator = emp.iterator(); iterator.hasNext();) {
			Employee employee = (Employee) iterator.next();
			Department deptartment = null;
			
			try {
				deptartment = dept.get();
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
				throw new GeneralExcpetion();
			}
			employee.setDepartment(deptartment);
			employees.add(employee);
		}

		return employees;
	}

	@Override
	@CachePut(value="employees", key= "#empId")
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW) // need to understand the use
																							// of propagation
	public Employee updateEmployeeAndDepartment(Long empId, Employee updatedEmployee)
			throws EntityNotFoundException, GeneralExcpetion {
		Employee existingEmployee = getEmployeeAndDepartment(empId);
		if (existingEmployee == null) {
			throw new EntityNotFoundException("Employee You are refering to is not found");
		}
		return employeeRepository.save(existingEmployee);

	}

	@Override
	@CachePut(value="employees")
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW) // need to understand the use
																							// of propagation
	public Employee saveEmployee(Employee employee) {
		return employeeRepository.save(employee);
	}
}
