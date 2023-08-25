package andre.chamis.socialnetwork.controller;

import andre.chamis.socialnetwork.domain.auth.annotation.JwtAuthenticated;
import andre.chamis.socialnetwork.domain.auth.annotation.NonAuthenticated;
import andre.chamis.socialnetwork.domain.user.dto.CreateUserDTO;
import andre.chamis.socialnetwork.domain.user.dto.GetUserDTO;
import andre.chamis.socialnetwork.domain.user.dto.UpdateUserDTO;
import andre.chamis.socialnetwork.service.AuthorizationService;
import andre.chamis.socialnetwork.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@SecurityRequirement(name = "jwt-token")
@RestController
@JwtAuthenticated
@RequiredArgsConstructor
@RequestMapping("user/")
public class UserController {
    private final UserService userService;
    private final AuthorizationService authorizationService;

    @GetMapping("")
    public ResponseEntity<GetUserDTO> getCurrentUser(@RequestParam Optional<Long> userId){
        GetUserDTO getUserDTO = userService.getUserById(userId);
        return ResponseEntity.ok(getUserDTO);
    }

    @NonAuthenticated
    @PostMapping("register")
    public ResponseEntity<GetUserDTO> registerUser(@RequestBody CreateUserDTO createUserDTO) {
        GetUserDTO getUserDTO = userService.registerUser(createUserDTO);
        return new ResponseEntity<>(getUserDTO, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<GetUserDTO> updateUser(@RequestBody UpdateUserDTO updateUserDTO){
        GetUserDTO getUserDTO = userService.updateUser(updateUserDTO);
        return ResponseEntity.ok(getUserDTO);
    }

    // TODO: 24/08/2023 Rota para editar usuário
}
