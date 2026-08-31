package io.github.ahmedktarii.digitalsingage.Controllers;

import io.github.ahmedktarii.digitalsingage.DTOS.RegisterRequest;
import io.github.ahmedktarii.digitalsingage.Entities.Roles;
import io.github.ahmedktarii.digitalsingage.Entities.User; // adjust path to match your project
import io.github.ahmedktarii.digitalsingage.Services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
@RequiredArgsConstructor
public class RegisterController {


    @Autowired
    private UserService userService;

    @PostMapping("/register")

    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        // checking the Email existence
        if (userService.doesEmailExist(request.getEmailRequest()) ) {
            return ResponseEntity.badRequest().body(Map.of("message", "Email already in use"));
        }
        // Building the new User
        User newUser = User.builder()
                        .username(request.getUsernameRequest())
                        .email(request.getEmailRequest())
                        .password(request.getPasswordRequest())
                        .role(Roles.client)
                        .createdAt(LocalDateTime.now())
                        .build();
        userService.save(newUser);
        //Adding the User Code Format : U+user.id
        newUser.setUserCode("U" + String.format("%02d", newUser.getId()));
        userService.save(newUser);
        //all good
        return ResponseEntity.ok(Map.of(
                "message", "login successful",
                "username", newUser.getUsername(),
                "email", newUser.getEmail()
        ));


    }


}