package phone_book.repos;

import phone_book.domain.PhoneNumber;
import phone_book.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhoneRepository extends JpaRepository<PhoneNumber, Integer> {
    List<PhoneNumber> findAllByUser(User user);
}
