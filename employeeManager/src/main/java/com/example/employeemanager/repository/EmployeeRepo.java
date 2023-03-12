package com.example.employeemanager.repository;

import com.example.employeemanager.entity.Employee;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepo extends CrudRepository <Employee,Integer>{
}
