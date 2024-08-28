package chainsys.pranavraj.jobconnect.controller;

import chainsys.pranavraj.jobconnect.model.User;
import chainsys.pranavraj.jobconnect.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
@Tag(name = "User Controller", description = "Operations related to managing users by admin")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "Get all users", description = "Retrieve a list of all users. Only accessible by admins.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved list of users",
                content = {@Content(mediaType = "application/json", schema = @Schema(implementation = User.class))}),
        @ApiResponse(responseCode = "403", description = "Access denied", content = @Content)
    })
    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers(@RequestHeader("Role") String role) {
        if (!role.equals("Admin")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
        }
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @Operation(summary = "Get user by ID", description = "Retrieve a user by their ID. Only accessible by admins.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved the user",
                content = {@Content(mediaType = "application/json", schema = @Schema(implementation = User.class))}),
        @ApiResponse(responseCode = "403", description = "Access denied", content = @Content),
        @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    @GetMapping("/users/{userId}")
    public ResponseEntity<?> getUserById(
            @RequestHeader("Role") @Parameter(description = "Role of the requester", required = true) String role,
            @PathVariable @Parameter(description = "ID of the user to retrieve", required = true) String userId) {
        if (!role.equals("Admin")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
        }
        Optional<User> user = userService.getUserById(userId);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @Operation(summary = "Add a new user", description = "Create a new user. Only accessible by admins.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "User created successfully"),
        @ApiResponse(responseCode = "400", description = "Validation error", content = @Content),
        @ApiResponse(responseCode = "403", description = "Access denied", content = @Content)
    })
    @PostMapping("/users")
    public ResponseEntity<?> addUser(
            @RequestHeader("Role") @Parameter(description = "Role of the requester", required = true) String role,
            @Valid @RequestBody @Parameter(description = "User object to be created", required = true) User user,
            BindingResult bindingResult) {
        if (!role.equals("Admin")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
        }
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Validation error: " + bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        userService.addUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully.");
    }

    @Operation(summary = "Update an existing user", description = "Update a user's details. Only accessible by admins.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User updated successfully"),
        @ApiResponse(responseCode = "400", description = "Validation error", content = @Content),
        @ApiResponse(responseCode = "403", description = "Access denied", content = @Content),
        @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    @PutMapping("/users/{userId}")
    public ResponseEntity<?> updateUser(
            @RequestHeader("Role") @Parameter(description = "Role of the requester", required = true) String role,
            @PathVariable @Parameter(description = "ID of the user to update", required = true) String userId,
            @Valid @RequestBody @Parameter(description = "Updated user object", required = true) User user,
            BindingResult bindingResult) {
        if (!role.equals("Admin")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
        }
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Validation error: " + bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        user.setUserId(userId);
        userService.updateUser(user);
        return ResponseEntity.ok("User updated successfully.");
    }

    @Operation(summary = "Delete a user", description = "Delete a user by their ID. Only accessible by admins.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User deleted successfully"),
        @ApiResponse(responseCode = "403", description = "Access denied", content = @Content),
        @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    @DeleteMapping("/users/{userId}")
    public ResponseEntity<?> deleteUser(
            @RequestHeader("Role") @Parameter(description = "Role of the requester", required = true) String role,
            @PathVariable @Parameter(description = "ID of the user to delete", required = true) String userId) {
        if (!role.equals("Admin")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
        }
        userService.deleteUser(userId);
        return ResponseEntity.ok("User deleted successfully.");
    }
}
