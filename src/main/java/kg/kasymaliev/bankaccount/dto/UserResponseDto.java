package kg.kasymaliev.bankaccount.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDto {
  private Long id;
  private String name;
  private String email;
  private LocalDateTime registrationDate;
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private List<AccountResponseDto> accounts;
}
