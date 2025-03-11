package kg.kasymaliev.bankaccount.mapper;

import java.util.List;
import kg.kasymaliev.bankaccount.dto.AccountResponseDto;
import kg.kasymaliev.bankaccount.dto.CreateAccountRequestDto;
import kg.kasymaliev.bankaccount.model.Account;
import kg.kasymaliev.bankaccount.model.User;
import org.springframework.util.CollectionUtils;

public class AccountMapper {
  public static AccountResponseDto toDto(Account account) {
    AccountResponseDto createAccountRequestDto = new AccountResponseDto();
    createAccountRequestDto.setId(account.getId());
    createAccountRequestDto.setBalance(account.getBalance());
    createAccountRequestDto.setAccountNumber(account.getAccountNumber());
    return createAccountRequestDto;
  }

  public static Account toEntity(CreateAccountRequestDto createAccountRequestDto, User user) {
    Account account = new Account();
    account.setBalance(createAccountRequestDto.getBalance());
    account.setAccountNumber(createAccountRequestDto.getAccountNumber());
    account.setUser(user);
    return account;
  }

  public static List<AccountResponseDto> toDtoList(List<Account> accounts) {
    if (CollectionUtils.isEmpty(accounts)) {
      return List.of();
    }
    return accounts.stream().map(AccountMapper::toDto).toList();
  }
}
