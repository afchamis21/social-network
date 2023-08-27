package andre.chamis.socialnetwork.controller;

import andre.chamis.socialnetwork.domain.auth.annotation.JwtAuthenticated;
import andre.chamis.socialnetwork.domain.auth.annotation.NonAuthenticated;
import andre.chamis.socialnetwork.domain.response.ResponseMessage;
import andre.chamis.socialnetwork.domain.response.ResponseMessageBuilder;
import andre.chamis.socialnetwork.domain.user.dto.CreateUserDTO;
import andre.chamis.socialnetwork.domain.user.dto.GetUserDTO;
import andre.chamis.socialnetwork.domain.user.dto.UpdateUserDTO;
import andre.chamis.socialnetwork.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Controller class for user-related operations.
 */
@SecurityRequirement(name = "jwt-token")
@RestController
@JwtAuthenticated
@RequiredArgsConstructor
@RequestMapping("user/")
public class UserController {
    private final UserService userService;

    /**
     * Retrieves information about the current user or a specified user.
     *
     * @param userId Optional parameter to specify the user ID.
     * @return ResponseEntity containing the response message and user information.
     */
    @GetMapping
    public ResponseEntity<ResponseMessage<GetUserDTO>> getCurrentUser(@RequestParam Optional<Long> userId){
        GetUserDTO getUserDTO = userService.getUserById(userId);
        return ResponseMessageBuilder.build(getUserDTO, HttpStatus.OK);
    }

    /**
     * Registers a new user.
     *
     * @param createUserDTO DTO containing user registration information.
     * @return ResponseEntity containing the response message and registered user information.
     */
    @NonAuthenticated
    @PostMapping("register")
    public ResponseEntity<ResponseMessage<GetUserDTO>> registerUser(@RequestBody CreateUserDTO createUserDTO) {
        GetUserDTO getUserDTO = userService.registerUser(createUserDTO);
        return ResponseMessageBuilder.build(getUserDTO, HttpStatus.CREATED);
    }

    /**
     * Updates user information.
     *
     * @param updateUserDTO DTO containing updated user information.
     * @return ResponseEntity containing the response message and updated user information.
     */
    @PutMapping
    public ResponseEntity<ResponseMessage<GetUserDTO>> updateUser(@RequestBody UpdateUserDTO updateUserDTO){
        GetUserDTO getUserDTO = userService.updateUser(updateUserDTO);
        return ResponseMessageBuilder.build(getUserDTO, HttpStatus.OK);
    }
}
