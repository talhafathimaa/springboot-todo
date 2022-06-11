package com.tw.todo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class ToDoRepositoryTest {

    @Autowired
    private ToDoRepository toDoRepository;

    @Test
    void shouldReturnAllToDosWhenFindAllIsCalled() {
        List<ToDo> expectedToDos = List.of(new ToDo( "eat", false), new ToDo( "sleep", true));
        toDoRepository.saveAll(expectedToDos);

        List<ToDo> actualToDos = toDoRepository.findAll();

        assertEquals(expectedToDos, actualToDos);
    }
}
