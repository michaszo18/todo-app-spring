package com.example.todoapp.logic;

import com.example.todoapp.TaskConfigurationProperties;
import com.example.todoapp.model.ProjectRepository;
import com.example.todoapp.model.TaskGroup;
import com.example.todoapp.model.TaskGroupRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProjectServiceTest {

    @Test
    @DisplayName("Should throw IllegalStateException when configured to allows just 1 group and the other undone group exist.")
    void test1() {
        // given
        var mockGroupRepository = groupRepositoryReturning(true);

        TaskConfigurationProperties mockConfig = configurationReturning(false);

        // system under test
        var sut = new ProjectService(null, mockGroupRepository, mockConfig);

        //when
        var exception = catchThrowable(() -> sut.createGroup(LocalDateTime.now(), 1));

        // then
        assertThat(exception)
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("tylko jeden projekt");

    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when configuration ok and no project for given id")
    void test2() {
        // given
        var mockRepository = mock(ProjectRepository.class);
        when(mockRepository.findById(anyInt())).thenReturn(Optional.empty());
        TaskConfigurationProperties mockConfig = configurationReturning(true);

        // system under test
        var sut = new ProjectService(mockRepository, null, mockConfig);

        //when
        var exception = catchThrowable(() -> sut.createGroup(LocalDateTime.now(), 1));

        // then
        assertThat(exception)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("ID nie istnieje");

    }

    @Test
    @DisplayName("Should throw IllegalStateException when configured to allows just 1 group and no groups projects for a given id.")
    void test3() {
        // given
        var mockRepository = mock(ProjectRepository.class);
        when(mockRepository.findById(anyInt())).thenReturn(Optional.empty());

        var mockGroupRepository = groupRepositoryReturning(false);

        TaskConfigurationProperties mockConfig = configurationReturning(true);

        // system under test
        var sut = new ProjectService(mockRepository, mockGroupRepository, mockConfig);

        //when
        var exception = catchThrowable(() -> sut.createGroup(LocalDateTime.now(), 1));

        // then
        assertThat(exception)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("ID nie istnieje");
    }


    private TaskConfigurationProperties configurationReturning(final boolean b) {
        var mockTemplate = mock(TaskConfigurationProperties.Template.class);
        when(mockTemplate.isAllowMultipleTasks()).thenReturn(b);

        var mockConfig = mock(TaskConfigurationProperties.class);
        when(mockConfig.getTemplate()).thenReturn(mockTemplate);
        return mockConfig;
    }

    private TaskGroupRepository groupRepositoryReturning(final boolean b) {
        var mockGroupRepository = mock(TaskGroupRepository.class);
        when(mockGroupRepository.existsByDoneIsFalseAndProject_Id(anyInt())).thenReturn(b);
        return mockGroupRepository;
    }

}