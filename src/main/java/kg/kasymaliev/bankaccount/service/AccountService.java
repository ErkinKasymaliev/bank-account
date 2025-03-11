package kg.kasymaliev.bankaccount.service;

import java.math.BigDecimal;
import java.util.List;
import kg.kasymaliev.bankaccount.dto.CreateAccountRequestDto;
import kg.kasymaliev.bankaccount.model.Account;

public interface AccountService {
  Account createAccount(Long userId, CreateAccountRequestDto createAccountRequestDto);
  Account deposit(Long accountId, BigDecimal amount);
  Account withdraw(Long accountId, BigDecimal amount);
  List<Account> getAccountsByUser(Long userId);
}
