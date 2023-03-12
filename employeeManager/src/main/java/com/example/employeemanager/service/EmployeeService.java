package com.example.employeemanager.service;

import com.example.employeemanager.entity.Employee;

public interface EmployeeService {
  Iterable<Employee> getAllEmpDetails();
  Employee getEmpDetailById(int id);
  Employee createNewEmp(Employee emp);
  Employee updateEmpById(int id, Employee emp);
  String deleteEmpById(int id);
  Employee getEmpIncrementById(int id);
  Employee incrementEmpSalaryById(int id);
}
