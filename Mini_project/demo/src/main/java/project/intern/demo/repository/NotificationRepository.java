package project.intern.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.intern.demo.entity.Notification;
import project.intern.demo.entity.User;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification,Integer> {
    public List<Notification> findByUser_Id(int id);
    boolean existsByUserAndMessageAndCreateAt(User user, String message, LocalDate createAt);

}
