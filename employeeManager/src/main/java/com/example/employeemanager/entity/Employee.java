package com.example.employeemanager.entity;


import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;


@Data
@NoArgsConstructor
@Entity
public class Employee {
  @Id
  int id;
  String name;
  String designation;
  int salary;
}

