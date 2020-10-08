package com.example.todoapp.adapter;

import com.example.todoapp.model.Project;
import com.example.todoapp.model.ProjectRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SqlProjectRepository extends ProjectRepository, JpaRepository<Project, Integer> {

    @Override
    @Query("select distinct p from Project p join fetch p.steps") //domyślnie inner join
    List<Project> findAll();

}
