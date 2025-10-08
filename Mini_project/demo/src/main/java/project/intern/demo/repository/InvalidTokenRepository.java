package project.intern.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.intern.demo.entity.InvalidToken;

@Repository
public interface InvalidTokenRepository extends JpaRepository<InvalidToken, String> {
}
