package com.tsse.annotations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.tsse.entities.Employee;

public class EmployeeCustomValidator implements ConstraintValidator<EmployeeCustomAnnotation, Employee> {

	@Override
	public void initialize(EmployeeCustomAnnotation employeeCustom) {

	}

	@Override
	public boolean isValid(Employee object, ConstraintValidatorContext cxt) {
		if (!(object instanceof Employee)) {
			throw new IllegalArgumentException("@EmployeeCustomAnnotation only applies to Employee");
		}
		Employee employee = (Employee) object;
		if (employee.getFirstName() != null || employee.getLastName() != null) {
			return true;
		} else {
			return false;
		}
	}
}
