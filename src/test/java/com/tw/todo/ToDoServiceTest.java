package com.tw.todo;

import com.tw.todo.exception.IdNotFoundException;
import com.tw.todo.exception.ToDoAlreadyExistsException;
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

    @Test
    public void shouldThrowToDoAlreadyExistsExceptionWhenSaveToDoIsCalledAndTextIsAlreadyPresent(){
        ToDo expectedToDo = new ToDo(1,"eat", false);
        when(toDoRepository.findByText(expectedToDo.getText())).thenReturn(Optional.of(expectedToDo));

        assertThrows(ToDoAlreadyExistsException.class,()->toDoService.saveToDo(expectedToDo));
        verify(toDoRepository,never()).save(any(ToDo.class));
    }

    @Test
    public void shouldSaveToDoAndReturnSavedToDoWhenSaveToDoIsCalled() throws ToDoAlreadyExistsException {
        ToDo expectedToDo = new ToDo(1,"eat", false);
        when(toDoRepository.findByText(expectedToDo.getText())).thenReturn(Optional.empty());
        when(toDoRepository.save(expectedToDo)).thenReturn(expectedToDo);

        ToDo savedToDo = toDoService.saveToDo(expectedToDo);

        assertEquals(expectedToDo,savedToDo);
        verify(toDoRepository,times(1)).save(expectedToDo);
    }

    @Test
    void shouldUpdateToDoAndReturnUpdatedToDoWhenUpdateToDoIsCalled() throws IdNotFoundException {
        ToDo existingToDo = new ToDo(1,"eat", false);
        ToDo newToDo = new ToDo(1,"sleep", false);
        when(toDoRepository.findById(existingToDo.getId())).thenReturn(Optional.of(existingToDo));
        when(toDoRepository.save(newToDo)).thenReturn(newToDo);

        ToDo updatedToDo=toDoService.updateToDo(existingToDo.getId(), newToDo);

        assertEquals(updatedToDo.getText(),newToDo.getText());
        verify(toDoRepository,times(1)).save(newToDo);
        verify(toDoRepository,times(1)).findById(existingToDo.getId());
    }

    @Test
    public void shouldThrowIdNotFoundExceptionWhenUpdateToDoIsCalledAndIdIsNotPresent(){
        ToDo newToDo = new ToDo(1,"sleep", false);
        when(toDoRepository.findById(any(Integer.class))).thenReturn(Optional.empty());

        assertThrows(IdNotFoundException.class,()->toDoService.updateToDo(2,newToDo));
        verify(toDoRepository,times(1)).findById(2);
        verify(toDoRepository,never()).save(newToDo);
    }

    @Test
    void shouldDeleteToDoByIdWhenDeleteToDoIsCalled() {
        ToDo expectedToDo = new ToDo(1,"eat", false);
        doNothing().when(toDoRepository).deleteById(expectedToDo.getId());

        toDoService.deleteToDo(expectedToDo.getId());

        verify(toDoRepository,times(1)).deleteById(expectedToDo.getId());
    }

}
