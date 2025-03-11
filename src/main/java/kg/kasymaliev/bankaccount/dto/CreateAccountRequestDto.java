package kg.kasymaliev.bankaccount.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAccountRequestDto {
  @NotNull(message = "Account can not be empty")
  private String accountNumber;

  @DecimalMin(value = "0.0", inclusive = false, message = "Account balance can not be negative")
  private BigDecimal balance;
}
