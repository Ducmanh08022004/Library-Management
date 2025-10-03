package project.intern.demo.service.Impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import project.intern.demo.dto.request.borrowedRecord.BorrowedRecordCreateRequest;
import project.intern.demo.dto.request.borrowedRecord.BorrowedRecordUpdateRequest;
import project.intern.demo.dto.response.BorrowedRecordResponse;
import project.intern.demo.entity.Book;
import project.intern.demo.entity.BorrowedRecord;
import project.intern.demo.entity.User;
import project.intern.demo.exception.AppException;
import project.intern.demo.exception.ErrorCode;
import project.intern.demo.repository.BookRepository;
import project.intern.demo.repository.BorrowedRecordRepository;
import project.intern.demo.repository.UserRepository;
import project.intern.demo.service.BorrowedRecordService;

import java.util.List;
@Service
public class BorrowedRecordServiceImpl implements BorrowedRecordService {
    private final BorrowedRecordRepository borrowedRecordRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    @Autowired
    public BorrowedRecordServiceImpl(BorrowedRecordRepository borrowedRecordRepository, UserRepository userRepository, BookRepository bookRepository) {
        this.borrowedRecordRepository = borrowedRecordRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }
    private BorrowedRecordResponse maptoResponse(BorrowedRecord borrowedRecord)
    {
        return new BorrowedRecordResponse(
                borrowedRecord.getId(),
                borrowedRecord.getBorrowDate(),
                borrowedRecord.getReturnDate(),
                borrowedRecord.getDueDate(),
                borrowedRecord.getStatus(),
                borrowedRecord.getUser().getId(),
                borrowedRecord.getBook().getId()
        );
    }

    @Override
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public List<BorrowedRecordResponse> getAllBorrowedRecord() {

        return borrowedRecordRepository.findAll().stream()
                .map(this::maptoResponse).toList();
    }

    @Override
    @PostAuthorize("hasAuthority('SCOPE_ADMIN') or returnObject.user_id == authentication.tokenAttributes['userId']")
    public BorrowedRecordResponse getBorrowedRecordById(int id) {
        return maptoResponse(borrowedRecordRepository.
                findById(id).
                orElseThrow(()-> new AppException(ErrorCode.BORROWEDRECORD_NOT_EXISTED)));
    }

    @Override
    @Transactional
    public BorrowedRecordResponse addBorrowedRecord(BorrowedRecordCreateRequest borrowedRecordCreateRequest) {
        User user = userRepository.findById(borrowedRecordCreateRequest.getUser_id())
                .orElseThrow(()-> new AppException(ErrorCode.USER_NOT_FOUND));
        Book book = bookRepository.findById(borrowedRecordCreateRequest.getBook_id())
                .orElseThrow(()-> new AppException(ErrorCode.BOOK_NOT_FOUND));
        BorrowedRecord borrowedRecord = new BorrowedRecord();
        borrowedRecord.setBorrowDate(borrowedRecordCreateRequest.getBorrowDate());
        borrowedRecord.setReturnDate(borrowedRecordCreateRequest.getReturnDate());
        borrowedRecord.setDueDate(borrowedRecordCreateRequest.getDueDate());
        borrowedRecord.setStatus(borrowedRecordCreateRequest.getStatus());
        borrowedRecord.setUser(user);
        borrowedRecord.setBook(book);
        book.setAvailable(book.getAvailable() - 1);
        bookRepository.save(book);
        return maptoResponse(borrowedRecordRepository.save(borrowedRecord));
    }

    @Override
    @Transactional
    @PreAuthorize("hasAuthority('SCOPE_ADMIN') or #borrowedRecordUpdateRequest.user_id == authentication.tokenAttributes['userId']")
    public BorrowedRecordResponse updateBorrowedRecord(int id, BorrowedRecordUpdateRequest borrowedRecordUpdateRequest) {
        BorrowedRecord borrowedRecord = borrowedRecordRepository.findById(id)
                .orElseThrow(()-> new AppException(ErrorCode.BORROWEDRECORD_NOT_EXISTED));
        User user = userRepository.findById(borrowedRecordUpdateRequest.getUser_id())
                .orElseThrow(()-> new AppException(ErrorCode.USER_NOT_FOUND));
        Book book = bookRepository.findById(borrowedRecordUpdateRequest.getBook_id())
                .orElseThrow(()-> new AppException(ErrorCode.BOOK_NOT_FOUND));
        borrowedRecord.setBorrowDate(borrowedRecordUpdateRequest.getBorrowDate());
        borrowedRecord.setStatus(borrowedRecordUpdateRequest.getStatus());
        borrowedRecord.setReturnDate(borrowedRecordUpdateRequest.getReturnDate());
        borrowedRecord.setUser(user);
        borrowedRecord.setBook(book);
        book.setAvailable(book.getAvailable()+1);
        bookRepository.save(book);
        return maptoResponse(borrowedRecordRepository.save(borrowedRecord));
    }

    @Override
    @Transactional
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public String deleteBorrowedRecordById(int id) {

        BorrowedRecord borrowedRecord = borrowedRecordRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.BORROWEDRECORD_NOT_EXISTED));
        if (!borrowedRecord.getStatus().equals("RETURNED"))
        {
            throw new AppException(ErrorCode.NOT_RETURNED);
        }
        borrowedRecordRepository.deleteById(id);
        return "BorrowedRecord has been deleted";
    }

    @Override
    @PreAuthorize("#id == authentication.tokenAttributes['userId'] or hasAuthority('SCOPE_ADMIN')")
    public List<BorrowedRecordResponse> getBorrowedRecordByUserId(int id) {
        return borrowedRecordRepository.findByUser_id(id)
                .stream().map(this::maptoResponse)
                .toList();
    }

    @Override
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public List<BorrowedRecordResponse> getBorrowedRecordByBookId(int id) {
        return borrowedRecordRepository.findByBook_id(id)
                .stream().map(this::maptoResponse)
                .toList();
    }
}
