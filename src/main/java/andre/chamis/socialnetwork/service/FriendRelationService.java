package andre.chamis.socialnetwork.service;

import andre.chamis.socialnetwork.domain.exception.EntityNotFoundException;
import andre.chamis.socialnetwork.domain.friend.relation.dto.RemoveFriendDTO;
import andre.chamis.socialnetwork.domain.friend.relation.model.FriendRelation;
import andre.chamis.socialnetwork.domain.friend.relation.repository.FriendRelationRepository;
import andre.chamis.socialnetwork.domain.user.dto.GetUserDTO;
import andre.chamis.socialnetwork.domain.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FriendRelationService {
    private final SessionService sessionService;
    private final FriendRelationRepository friendRelationRepository;
    private final UserService userService;

    public void removeFriend(RemoveFriendDTO removeFriendDTO){
        Long currentUserId = sessionService.getCurrentUserId();
        friendRelationRepository.deleteAllFriendRelationsByUserIds(currentUserId, removeFriendDTO.userId());
    }

    public Page<GetUserDTO> listUserFriends(Pageable pageable){
        Long currentUserId = sessionService.getCurrentUserId();
        Page<FriendRelation> friendRelations = friendRelationRepository.findUserFriends(currentUserId, pageable);
        Page<User> friends = friendRelations.map(
                friendRelation ->
                    userService.findUserById(friendRelation.getUserId2()).orElseThrow(() -> new EntityNotFoundException(HttpStatus.INTERNAL_SERVER_ERROR))

        );
        return friends.map(GetUserDTO::fromUser);
    }

    public boolean existsFriendRelationByUserIds(Long userId1, Long userId2) {
        return friendRelationRepository.existsFriendRelationByUserIds(userId1, userId2);
    }

    public void createFriendRelation(FriendRelation friendRelation) {
        friendRelationRepository.save(friendRelation);
    }
}
