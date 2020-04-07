package phone_book.repos;

import org.springframework.stereotype.Repository;
import phone_book.domain.Person;
import phone_book.domain.PhoneNumber;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository
public interface PhoneRepository extends JpaRepository<PhoneNumber, Integer> {
    List<PhoneNumber> findAllByPerson(Person person);
}
