package kg.kasymaliev.bankaccount.mapper;

import java.util.List;
import kg.kasymaliev.bankaccount.dto.CreateUserRequestDto;
import kg.kasymaliev.bankaccount.dto.UserResponseDto;
import kg.kasymaliev.bankaccount.model.User;
import org.springframework.util.CollectionUtils;

public class UserMapper {
  public static User toEntity(CreateUserRequestDto dto) {
    User user = new User();
    user.setName(dto.getName());
    user.setEmail(dto.getEmail());
    return user;
  }

  public static UserResponseDto toDto(User user) {
    UserResponseDto dto = new UserResponseDto();
    dto.setId(user.getId());
    dto.setName(user.getName());
    dto.setEmail(user.getEmail());
    dto.setRegistrationDate(user.getRegistrationDate());
    dto.setAccounts(AccountMapper.toDtoList(user.getAccounts()));
    return dto;
  }

  public static List<UserResponseDto> toDtoList(List<User> users) {
    if (CollectionUtils.isEmpty(users)) {
      return List.of();
    }
    return users.stream().map(UserMapper::toDto).toList();
  }
}
