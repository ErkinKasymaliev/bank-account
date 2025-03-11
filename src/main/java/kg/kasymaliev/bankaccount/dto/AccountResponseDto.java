package kg.kasymaliev.bankaccount.dto;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountResponseDto {
  private Long id;
  private String accountNumber;
  private BigDecimal balance;
}
