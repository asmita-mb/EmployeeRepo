package com.example.employeeManager.service;

import com.example.employeeManager.entity.Employee;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImp implements EmployeeService{

  @Override
  public List<Employee> getAllEmpDetails() {
    return null;
  }

  @Override
  public Employee getEmpDetailById(int id) {
    return null;
  }

  @Override
  public Employee createNewEmp(Employee emp) {
    return null;
  }

  @Override
  public Employee updateEmpById(int id, Employee emp) {
    return null;
  }

  @Override
  public String deleteEmpById(int id) {
    return null;
  }

  @Override
  public Employee getEmpIncrementById(int id) {
    return null;
  }

  @Override
  public Employee incrementEmpSalaryById(int id) {
    return null;
  }
}
