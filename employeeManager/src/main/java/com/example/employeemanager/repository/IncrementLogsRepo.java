package com.example.employeemanager.repository;

import com.example.employeemanager.entity.IncrementLogs;
import org.springframework.data.repository.CrudRepository;

public interface IncrementLogsRepo extends CrudRepository<IncrementLogs,Integer> {
}
