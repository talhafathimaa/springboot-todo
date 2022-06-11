package com.tw.todo;

import com.tw.todo.exception.IdNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ToDoServiceTest {

    @Mock
    private ToDoRepository toDoRepository;

    @InjectMocks
    private ToDoService toDoService;

    @Test
    public void shouldReturnAllToDosWhenGetToDosIsCalled() {
        List<ToDo> expectedToDos=List.of(new ToDo("eat",false),new ToDo("sleep",true));
        when(toDoRepository.findAll()).thenReturn(expectedToDos);

        List<ToDo> actualToDos=toDoService.getToDos();

        assertEquals(expectedToDos,actualToDos);
        verify(toDoRepository,times(1)).findAll();
    }

    @Test
    public void shouldBeAbleToGetToDoByIdWhenGetToDoIsCalledAndIdIsPresent() throws IdNotFoundException {
        ToDo expectedToDo = new ToDo("eat", false);
        when(toDoRepository.findById(expectedToDo.getId())).thenReturn(Optional.of(expectedToDo));

        ToDo actualToDo=toDoService.getToDo(expectedToDo.getId());

        assertEquals(expectedToDo,actualToDo);
        verify(toDoRepository,times(1)).findById(expectedToDo.getId());
    }

    @Test
    public void shouldThrowIdNotFoundExceptionWhenGetToDoIsCalledAndIdIsNotPresent(){
        when(toDoRepository.findById(any(Integer.class))).thenReturn(Optional.empty());

        assertThrows(IdNotFoundException.class,()->toDoService.getToDo(1));
        verify(toDoRepository,times(1)).findById(1);
    }
}
