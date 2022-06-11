package com.tw.todo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
}
