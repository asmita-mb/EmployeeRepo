package com.example.employeeManager.service;

import com.example.employeeManager.entity.Employee;

import java.util.List;

public interface EmployeeService {
  List<Employee> getAllEmpDetails();
  Employee getEmpDetailById(int id);
  Employee createNewEmp(Employee emp);
  Employee updateEmpById(int id, Employee emp);
  String deleteEmpById(int id);
  Employee getEmpIncrementById(int id);
  Employee incrementEmpSalaryById(int id);
}
