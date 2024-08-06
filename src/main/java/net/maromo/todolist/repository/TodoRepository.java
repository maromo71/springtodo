package net.maromo.todolist.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import net.maromo.todolist.model.Todo;
public interface TodoRepository extends JpaRepository<Todo, Long> {
    
}
