Tutorial Spring
Exemplo To do List

Vamos criar uma aplicação web simples de To-do List usando Spring Boot, Thymeleaf e Spring Data JPA com um banco de dados em memória (H2). Esta aplicação permitirá que você adicione, visualize e remova tarefas.

Passos para criar a aplicação
1. Configurar o Projeto
Primeiro, precisamos configurar um novo projeto Spring Boot. Você pode fazer isso manualmente ou usar o Spring Initializr. Aqui estão as dependências necessárias:
Spring Web
Thymeleaf
Spring Data JPA
H2 Database
2. Estrutura do Projeto
Aqui está a estrutura básica do projeto:
css
Copiar código
src
└── main
    ├── java
    │   └── com
    │       └── exemplo
    │           └── todolist
    │               ├── TodoListApplication.java
    │               ├── controller
    │               │   └── TodoController.java
    │               ├── model
    │               │   └── Todo.java
    │               └── repository
    │                   └── TodoRepository.java
    └── resources
        ├── application.properties
        ├── templates
        │   ├── index.html
        └── static

3. Implementação do Código
3.1. Todo Model
Vamos criar uma classe Todo que representa uma tarefa.
java
Copiar código
package com.exemplo.todolist.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private boolean completed;

    // Getters e setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}

3.2. TodoRepository Interface
Criar uma interface TodoRepository que extende JpaRepository.
java
Copiar código
package com.exemplo.todolist.repository;

import com.exemplo.todolist.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {
}

3.3. TodoController
Implementar o controlador para lidar com as requisições HTTP.
java
Copiar código
package com.exemplo.todolist.controller;

import com.exemplo.todolist.model.Todo;
import com.exemplo.todolist.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class TodoController {

    @Autowired
    private TodoRepository todoRepository;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("todos", todoRepository.findAll());
        model.addAttribute("newTodo", new Todo());
        return "index";
    }

    @PostMapping("/add")
    public String addTodo(@ModelAttribute Todo todo) {
        todoRepository.save(todo);
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String deleteTodo(@PathVariable Long id) {
        todoRepository.deleteById(id);
        return "redirect:/";
    }
}

3.4. index.html
Criar o arquivo index.html em src/main/resources/templates.
html
Copiar código
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>To-do List</title>
</head>
<body>
    <h1>To-do List</h1>
    <form th:action="@{/add}" th:object="${newTodo}" method="post">
        <input type="text" th:field="*{title}" placeholder="Add new task"/>
        <button type="submit">Add</button>
    </form>
    <ul>
        <li th:each="todo : ${todos}">
            <span th:text="${todo.title}"></span>
            <a th:href="@{/delete/{id}(id=${todo.id})}">Delete</a>
        </li>
    </ul>
</body>
</html>

4. Configuração do application.properties
Configurar o banco de dados H2 no arquivo application.properties.
properties
Copiar código
spring.h2.console.enabled=true
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=update

5. Classe Principal TodoListApplication
Criar a classe principal para rodar a aplicação.
java
Copiar código
package com.exemplo.todolist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TodoListApplication {

    public static void main(String[] args) {
        SpringApplication.run(TodoListApplication.class, args);
    }
}

6. Executar a Aplicação
Com tudo configurado, você pode executar a aplicação. Acesse http://localhost:8080 no seu navegador para interagir com a lista de tarefas.
Conclusão
Essa aplicação é um exemplo básico de como usar Spring Boot, Thymeleaf, e Spring Data JPA para criar uma aplicação web com um banco de dados em memória. Você pode expandir este projeto adicionando mais funcionalidades, como edição de tarefas, categorias, ou até mesmo autenticação de usuários.
