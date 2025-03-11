package kg.kasymaliev.bankaccount.service.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.util.List;
import kg.kasymaliev.bankaccount.dto.CreateUserRequestDto;
import kg.kasymaliev.bankaccount.exception.BadRequestException;
import kg.kasymaliev.bankaccount.mapper.UserMapper;
import kg.kasymaliev.bankaccount.model.User;
import kg.kasymaliev.bankaccount.repository.UserRepository;
import kg.kasymaliev.bankaccount.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  @Override
  @Transactional
  public User createUser(CreateUserRequestDto createUserRequestDto) {
    userRepository.findByEmail(createUserRequestDto.getEmail()).ifPresent(u -> {
      throw new BadRequestException("Email already in use");
    });
    User user = UserMapper.toEntity(createUserRequestDto);
    return userRepository.save(user);
  }

  @Override
  public User getUserById(Long id) {
    return userRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("User not found"));
  }

  @Override
  public List<User> getAllUsers() {
    return userRepository.findAll();
  }
}
