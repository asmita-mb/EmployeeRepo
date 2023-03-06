package com.example.employeeManager.repository;

import com.example.employeeManager.entity.Employee;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepo extends CrudRepository <Employee,Integer>{
}
