package com.tw.todo;

import com.tw.todo.exception.IdNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToDoService {

    private final ToDoRepository toDoRepository;

    @Autowired
    public ToDoService(ToDoRepository toDoRepository) {
        this.toDoRepository = toDoRepository;
    }

    public List<ToDo> getToDos() {
        return toDoRepository.findAll();
    }

    public ToDo getToDo(Integer id) throws IdNotFoundException {
        return toDoRepository.findById(id).orElseThrow(IdNotFoundException::new);
    }

}
