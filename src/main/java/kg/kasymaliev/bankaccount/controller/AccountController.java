package kg.kasymaliev.bankaccount.controller;

import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import kg.kasymaliev.bankaccount.dto.AccountResponseDto;
import kg.kasymaliev.bankaccount.dto.CreateAccountRequestDto;
import kg.kasymaliev.bankaccount.mapper.AccountMapper;
import kg.kasymaliev.bankaccount.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accounts")
public class AccountController {

  private final AccountService accountService;

  @Autowired
  public AccountController(AccountService accountService) {
    this.accountService = accountService;
  }

  @PostMapping("/create/{userId}")
  @ResponseStatus(HttpStatus.CREATED)
  public AccountResponseDto createAccount(@PathVariable Long userId, @Valid @RequestBody CreateAccountRequestDto account) {
    return AccountMapper.toDto(accountService.createAccount(userId, account));
  }

  @PostMapping("/deposit/{accountId}")
  public AccountResponseDto deposit(@PathVariable Long accountId, @RequestParam BigDecimal amount) {
    return AccountMapper.toDto(accountService.deposit(accountId, amount));
  }

  @PostMapping("/withdraw/{accountId}")
  public AccountResponseDto withdraw(@PathVariable Long accountId, @RequestParam BigDecimal amount) {
    return AccountMapper.toDto(accountService.withdraw(accountId, amount));
  }

  @GetMapping("/user/{userId}")
  public List<AccountResponseDto> getAccounts(@PathVariable Long userId) {
    return AccountMapper.toDtoList(accountService.getAccountsByUser(userId));
  }
}
