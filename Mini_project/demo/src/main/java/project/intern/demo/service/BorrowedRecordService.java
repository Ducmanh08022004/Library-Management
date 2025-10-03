package project.intern.demo.service;



import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import project.intern.demo.dto.request.borrowedRecord.BorrowedRecordCreateRequest;
import project.intern.demo.dto.request.borrowedRecord.BorrowedRecordUpdateRequest;
import project.intern.demo.dto.response.BorrowedRecordResponse;
import project.intern.demo.entity.BorrowedRecord;

import java.util.List;

public interface BorrowedRecordService {
    public List<BorrowedRecordResponse> getAllBorrowedRecord();
    public BorrowedRecordResponse getBorrowedRecordById(int id);
    public BorrowedRecordResponse addBorrowedRecord(BorrowedRecordCreateRequest borrowedRecordCreateRequest);
    public BorrowedRecordResponse updateBorrowedRecord(int id, BorrowedRecordUpdateRequest borrowedRecordUpdateRequest);
    public String deleteBorrowedRecordById(int id);
    public List<BorrowedRecordResponse> getBorrowedRecordByUserId(int id);
    public List<BorrowedRecordResponse> getBorrowedRecordByBookId(int id);
}
