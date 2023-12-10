package com.example.demo.integration.user;

import com.example.demo.dto.AuthDto;
import com.example.demo.dto.ResponseDto;
import com.example.demo.dto.UserDto;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.junit.AfterClass;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.event.annotation.AfterTestClass;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.context.event.annotation.BeforeTestMethod;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Tag("IT")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserTestIT {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserService userService;

    final String username = "IntegrationTestingAccount123456789";
    final String password = "test";
    final String email = "IntegrationTestingAccount123456789";
    final String role = Role.USER;

    UserDto userDto = new UserDto(username, password, email, role);

    public boolean hasUsernameInJwt(String token) {
        String[] jwtParts = token.split("\\.");
        // Decode username part from jwt
        String decodedPayload = new String(Base64.getDecoder().decode(jwtParts[1]));
        return (decodedPayload).contains(username);
    }

    @Test
    @Order(1)
    public void preconditions() {
        userService.deleteUser(username);
    }

    @Test
    @Order(2)
    public void shouldRegisterAccount() {
        String url = "http://localhost:" + port + "/core/register";

        ResponseEntity<AuthDto> responseEntity = restTemplate.postForEntity(url, userDto, AuthDto.class);

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertNotNull(responseEntity.getBody());
        Assertions.assertNull(responseEntity.getBody().errors);
        Assertions.assertTrue(hasUsernameInJwt(responseEntity.getBody().result));
    }

    @Test
    @Order(3)
    public void shouldLoginAccount() {
        String url = "http://localhost:" + port + "/core/login";

        ResponseEntity<AuthDto> responseEntity = restTemplate.postForEntity(url, userDto, AuthDto.class);

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertNotNull(responseEntity.getBody());
        Assertions.assertTrue(hasUsernameInJwt(responseEntity.getBody().result));

    }

    @Test
    @Order(4)
    public void shouldReturnErrorOnCreatingDuplicateAccount() {
        String url = "http://localhost:" + port + "/core/register";

        ResponseEntity<AuthDto> responseEntity = restTemplate.postForEntity(url, userDto, AuthDto.class);

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertNotNull(responseEntity.getBody());
        Assertions.assertEquals(responseEntity.getBody().errors, "Eroare la autentificare");

    }

    @Test
    @Order(5)
    public void shouldReturnErrorOnLoggingWithWrongPassword() {
        String url = "http://localhost:" + port + "/core/login";

        UserDto userDtoWrongPassword = new UserDto(username, "somewrongpasswordhere", email, role);

        ResponseEntity<AuthDto> responseEntity = restTemplate.postForEntity(url, userDtoWrongPassword, AuthDto.class);

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertNotNull(responseEntity.getBody());
        Assertions.assertEquals(responseEntity.getBody().errors, "Eroare la autentificare");

    }

    @Test
    @Order(6)
    public void cleanup() {
        userService.deleteUser(username);
    }

}
