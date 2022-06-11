package com.tw.todo;

import com.tw.todo.exception.IdNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ToDoController {

    private final ToDoService toDoService;

    @Autowired
    public ToDoController(ToDoService toDoService) {
        this.toDoService = toDoService;
    }

    @GetMapping("/todos")
    public ResponseEntity getToDos(){
        List<ToDo> todos=toDoService.getToDos();
        return new ResponseEntity(todos, HttpStatus.OK);
    }

    @GetMapping("/todo/{id}")
    public ResponseEntity getToDo(@PathVariable Integer id) {
        try {
            ToDo todo = toDoService.getToDo(id);
            return new ResponseEntity(todo, HttpStatus.OK);
        } catch (IdNotFoundException exception) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

}
