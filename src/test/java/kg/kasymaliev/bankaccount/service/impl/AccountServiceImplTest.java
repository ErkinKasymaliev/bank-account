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
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import kg.kasymaliev.bankaccount.dto.CreateAccountRequestDto;
import kg.kasymaliev.bankaccount.exception.BadRequestException;
import kg.kasymaliev.bankaccount.model.Account;
import kg.kasymaliev.bankaccount.model.User;
import kg.kasymaliev.bankaccount.repository.AccountRepository;
import kg.kasymaliev.bankaccount.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AccountServiceImplTest {

  @Mock
  private AccountRepository accountRepository;

  @InjectMocks
  private AccountServiceImpl accountService;
  @Mock
  private UserService userService;

  private kg.kasymaliev.bankaccount.model.User user;
  private CreateAccountRequestDto createAccountRequestDto;
  private Account account;

  @BeforeEach
  public void setUp() {
    user = new User();
    user.setId(1L);
    user.setName("John Doe");
    user.setEmail("john.doe@example.com");

    createAccountRequestDto = new CreateAccountRequestDto();
    createAccountRequestDto.setAccountNumber("1234567890");
    createAccountRequestDto.setBalance(BigDecimal.valueOf(100));

    account = new Account();
    account.setId(1L);
    account.setAccountNumber("1234567890");
    account.setBalance(BigDecimal.valueOf(100));
  }

  @Test
  public void testCreateAccount_ShouldCreateAccount() {
    when(accountRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
    when(userService.getUserById(1L)).thenReturn(user);
    Account createdAccount = accountService.createAccount(1L, createAccountRequestDto);

    assertNotNull(createdAccount);
    assertEquals(createAccountRequestDto.getAccountNumber(), createdAccount.getAccountNumber());
    assertEquals(createAccountRequestDto.getBalance(), createdAccount.getBalance());
    verify(accountRepository, times(1)).save(any(Account.class));
  }

  @Test
  public void testDeposit_ShouldDepositAmountToAccount() {
    when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
    when(accountRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

    Account updatedAccount = accountService.deposit(1L, BigDecimal.valueOf(200));

    assertEquals(300, updatedAccount.getBalance().doubleValue());
    verify(accountRepository, times(1)).save(any(Account.class));
  }

  @Test
  public void testDeposit_AmountMustBeGreaterThanZero() {
    BadRequestException exception = assertThrows(BadRequestException.class,
        () -> accountService.deposit(1L, BigDecimal.valueOf(-200)));
    assertEquals("Amount must be greater than zero", exception.getMessage());
    verify(accountRepository, never()).save(any(Account.class));
  }

  @Test
  public void testWithdraw_ShouldWithdrawAmountFromAccount() {
    when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
    when(accountRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

    Account updatedAccount = accountService.withdraw(1L, BigDecimal.valueOf(50));

    assertEquals(BigDecimal.valueOf(50), updatedAccount.getBalance());
    verify(accountRepository, times(1)).save(any(Account.class));
  }

  @Test
  public void testWithdraw_AmountMustBeGreaterThanZero() {
    BadRequestException exception = assertThrows(BadRequestException.class,
        () -> accountService.withdraw(1L, BigDecimal.valueOf(-200)));
    assertEquals("Amount must be greater than zero", exception.getMessage());
    verify(accountRepository, never()).save(any(Account.class));
  }

  @Test
  public void testWithdraw_ShouldThrowException_WhenNotEnoughBalance() {

    when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

    BadRequestException exception = assertThrows(BadRequestException.class,
        () -> accountService.withdraw(1L, BigDecimal.valueOf(150)));
    assertEquals("Insufficient funds in the account", exception.getMessage());
    verify(accountRepository, never()).save(any(Account.class));
  }

  @Test
  public void testGetAccountsByUser_ShouldReturnAccountsList() {
    when(accountRepository.findByUser(user)).thenReturn(List.of(account));
    when(userService.getUserById(1L)).thenReturn(user);

    List<Account> accounts = accountService.getAccountsByUser(1L);

    assertNotNull(accounts);
    assertFalse(accounts.isEmpty());
    assertEquals(1, accounts.size());
  }

  @Test
  public void testCreateAccount_ShouldThrowException_WhenUserNotFound() {
    when(accountRepository.save(any()))
        .thenThrow(new EntityNotFoundException("User not found"));

    EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> accountService.createAccount(1L, createAccountRequestDto));
    assertEquals("User not found", exception.getMessage());
  }
}
