package com.phyo.springboot_dynamodb.controller;

import com.phyo.springboot_dynamodb.entity.Employee;
import com.phyo.springboot_dynamodb.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private  EmployeeRepository repository;

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployee(@PathVariable  String id){
        Employee employee = repository.getEmployeeById(id);
        return ResponseEntity.ok(employee);
    }

    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployee(){
        List<Employee> employeeList = repository.getEmployees();
        return ResponseEntity.ok(employeeList);
    }

    @PostMapping
    public ResponseEntity<Employee> create(@RequestBody Employee employee){
        Employee savedEmployee = repository.save(employee);
        return new ResponseEntity<>(savedEmployee,HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable String id){
        String message = repository.deleteEmployee(id);
        return new ResponseEntity<>(message,HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable String id,
                                         @RequestBody Employee employee){
        String message = repository.updateEmployee(id, employee);
        return ResponseEntity.ok(message);
    }


}
