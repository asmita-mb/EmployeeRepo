package com.example.employeemanager.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Data
@NoArgsConstructor
@Entity
@Table(name = "INCREMENTS_RECORDS")
public class IncrementLogs {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  int id;
  int empID;
  int prevSalary;
  int currSalary;
}
