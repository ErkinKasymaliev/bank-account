package kg.kasymaliev.bankaccount.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserRequestDto {
  @NotEmpty(message = "Name can not be empty")
  private String name;

  @Email(message = "Invalid email")
  @NotEmpty(message = "Email can not be empty")
  private String email;
}
