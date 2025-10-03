package project.intern.demo.rest;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.intern.demo.dto.request.borrowedRecord.BorrowedRecordCreateRequest;
import project.intern.demo.dto.request.borrowedRecord.BorrowedRecordUpdateRequest;
import project.intern.demo.dto.response.ApiResponse;
import project.intern.demo.dto.response.BorrowedRecordResponse;
import project.intern.demo.entity.BorrowedRecord;
import project.intern.demo.service.BorrowedRecordService;

import java.util.List;

@RestController
@RequestMapping("/record")
public class BorrowedRecordController {
    private final BorrowedRecordService borrowedRecordService;

    public BorrowedRecordController(BorrowedRecordService borrowedRecordService) {
        this.borrowedRecordService = borrowedRecordService;
    }


    @GetMapping
    public ApiResponse<List<BorrowedRecordResponse>> getAllBorrowedRecord()
    {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(borrowedRecordService.getAllBorrowedRecord());
        return apiResponse;
    }
    @GetMapping("/{id}")
    public ApiResponse<BorrowedRecordResponse> getBorrowedRecordById(@PathVariable int id)
    {
        BorrowedRecordResponse borrowedRecord = borrowedRecordService.getBorrowedRecordById(id);
        if (borrowedRecord==null)
        {
            throw new EntityNotFoundException("Not found");

        }
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(borrowedRecord);
        return apiResponse;
    }
    @PutMapping("/{id}")
    public ApiResponse<BorrowedRecordResponse> updateBorrowedRecord(@PathVariable int id,@RequestBody BorrowedRecordUpdateRequest borrowedRecordUpdateRequest)
    {
        BorrowedRecordResponse borrowedRecord = borrowedRecordService.getBorrowedRecordById(id);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(borrowedRecordService.updateBorrowedRecord(id,borrowedRecordUpdateRequest));
        return apiResponse;
    }
    @PostMapping
    public ApiResponse<BorrowedRecord> addBorrowedRecord(@RequestBody BorrowedRecordCreateRequest borrowedRecordCreateRequest)
    {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(borrowedRecordService.addBorrowedRecord(borrowedRecordCreateRequest));
        return apiResponse;

    }
    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteRecordById(@PathVariable int id)
    {
        BorrowedRecordResponse borrowedRecord = borrowedRecordService.getBorrowedRecordById(id);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage(borrowedRecordService.deleteBorrowedRecordById(id));
        return apiResponse;
    }
    @GetMapping("/user/{user_id}")
    public ApiResponse<List<BorrowedRecord>> getByUserId(@PathVariable int user_id)
    {
        List<BorrowedRecordResponse> borrowedRecord = borrowedRecordService.getBorrowedRecordByUserId(user_id);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(borrowedRecord);
        return apiResponse;
    }
    @GetMapping("/book/{book_id}")
    public  ApiResponse<List<BorrowedRecord>> getByBookId(@PathVariable int book_id)
    {
        List<BorrowedRecordResponse> borrowedRecord = borrowedRecordService.getBorrowedRecordByBookId(book_id);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(borrowedRecord);
        return apiResponse;
    }
}
