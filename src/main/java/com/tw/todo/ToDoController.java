package com.tw.todo;

import com.tw.todo.exception.IdNotFoundException;
import com.tw.todo.exception.ToDoAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/todo")
    public ResponseEntity saveToDo(@RequestBody ToDo toDo){
        try {
            ToDo todo = toDoService.saveToDo(toDo);
            return new ResponseEntity(todo, HttpStatus.OK);
        } catch (ToDoAlreadyExistsException exception) {
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
    }

    @PutMapping("/todo/{id}")
    public ResponseEntity updateTodo(@PathVariable Integer id ,@RequestBody ToDo toDo) throws IdNotFoundException {
        try {
            ToDo updatedToDo = toDoService.updateToDo(id,toDo);
            return new ResponseEntity(updatedToDo, HttpStatus.CREATED);
        }catch (IdNotFoundException exception){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

}
