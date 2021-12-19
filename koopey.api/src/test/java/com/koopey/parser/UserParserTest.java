package com.koopey.parser;

/*import com.koopey.api.model.dto.UserRegisterDto;
import com.koopey.api.model.entity.User;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;*/
import org.springframework.boot.test.context.SpringBootTest;

// import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UserParserTest {

 // private ModelMapper modelMapper = new ModelMapper();
/*
    @Test
    public void whenConvertUserEntityToUserRegisterDtothenCorrect() {
        User userEntity = new User();
        userEntity.setId(UUID.randomUUID());
        userEntity.setName("name");
        userEntity.setEmail("fake@domain.com");

        UserRegisterDto userDto = modelMapper.map(userEntity, UserRegisterDto.class);
        assertEquals(userEntity.getName(), userDto.getName());
        assertEquals(userEntity.getEmail(), userDto.getEmail());
    }

    public void whenConvertUserRegisterDtoToUserEntityhenCorrect() {
        UserRegisterDto userDto = new UserRegisterDto();
        userDto.setName("name");
        userDto.setEmail("fake@domain.com");

        User userEntity = modelMapper.map(userDto, User.class);
        assertEquals(userEntity.getName(), userDto.getName());
        assertEquals(userEntity.getEmail(), userDto.getEmail());
    }*/
}
