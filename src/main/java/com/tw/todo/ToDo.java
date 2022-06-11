package com.tw.todo;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "todos")
public class ToDo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String text;
    private boolean completed;

    public ToDo() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public String toString() {
        return "ToDo{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", completed=" + completed +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ToDo toDo = (ToDo) o;
        return completed == toDo.completed && id.equals(toDo.id) && text.equals(toDo.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text, completed);
    }

    public ToDo(Integer id, String text, boolean completed) {
        this.id = id;
        this.text = text;
        this.completed = completed;
    }

    public ToDo(String text, boolean completed) {
        this.text = text;
        this.completed = completed;
    }

}
