package andre.chamis.socialnetwork.service;

import andre.chamis.socialnetwork.domain.exception.InvalidUserDataException;
import andre.chamis.socialnetwork.domain.exception.UserNotFoundException;
import andre.chamis.socialnetwork.domain.user.dto.CreateUserDTO;
import andre.chamis.socialnetwork.domain.user.dto.GetUserDTO;
import andre.chamis.socialnetwork.domain.user.dto.LoginDTO;
import andre.chamis.socialnetwork.domain.user.model.User;
import andre.chamis.socialnetwork.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final SessionService sessionService;

    public GetUserDTO register(CreateUserDTO createUserDTO) {
        if (!validateEmail(createUserDTO.email())){
            throw new InvalidUserDataException("Email inválido!", HttpStatus.BAD_REQUEST);
        }
        
        if (!validateUsername(createUserDTO.username())){
            throw new InvalidUserDataException(
                    "Usuário inválido! O usuário não pode conter caracteres especiais e deve ter pelo menos 4 caracteres",
                    HttpStatus.BAD_REQUEST
            );
        }
        
        if (!validatePassword(createUserDTO.password())){
            throw new InvalidUserDataException(
                    "Senha inválida! A senha não pode conter espaços e deve ter pelo menos 6 caracteres",
                    HttpStatus.BAD_REQUEST
            );
        }

        boolean isUserOnDatabase = userRepository.existsByUsername(createUserDTO.username());
        if (isUserOnDatabase) {
            throw new InvalidUserDataException(
                    "Nome de usuário já está sendo utilizado!",
                    HttpStatus.BAD_REQUEST
            );
        }

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = bCryptPasswordEncoder.encode(createUserDTO.password());

        User user = new User();
        user.setUsername(createUserDTO.username());
        user.setEmail(createUserDTO.email());
        user.setPassword(hashedPassword);
        user.setCreateDt(Date.from(Instant.now()));
        user.setUpdateDt(Date.from(Instant.now()));

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

    public Optional<User> validateUserCredential(LoginDTO loginDTO){
        Optional<User> userOptional = userRepository.findUserByUsername(loginDTO.username());

        if (userOptional.isEmpty()) {
            return Optional.empty();
        }
        User user = userOptional.get();

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        boolean isPasswordCorrect = bCryptPasswordEncoder.matches(loginDTO.password(), user.getPassword());
        if (!isPasswordCorrect) {
            return Optional.empty();
        }

        return Optional.of(user);
    }

    public User findCurrentUser(){
        Long currentUserId = sessionService.getCurrentUserId();
        Optional<User> userOptional = findUserById(currentUserId);
        return userOptional.orElseThrow(() -> new UserNotFoundException(HttpStatus.FORBIDDEN));
    }

    public GetUserDTO getCurrentUser(){
        return GetUserDTO.fromUser(findCurrentUser());
    }

    public Optional<User> findUserById(Long userId) {
        return userRepository.findById(userId);
    }

    public boolean userExistsById(Long userId) {
        return userRepository.existsById(userId);
    }
}
