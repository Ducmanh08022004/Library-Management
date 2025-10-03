package project.intern.demo.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import project.intern.demo.dto.request.book.BookCreateRequest;
import project.intern.demo.dto.request.book.BookUpdateRequest;
import project.intern.demo.dto.response.BookResponse;
import project.intern.demo.entity.Book;

import java.util.List;


public interface BookService {
    public List<BookResponse> getAllBooks();
    public BookResponse getBookById(int id);
    public BookResponse addBook(BookCreateRequest book);
    public BookResponse updateBook(int id, BookUpdateRequest book);
    public String deleteBookById(int id);
    public BookResponse findByTitle(String title);
}
