package kg.kasymaliev.bankaccount.controller;

import jakarta.validation.Valid;
import java.util.List;
import kg.kasymaliev.bankaccount.dto.CreateUserRequestDto;
import kg.kasymaliev.bankaccount.dto.UserResponseDto;
import kg.kasymaliev.bankaccount.mapper.UserMapper;
import kg.kasymaliev.bankaccount.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public UserResponseDto createUser(@Valid @RequestBody CreateUserRequestDto user) {
    return UserMapper.toDto(userService.createUser(user));
  }

  @GetMapping("/{id}")
  public UserResponseDto getUserById(@PathVariable Long id) {
    return UserMapper.toDto(userService.getUserById(id));
  }

  @GetMapping
  public List<UserResponseDto> getAllUsers() {
    return UserMapper.toDtoList(userService.getAllUsers());
  }
}
