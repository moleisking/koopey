package com.koopey.api.parser;

import com.koopey.api.ServerApplication;
import com.koopey.api.model.dto.MessageDto;
import com.koopey.api.model.entity.Message;
import com.koopey.api.model.parser.MessageParser;

import java.text.ParseException;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = ServerApplication.class)
@TestPropertySource("classpath:application-test.properties")
public class MessageParserTest {

    @Test
    public void whenMessageConvertToEntity() throws ParseException {

        String json = """
                {
                "id":"00000000-0000-0000-0000-000000000001",
                "name":"test",
                "description":"description",
                "senderId":"00000000-0000-0000-0000-000000000001",
                "receiverId":"00000000-0000-0000-0000-000000000002"
                }
                """;
        Message messageDto = MessageParser.convertToEntity(json);
        assertEquals(messageDto.getDescription(), "description");
        assertEquals(messageDto.getName(), "test");
        assertEquals(messageDto.getId(), "00000000-0000-0000-0000-000000000001");
        assertEquals(messageDto.getSenderId(), "00000000-0000-0000-0000-000000000001");
        assertEquals(messageDto.getReceiverId(), "00000000-0000-0000-0000-000000000002");
    }
}
