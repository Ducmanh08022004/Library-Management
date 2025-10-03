package project.intern.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.intern.demo.entity.BorrowedRecord;

import java.util.List;

@Repository
public interface BorrowedRecordRepository extends JpaRepository<BorrowedRecord, Integer> {
    public List<BorrowedRecord> findByUser_id(int id);
    public List<BorrowedRecord> findByBook_id(int id);
    public List<BorrowedRecord> findAllByStatus(String status);
}
