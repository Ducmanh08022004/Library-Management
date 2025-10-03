package project.intern.demo.service.Impl;


import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import project.intern.demo.dto.request.book.BookCreateRequest;
import project.intern.demo.dto.request.book.BookUpdateRequest;
import project.intern.demo.dto.response.BookResponse;
import project.intern.demo.entity.Book;
import project.intern.demo.exception.AppException;
import project.intern.demo.exception.ErrorCode;
import project.intern.demo.repository.BookRepository;
import project.intern.demo.service.BookService;

import java.util.List;
@Service
public class BookServiceImpl  implements BookService {
    private final BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    private BookResponse maptoResponse(Book book)
    {
        return new BookResponse(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getGenre(),
                book.getAvailable()
        );
    }
    @Override
    public List<BookResponse> getAllBooks() {
        return bookRepository.findAll()
                .stream().map(this::maptoResponse).toList();
    }

    @Override
    public BookResponse getBookById(int id) {
        return maptoResponse(bookRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.BOOK_NOT_FOUND)));
    }

    @Override
    @Transactional
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public BookResponse addBook(BookCreateRequest bookCreateRequest) {
        Book book = new Book();
        book.setAuthor(bookCreateRequest.getAuthor());
        book.setTitle(bookCreateRequest.getTitle());
        book.setGenre(bookCreateRequest.getGenre());
        book.setAvailable(bookCreateRequest.getAvailable());
        return maptoResponse(bookRepository.save(book));
    }

    @Override
    @Transactional
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public BookResponse updateBook(int id, BookUpdateRequest bookUpdateRequest) {
        Book book = bookRepository.findById(id).orElseThrow(()-> new AppException(ErrorCode.BOOK_NOT_FOUND));
        book.setTitle(bookUpdateRequest.getTitle());
        book.setAuthor(bookUpdateRequest.getAuthor());
        book.setGenre(bookUpdateRequest.getGenre());
        book.setAvailable(bookUpdateRequest.getAvailable());
        return maptoResponse(bookRepository.saveAndFlush(book));
    }

    @Override
    @Transactional
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public String deleteBookById(int id) {
        if (!bookRepository.existsById(id))
        {
            throw new AppException(ErrorCode.BOOK_NOT_FOUND);
        }

        bookRepository.deleteById(id);
        return "Book have deleted";
    }

    @Override
    public BookResponse findByTitle(String title) {
        return maptoResponse(bookRepository.findByTitle(title));
    }
}
