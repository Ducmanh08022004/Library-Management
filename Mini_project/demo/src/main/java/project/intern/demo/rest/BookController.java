package project.intern.demo.rest;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.intern.demo.dto.request.book.BookCreateRequest;
import project.intern.demo.dto.request.book.BookUpdateRequest;
import project.intern.demo.dto.response.ApiResponse;
import project.intern.demo.dto.response.BookResponse;
import project.intern.demo.entity.Book;
import project.intern.demo.service.BookService;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ApiResponse<List<BookResponse>> getAllBooks()
    {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(bookService.getAllBooks());
        return apiResponse;
    }
    @GetMapping("title/{title}")
    public ApiResponse<BookResponse> findBookByTitle(@PathVariable String title)
    {
        BookResponse book = bookService.findByTitle(title);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(book);
        return apiResponse;
    }
    @GetMapping("id/{id}")
    public ApiResponse<BookResponse> getBookById(@PathVariable int id)
    {
        BookResponse book = bookService.getBookById(id);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(book);
        return apiResponse;
    }
    @PostMapping()
    public ApiResponse<BookResponse> addBook(@RequestBody BookCreateRequest bookCreateRequest)
    {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(bookService.addBook(bookCreateRequest));
        return apiResponse;
    }
    @PutMapping("/{id}")
    public ApiResponse<BookResponse> updateBook(@PathVariable int id, @RequestBody BookUpdateRequest bookUpdateRequest)
    {
        BookResponse book = bookService.getBookById(id);
        if (book == null)
        {
            throw new EntityNotFoundException("Not found");
        }
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(bookService.updateBook(id,bookUpdateRequest));
        return apiResponse;
    }
    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteBook(@PathVariable int id)
    {
        BookResponse book = bookService.getBookById(id);
        if (book == null)
        {
            throw new EntityNotFoundException("Not found");
        }
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage(bookService.deleteBookById(id));
        return apiResponse;
    }

}
