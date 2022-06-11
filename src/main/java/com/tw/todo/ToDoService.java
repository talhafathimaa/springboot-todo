package com.tw.todo;

import com.tw.todo.exception.IdNotFoundException;
import com.tw.todo.exception.ToDoAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public ToDo saveToDo(ToDo toDo) throws ToDoAlreadyExistsException {
        Optional<ToDo> toDoOptional = toDoRepository.findByText(toDo.getText());
        if(toDoOptional.isPresent()){
            throw new ToDoAlreadyExistsException();
        }
        return toDoRepository.save(toDo);
    }

    public ToDo updateToDo(Integer id, ToDo toDo) throws IdNotFoundException {
        ToDo existingToDo=toDoRepository.findById(id).orElseThrow(IdNotFoundException::new);
        existingToDo.setText(toDo.getText());
        existingToDo.setCompleted(toDo.isCompleted());
        return toDoRepository.save(existingToDo);
    }

}
