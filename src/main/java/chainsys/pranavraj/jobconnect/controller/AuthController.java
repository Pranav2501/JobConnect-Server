package chainsys.pranavraj.jobconnect.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import chainsys.pranavraj.jobconnect.model.User;
import chainsys.pranavraj.jobconnect.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication Controller", description = "Operations related to user authentication")
public class AuthController {

    @Autowired
    private UserService userService;

    @Operation(summary = "User login", description = "Authenticate a user by their username and password.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Login successful",
                content = {@Content(mediaType = "application/json", schema = @Schema(implementation = AuthResponse.class))}),
        @ApiResponse(responseCode = "401", description = "Invalid username or password", content = @Content)
    })
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginUser) {
        User user = userService.getUserByUsername(loginUser.getUsername());

        if (user == null || !user.getPassword().equals(loginUser.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }

        // Send back user role along with the response
        return ResponseEntity.ok().body(new AuthResponse("Login successful", user.getRole()));
    }

    // Inner class to structure the response
    public static class AuthResponse {
        private String message;
        private String role;

        public AuthResponse(String message, String role) {
            this.message = message;
            this.role = role;
        }

        public String getMessage() {
            return message;
        }

        public String getRole() {
            return role;
        }
    }
}
