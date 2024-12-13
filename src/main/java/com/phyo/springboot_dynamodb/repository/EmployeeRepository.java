package com.phyo.springboot_dynamodb.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.phyo.springboot_dynamodb.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmployeeRepository {

    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    public Employee save(Employee employee){
        System.out.println("Before saving: " + employee);
        dynamoDBMapper.save(employee);
        return employee;
    }

    public Employee getEmployeeById(String id){
       return  dynamoDBMapper.load(Employee.class,id);
    }

    public List<Employee> getEmployees(){
        return dynamoDBMapper.scan(Employee.class, new DynamoDBScanExpression());
    }

    public String deleteEmployee(String id){
        Employee employee = dynamoDBMapper.load(Employee.class, id);
        dynamoDBMapper.delete(employee);
        return "Successfully Deleted!";
    }

    public String updateEmployee(String id, Employee employee){
        employee.setEmployeeId(id);

        // Conditional expression to ensure the item exists
        DynamoDBSaveExpression saveExpression = new DynamoDBSaveExpression()
                .withExpectedEntry("employeeId", new ExpectedAttributeValue(
                        new AttributeValue().withS(id)
                ));

            dynamoDBMapper.save(employee, saveExpression);
            return "Successfully Updated for the ID: " + id;
    }
}
