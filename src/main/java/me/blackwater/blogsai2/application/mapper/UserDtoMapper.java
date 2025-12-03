package me.blackwater.blogsai2.application.mapper;


import me.blackwater.blogsai2.application.dto.UserDto;
import me.blackwater.blogsai2.application.dto.UserWithIdDto;
import me.blackwater.blogsai2.domain.model.User;
import me.blackwater.blogsai2.domain.model.UserRole;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;

@Component
public class UserDtoMapper {


    public UserDto toDto(User user) {
        return new UserDto(user.getUserName(),user.getPhone().countryCode() + user.getPhone().value(),user.getEmail().value(),user.getRoles().stream().map(UserRole::getValue).collect(Collectors.toSet()));
    }
    public UserWithIdDto toDtoWithId(User user) {
        return new UserWithIdDto(user.getId(),user.getUserName(),user.getPhone().countryCode() + user.getPhone().value(),user.getEmail().value(),user.getRoles().stream().map(UserRole::getValue).collect(Collectors.toSet()));
    }
}
