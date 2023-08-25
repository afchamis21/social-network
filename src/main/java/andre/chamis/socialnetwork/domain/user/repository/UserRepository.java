package andre.chamis.socialnetwork.domain.user.repository;

import andre.chamis.socialnetwork.domain.user.model.User;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepository {
    private final UserJpaRepository userJpaRepository;
    private final UserInMemoryCache userInMemoryCache;

    @PostConstruct
    public int initializeCache(){
        List<User> users = userJpaRepository.findAll();
        userInMemoryCache.initializeCache(users, User::getUserId);
        return userInMemoryCache.getSize();
    }

    public Optional<User> findById(Long userId){
        Optional<User> userOptionalFromCache = userInMemoryCache.get(userId);
        if (userOptionalFromCache.isPresent()){
            return userOptionalFromCache;
        }

        Optional<User> userOptionalFromDatabase = userJpaRepository.findById(userId);
        if (userOptionalFromDatabase.isPresent()){
            User user = userOptionalFromDatabase.get();
            userInMemoryCache.put(user.getUserId(), user);
        }

        return userJpaRepository.findById(userId);
    }

    public User save(User user) {
        user = userJpaRepository.save(user);

        userInMemoryCache.put(user.getUserId(), user);

        return user;
    }

    public boolean existsByUsername(String username) {
        return userJpaRepository.existsByUsername(username);
    }

    public Optional<User> findUserByUsername(String username){
        return userJpaRepository.findUserByUsername(username);
    }

    public boolean existsById(Long userId) {
        return userInMemoryCache.containsKey(userId);
    }
}
