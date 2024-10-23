package com.espeshop.users.controllers.v1;


import com.espeshop.users.model.dtos.CustomApiResponse;
import com.espeshop.users.model.entities.User;
import com.espeshop.users.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "User", description = "Operations related to user")
public class UserController {
    private final UserService userService;


    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(
            summary = "Create a new User",
            description = "This endpoint allows administrators to create a new User. Only users with 'ROLE_ADMIN' can access this."
    )
    public ResponseEntity<CustomApiResponse<User>> createUser(@RequestBody @Valid User User , HttpServletRequest request) {
        User createdUser = userService.createUser(User);
        CustomApiResponse<User> response = new CustomApiResponse<>(
                HttpStatus.CREATED.value(),
                true,
                "User created successfully",
                request.getRequestURI(),
                LocalDateTime.now(),
                createdUser
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(
            summary = "Get all Users",
            description = "Retrieve a list of all Users. You can optionally filter by name.",
            parameters = {
                    @Parameter(name = "page", required = true),
                    @Parameter(name = "size", required = true),
            }
    )
    public ResponseEntity<CustomApiResponse<Page<User>>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            HttpServletRequest request
    ) {
        final Pageable pageable = PageRequest.of(page, size);
        Page<User> Users = userService.getAllUsers(pageable);
        CustomApiResponse<Page<User>> response = new CustomApiResponse<>(
                HttpStatus.OK.value(),
                true,
                "Users retrieved successfully",
                request.getRequestURI(),
                LocalDateTime.now(),
                Users
        );
        return ResponseEntity.ok(response);
    }
}
