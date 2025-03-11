package kg.kasymaliev.bankaccount.repository;

import java.util.List;
import kg.kasymaliev.bankaccount.model.Account;
import kg.kasymaliev.bankaccount.model.User;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account, Long> {
  List<Account> findByUser(User user);
  List<Account> findAll();
}
