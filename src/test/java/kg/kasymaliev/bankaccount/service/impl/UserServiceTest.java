package kg.kasymaliev.bankaccount.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import kg.kasymaliev.bankaccount.dto.CreateUserRequestDto;
import kg.kasymaliev.bankaccount.exception.BadRequestException;
import kg.kasymaliev.bankaccount.model.User;
import kg.kasymaliev.bankaccount.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private UserServiceImpl userService;

  private CreateUserRequestDto createUserRequestDto;
  private User existingUser;

  @BeforeEach
  public void setUp() {
    createUserRequestDto = new CreateUserRequestDto();
    createUserRequestDto.setName("John Doe");
    createUserRequestDto.setEmail("john.doe@example.com");
    existingUser = new User();
    existingUser.setId(1L);
    existingUser.setName("John Doe");
    existingUser.setEmail("john.doe@example.com");
  }

  @Test
  public void testCreateUser_ShouldCreateUser() {
    when(userRepository.findByEmail(createUserRequestDto.getEmail())).thenReturn(Optional.empty());

    when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

    User createdUser = userService.createUser(createUserRequestDto);

    assertNotNull(createdUser);
    assertEquals(createUserRequestDto.getName(), createdUser.getName());
    assertEquals(createUserRequestDto.getEmail(), createdUser.getEmail());
    verify(userRepository, times(1)).save(any());
  }

  @Test
  public void testCreateUser_ShouldThrowException_WhenEmailIsAlreadyUsed() {
    when(userRepository.findByEmail(createUserRequestDto.getEmail())).thenReturn(Optional.of(existingUser));

    BadRequestException exception = assertThrows(BadRequestException.class, () -> userService.createUser(createUserRequestDto));
    assertEquals("Email already in use", exception.getMessage());
    verify(userRepository, never()).save(any());
  }

  @Test
  public void testGetUserById_ShouldReturnUser() {
    when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));

    User foundUser = userService.getUserById(1L);

    assertNotNull(foundUser);
    assertEquals(createUserRequestDto.getName(), foundUser.getName());
    assertEquals(createUserRequestDto.getEmail(), foundUser.getEmail());
  }

  @Test
  public void testGetUserById_ShouldThrowException_WhenUserNotFound() {
    when(userRepository.findById(1L)).thenReturn(Optional.empty());

    EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> userService.getUserById(1L));
    assertEquals("User not found", exception.getMessage());
  }

  @Test
  public void testGetAllUsers_ShouldReturnUsersList() {
    when(userRepository.findAll()).thenReturn(List.of(existingUser));

    List<User> users = userService.getAllUsers();

    assertNotNull(users);
    assertFalse(users.isEmpty());
    assertEquals(1, users.size());
  }
}
