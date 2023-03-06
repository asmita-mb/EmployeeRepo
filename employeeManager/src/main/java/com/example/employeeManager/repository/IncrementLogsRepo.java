package com.example.employeeManager.repository;

import com.example.employeeManager.entity.IncrementLogs;
import org.springframework.data.repository.CrudRepository;

public interface IncrementLogsRepo extends CrudRepository<IncrementLogs,Integer> {
}
