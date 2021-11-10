package com.Employee.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.Employee.demo.exception.ResourceNotFoundException;
import com.Employee.demo.model.Employee;
import com.Employee.demo.repository.EmployeeRepository;

@RestController
@RequestMapping("/api/v1/")
@CrossOrigin
public class EmployeeController {
	@Autowired
	private EmployeeRepository employeerepository;

	//get All Employee
	@GetMapping("/employees")
	public List<Employee> getAllEmployees(){
		return employeerepository.findAll();
	}

	//Add Employee
	@PostMapping("/employees")
	public Employee AddEmployee(@RequestBody Employee employee) {
		return employeerepository.save(employee);
	}

	//get Employee by id
	@GetMapping("/employees/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
		Employee employee =  employeerepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Employee not Exist with id:"+id));
		return ResponseEntity.ok(employee);
	}

	//update Employee
	@PutMapping("/employees/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable Long id,@RequestBody Employee employeeDetails) {
		Employee employee =  employeerepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Employee not Exist with id:"+id));

		employee.setFirstName(employeeDetails.getFirstName());
		employee.setLastName(employeeDetails.getLastName());
		employee.setEmailId(employeeDetails.getEmailId());

		Employee updatedEmployee =  employeerepository.save(employee);
		return ResponseEntity.ok(updatedEmployee);
	}

	//Delete Employee
	@DeleteMapping("/employees/{id}")
	public  ResponseEntity<Map<String,Boolean>> deleteEmployee(@PathVariable Long id){
		Employee employee =  employeerepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Employee not Exist with id:"+id));

		employeerepository.delete(employee);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted",Boolean.TRUE);
		return ResponseEntity.ok(response);
	}


}
