package io.github.ahmedktarii.digitalsingage.Controllers;

import io.github.ahmedktarii.digitalsingage.DTOS.LoginRequest;
import io.github.ahmedktarii.digitalsingage.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class LoginController {

    // calling the user service
    @Autowired
    private UserService userService;;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request ) {
        // checking the user existence
        if (!userService.doesEmailExist(request.getEmailRequest()) )
        {
            return ResponseEntity.badRequest().body(Map.of("message", "user doesn't exist"));
        }
        // checking the credentials email + password
        if(!((Objects.equals(userService.grapPasswordByEmail(request.getEmailRequest()), request.getPasswordRequest())))){
            return ResponseEntity.badRequest().body(Map.of("message", "wrong credentials"));
        }
        else {
            // all good
            return ResponseEntity.ok(Map.of(
                    "message", "login successful",
                    "username", userService.grapUsernameByEmail(request.getEmailRequest()).toString(),
                    "email",request.getEmailRequest().toString()
            ));
        }
    }

}
