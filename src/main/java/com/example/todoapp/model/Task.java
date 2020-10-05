package com.example.todoapp.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
public class Task extends BaseAuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message = "Wiadomość nie może być pusta")
    private String description;
    private boolean done;
    private LocalDateTime deadline;


    public Task() {
    }

    public String getDescription() {
        return this.description;
    }

    public int getId() {
        return this.id;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public void updateFrom(final Task source) {
        this.description = source.description;
        this.done = source.done;
        this.deadline = source.deadline;
    }

}
