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

    @Test
    void shouldReturnToDoByIdWhenFindByIdIsCalled() {
        ToDo expectedToDo = new ToDo( "Do the dishes", false);
        toDoRepository.save(expectedToDo);

        ToDo actualToDo = toDoRepository.findById(expectedToDo.getId()).get();

        assertEquals(expectedToDo, actualToDo);
    }

    @Test
    void shouldBeAbleToSaveToDoAndReturnSavedToDoWhenSaveIsCalled() {
        ToDo expectedToDo = new ToDo( "cook", false);

        ToDo actualToDo=toDoRepository.save(expectedToDo);

        assertEquals(expectedToDo, actualToDo);
    }

    @Test
    void shouldBeAbleToUpdateAnExistingToDoAndReturnUpdatedToDo() {
        ToDo toDo = new ToDo( "play", false);
        toDoRepository.save(toDo);

        ToDo existingToDo=toDoRepository.findById(toDo.getId()).get();
        existingToDo.setText("practice singing");
        existingToDo.setCompleted(true);
        ToDo updatedToDo=toDoRepository.save(existingToDo);

        assertEquals("practice singing",updatedToDo.getText());
        assertEquals(true,updatedToDo.isCompleted());
    }
}
