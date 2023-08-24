package andre.chamis.socialnetwork.service;

import andre.chamis.socialnetwork.domain.exceptions.InvalidUserDataException;
import andre.chamis.socialnetwork.domain.user.dto.CreateUserDTO;
import andre.chamis.socialnetwork.domain.user.dto.GetUserDTO;
import andre.chamis.socialnetwork.domain.user.dto.LoginDTO;
import andre.chamis.socialnetwork.domain.user.model.User;
import andre.chamis.socialnetwork.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public GetUserDTO register(CreateUserDTO createUserDTO) {
        if (!validateEmail(createUserDTO.email())){
            throw new InvalidUserDataException("Invalid Email!", HttpStatus.BAD_REQUEST);
        }
        
        if (!validateUsername(createUserDTO.username())){
            throw new InvalidUserDataException(
                    "Invalid Username! The username can not contain special characters and must be larger than 3 letters",
                    HttpStatus.BAD_REQUEST
            );
        }
        
        if (!validatePassword(createUserDTO.password())){
            throw new InvalidUserDataException(
                    "Invalid Password! The password can not contain spaces and must be larger than 6 letters",
                    HttpStatus.BAD_REQUEST
            );
        }

        boolean isUserOnDatabase = userRepository.existsByUsername(createUserDTO.username());
        if (isUserOnDatabase) {
            throw new InvalidUserDataException(
                    "Username already taken!",
                    HttpStatus.BAD_REQUEST
            );
        }

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = bCryptPasswordEncoder.encode(createUserDTO.password());

        User user = new User();
        user.setUsername(createUserDTO.username());
        user.setEmail(createUserDTO.email());
        user.setPassword(hashedPassword);
        user.setCreateDt(LocalDateTime.now());
        user.setUpdateDt(LocalDateTime.now());

        user = userRepository.save(user);

        return GetUserDTO.fromUser(user);
    }

    private boolean validatePassword(String password) {
        if (password == null) {
            return false;
        }

        String passwordRegex = "^\\S+$";
        Pattern pattern = Pattern.compile(passwordRegex);
        Matcher matcher = pattern.matcher(password);
        return password.length() > 6 && matcher.matches();
    }

    private boolean validateUsername(String username) {
        if (username == null) {
            return false;
        }

        if (username.length() <= 3) {
            return false;
        }

        String usernameRegex = "^[A-Za-z0-9_-]+$";
        Pattern pattern = Pattern.compile(usernameRegex);
        Matcher matcher = pattern.matcher(username);

        if (!matcher.matches()) {
            return false;
        }

        return true;
    }

    private boolean validateEmail(String email){
        if (email == null) {
            return false;
        }

        String emailRegex = "^[A-Za-z0-9+_.-]+@([A-Za-z0-9-]+\\.)+[A-Za-z]{2,4}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean validateUserCredential(LoginDTO loginDTO){
        Optional<User> userOptional = userRepository.findUserByUsername(loginDTO.username());

        if (userOptional.isEmpty()) {
            return false;
        }
        User user = userOptional.get();

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder.matches(loginDTO.password(), user.getPassword());
    }

//    public GetUserDTO getCurrentUser(){
//        Long currentUserId = sessionService.getCurrentUserId();
//        Optional<User> userOptional = userRepository.findById(currentUserId);
//        User user = userOptional.orElseThrow(() -> new UserNotFoundException());
//
//        return GetUserDTO.fromUser(user);
//    }
}
