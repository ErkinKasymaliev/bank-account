package kg.kasymaliev.bankaccount.service;

import java.util.List;
import kg.kasymaliev.bankaccount.dto.CreateUserRequestDto;
import kg.kasymaliev.bankaccount.model.User;

public interface UserService {
  User createUser(CreateUserRequestDto user);
  User getUserById(Long id);
  List<User> getAllUsers();
}
