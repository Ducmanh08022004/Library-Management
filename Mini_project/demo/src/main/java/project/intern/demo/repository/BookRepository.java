package project.intern.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.intern.demo.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book,Integer> {
    public Book findByTitle(String title);
}
