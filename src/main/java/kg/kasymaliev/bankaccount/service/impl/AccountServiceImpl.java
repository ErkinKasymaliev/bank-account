package kg.kasymaliev.bankaccount.service.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import kg.kasymaliev.bankaccount.dto.CreateAccountRequestDto;
import kg.kasymaliev.bankaccount.exception.BadRequestException;
import kg.kasymaliev.bankaccount.mapper.AccountMapper;
import kg.kasymaliev.bankaccount.model.Account;
import kg.kasymaliev.bankaccount.model.User;
import kg.kasymaliev.bankaccount.repository.AccountRepository;
import kg.kasymaliev.bankaccount.service.AccountService;
import kg.kasymaliev.bankaccount.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

  private final AccountRepository accountRepository;
  private final UserService userService;

  @Override
  @Transactional
  public Account createAccount(Long userId, CreateAccountRequestDto createAccountRequestDto) {
    User existingUser = userService.getUserById(userId);
    Account newAccount = AccountMapper.toEntity(createAccountRequestDto, existingUser);
    return accountRepository.save(newAccount);
  }

  @Override
  @Transactional
  public Account deposit(Long accountId, BigDecimal amount) {
    if (amount.compareTo(BigDecimal.ZERO) < 1) {
      throw new BadRequestException("Amount must be greater than zero");
    }
    Account account = accountRepository.findById(accountId)
        .orElseThrow(() -> new EntityNotFoundException("Account not found"));
    account.setBalance(account.getBalance().add(amount));
    return accountRepository.save(account);
  }

  @Override
  @Transactional
  public Account withdraw(Long accountId, BigDecimal amount) {
    if (amount.compareTo(BigDecimal.ZERO) < 1) {
      throw new BadRequestException("Amount must be greater than zero");
    }
    Account account = accountRepository.findById(accountId)
        .orElseThrow(() -> new EntityNotFoundException("Account not found"));
    if (account.getBalance().compareTo(amount) < 0) {
      throw new BadRequestException("Insufficient funds in the account");
    }
    account.setBalance(account.getBalance().subtract(amount));
    return accountRepository.save(account);
  }

  @Override
  public List<Account> getAccountsByUser(Long userId) {
    User existingUser = userService.getUserById(userId);
    return accountRepository.findByUser(existingUser);
  }
}
