package andre.chamis.socialnetwork.controller;

import andre.chamis.socialnetwork.domain.auth.annotation.JwtAuthenticated;
import andre.chamis.socialnetwork.domain.auth.annotation.NonAuthenticated;
import andre.chamis.socialnetwork.domain.user.dto.CreateUserDTO;
import andre.chamis.socialnetwork.domain.user.dto.GetUserDTO;
import andre.chamis.socialnetwork.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@SecurityRequirement(name = "jwt-token")
@RestController
@JwtAuthenticated
@RequiredArgsConstructor
@RequestMapping("user/")
public class UserController {
    private final UserService userService;

    @GetMapping("")
    public ResponseEntity<GetUserDTO> getCurrentUser(){
        GetUserDTO getUserDTO = userService.getCurrentUser();
        return ResponseEntity.ok(getUserDTO);
    }

    @NonAuthenticated
    @PostMapping("register")
    public ResponseEntity<GetUserDTO> register(@RequestBody CreateUserDTO createUserDTO) {
        GetUserDTO getUserDTO = userService.register(createUserDTO);
        return new ResponseEntity<>(getUserDTO, HttpStatus.CREATED);
    }

    // TODO: 24/08/2023 Rota para editar usuário, listar todos os posts do usuário (passando o id pela query string mas requer jwt auth, se não passar defaultar pro logado)
}
