package kg.kasymaliev.bankaccount.repository;

import java.util.List;
import java.util.Optional;
import kg.kasymaliev.bankaccount.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
  Optional<User> findByEmail(String email);
  List<User> findAll();
}
