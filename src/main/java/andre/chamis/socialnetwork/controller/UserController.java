package andre.chamis.socialnetwork.controller;

import andre.chamis.socialnetwork.domain.auth.annotation.JwtAuthenticated;
import andre.chamis.socialnetwork.domain.auth.annotation.NonAuthenticated;
import andre.chamis.socialnetwork.domain.user.dto.CreateUserDTO;
import andre.chamis.socialnetwork.domain.user.dto.GetUserDTO;
import andre.chamis.socialnetwork.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@JwtAuthenticated
@RequiredArgsConstructor
@RequestMapping("user/")
public class UserController {
    private final UserService userService;

//    @GetMapping("")
//    public ResponseEntity<GetUserDTO> getCurrentUser(){
//        GetUserDTO body = userService.getCurrentUser();
//        return ResponseEntity.ok(body);
//    }

    @NonAuthenticated
    @PostMapping("register")
    public ResponseEntity<GetUserDTO> register(@RequestBody CreateUserDTO createUserDTO) {
        GetUserDTO getUserDTO = userService.register(createUserDTO);
        return new ResponseEntity<>(getUserDTO, HttpStatus.CREATED);
    }

    @GetMapping("test")
    public ResponseEntity<Integer> test(){
        return ResponseEntity.ok(1);
    }
}
