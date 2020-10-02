package com.example.todoapp.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SqlTaskRepository extends TaskRepository, JpaRepository<Task, Integer> {

    @Override
    @Query(nativeQuery = true, value = "select count(*) > 0 from tasks where id=?1")
    boolean existsById(Integer id);

}
