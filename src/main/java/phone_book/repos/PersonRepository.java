package phone_book.repos;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import phone_book.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {

    @Query("select distinct p from Person p left join  p.numbers n where p.name like ?1 or n.number like ?2")
    List<Person> findAllByNameLAndNumber(String name, String number);
}
