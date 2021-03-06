package com.tw.todo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tw.todo.exception.IdNotFoundException;
import com.tw.todo.exception.ToDoAlreadyExistsException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(ToDoController.class)
public class ToDoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ToDoService toDoService;

    @Test
    public void shouldReturnAllToDosAndStatusAsOkWhenGetToDosEndPointIsAccessed() throws Exception {
        List<ToDo> toDos = List.of(new ToDo(1, "eat", false), new ToDo(2, "sleep", true));
        when(toDoService.getToDos()).thenReturn(toDos);

        mockMvc.perform(MockMvcRequestBuilders.get("/todos")).andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2))).andDo(print());

        verify(toDoService, times(1)).getToDos();
    }

    @Test
    public void shouldReturnToDoByIdAndStatusAsOkWhenGetToDoEndPointIsAccessed() throws Exception {
        ToDo toDo = new ToDo(1, "eat", false);
        when(toDoService.getToDo(toDo.getId())).thenReturn(toDo);

        mockMvc.perform(MockMvcRequestBuilders.get("/todo/{id}", toDo.getId())).andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.id").value(toDo.getId())).andExpect(jsonPath("$.text").value(toDo.getText())).andExpect(jsonPath("$.completed").value(toDo.isCompleted())).andDo(print());
        verify(toDoService, times(1)).getToDo(toDo.getId());
    }

    @Test
    public void shouldReturnStatusAsNotFoundWhenGetToDoEndPointIsAccessedAndIdDoesNotExists() throws Exception {
        when(toDoService.getToDo(any(Integer.class))).thenThrow(IdNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/todo/{id}", 2)).andExpect(status().isNotFound()).andDo(print());
        verify(toDoService, times(1)).getToDo(2);
    }

    @Test
    public void shouldReturnSavedToDoAndStatusAsOkWhenPostToDoEndPointIsAccessed() throws Exception {
        ToDo toDo = new ToDo(1, "eat", false);
        when(toDoService.saveToDo(toDo)).thenReturn(toDo);

        mockMvc.perform(MockMvcRequestBuilders.post("/todo").content(objectMapper.writeValueAsString(toDo)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.id").value(toDo.getId())).andExpect(jsonPath("$.text").value(toDo.getText())).andExpect(jsonPath("$.completed").value(toDo.isCompleted())).andDo(print());
        verify(toDoService, times(1)).saveToDo(toDo);
    }

    @Test
    public void shouldReturnStatusAsConflictWhenPostToDoEndPointIsAccessedAndToDoAlreadyExists() throws Exception {
        ToDo toDo = new ToDo(1, "eat", false);
        when(toDoService.saveToDo(any(ToDo.class))).thenThrow(ToDoAlreadyExistsException.class);

        mockMvc.perform(MockMvcRequestBuilders.post("/todo").content(objectMapper.writeValueAsString(toDo)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isConflict()).andDo(print());
        verify(toDoService, times(1)).saveToDo(toDo);
    }

    @Test
    public void shouldReturnUpdatedToDoAndStatusAsCreatedWhenPutToDoEndPointIsAccessed() throws Exception {
        ToDo toDo = new ToDo(1, "eat", false);
        ToDo newToDo = new ToDo(1, "sing", false);
        when(toDoService.updateToDo(toDo.getId(), newToDo)).thenReturn(newToDo);

        mockMvc.perform(MockMvcRequestBuilders.put("/todo/{id}", toDo.getId()).content(objectMapper.writeValueAsString(newToDo)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.id").value(newToDo.getId())).andExpect(jsonPath("$.text").value(newToDo.getText())).andExpect(jsonPath("$.completed").value(newToDo.isCompleted())).andDo(print());
        verify(toDoService, times(1)).updateToDo(toDo.getId(), newToDo);
    }

    @Test
    public void shouldReturnStatusAsNotFoundWhenPutToDoEndPointIsAccessedAndIdDoesNotExists() throws Exception {
        ToDo toDo = new ToDo(1, "eat", false);
        when(toDoService.updateToDo(any(Integer.class), any(ToDo.class))).thenThrow(IdNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.put("/todo/{id}", 2).content(objectMapper.writeValueAsString(toDo)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound()).andDo(print());
        verify(toDoService, times(1)).updateToDo(2, toDo);
    }

    @Test
    void shouldDeleteToDoByIdAndReturnStatusAsOkWhenDeleteToDoEndPointIsAccessed() throws Exception {
        ToDo toDo = new ToDo(1, "eat", false);
        doNothing().when(toDoService).deleteToDo(toDo.getId());

        mockMvc.perform(MockMvcRequestBuilders.delete("/todo/{id}", toDo.getId())).andExpect(status().isOk()).andDo(print());

        verify(toDoService, times(1)).deleteToDo(toDo.getId());
    }

}
