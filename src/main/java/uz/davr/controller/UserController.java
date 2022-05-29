package uz.davr.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.davr.dto.response.RestAPIResponse;
import uz.davr.model.User;
import uz.davr.service.UserService;



@RestController
@RequestMapping("/api/users")
public class UserController {


    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public HttpEntity<?> saveUser(@RequestBody User user) {
        RestAPIResponse restAPIResponse = userService.saveUser(user);
        return ResponseEntity.ok(restAPIResponse);
    }


}
