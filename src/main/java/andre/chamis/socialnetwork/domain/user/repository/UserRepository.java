package andre.chamis.socialnetwork.domain.user.repository;

import andre.chamis.socialnetwork.domain.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepository {
    private final UserJpaRepository userJpaRepository;

    public Optional<User> findById(Long userId){
        return userJpaRepository.findById(userId);
    }

    public User save(User user) {
        return userJpaRepository.save(user);
    }
}
