package andre.chamis.socialnetwork.service;

import andre.chamis.socialnetwork.domain.exception.ForbiddenException;
import andre.chamis.socialnetwork.domain.exception.FriendRequestNotFoundException;
import andre.chamis.socialnetwork.domain.exception.UserAlreadyFriendsException;
import andre.chamis.socialnetwork.domain.exception.UserNotFoundException;
import andre.chamis.socialnetwork.domain.friend.relation.model.FriendRelation;
import andre.chamis.socialnetwork.domain.friend.request.dto.AcceptFriendRequestDTO;
import andre.chamis.socialnetwork.domain.friend.request.dto.CancelFriendRequestDTO;
import andre.chamis.socialnetwork.domain.friend.request.dto.SendFriendRequestDTO;
import andre.chamis.socialnetwork.domain.friend.request.model.FriendRequest;
import andre.chamis.socialnetwork.domain.friend.request.repository.FriendRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FriendRequestService {
    private final SessionService sessionService;
    private final FriendRelationService friendRelationService;
    private final FriendRequestRepository friendRequestRepository;
    private final UserService userService;

    public void sendFriendRequest(SendFriendRequestDTO sendFriendRequestDTO){
        Long targetId = sendFriendRequestDTO.userId();
        Long currentUserId = sessionService.getCurrentUserId();

        boolean userExists = userService.userExistsById(targetId);
        if (!userExists) {
            throw new UserNotFoundException(HttpStatus.BAD_REQUEST);
        }

        boolean areUsersAlreadyFriends = friendRelationService.existsFriendRelationByUserIds(currentUserId, targetId);
        if (areUsersAlreadyFriends) {
            throw new UserAlreadyFriendsException("Os usuários ja são amigos!");
        }

        Optional<FriendRequest> friendRequestOptional = friendRequestRepository.findFriendRequestByUserIds(currentUserId, targetId);
        if (friendRequestOptional.isPresent()) {
            acceptFriendRequest(friendRequestOptional.get());
            return;
        }

        FriendRequest friendRequest = new FriendRequest();
        friendRequest.setSender(currentUserId);
        friendRequest.setReceiver(targetId);

        friendRequestRepository.createFriendRequest(friendRequest);
    }

    public void acceptFriendRequest(AcceptFriendRequestDTO acceptFriendRequestDTO){
        Long requestId = acceptFriendRequestDTO.requestId();
        Optional<FriendRequest> friendRequestOptional = friendRequestRepository.findFriendRequestByRequestId(requestId);
        FriendRequest friendRequest = friendRequestOptional.orElseThrow(() -> new FriendRequestNotFoundException(requestId));
        acceptFriendRequest(friendRequest);
    }

    private void acceptFriendRequest(FriendRequest friendRequest){
        Long currentUserId = sessionService.getCurrentUserId();

        if (currentUserId != friendRequest.getReceiver() && currentUserId != friendRequest.getSender()) {
            throw new ForbiddenException("Você não tem permissão para isso!");
        }

        FriendRelation friendRelation1 = new FriendRelation();
        friendRelation1.setUserId1(friendRequest.getReceiver());
        friendRelation1.setUserId2(friendRequest.getSender());

        FriendRelation friendRelation2 = new FriendRelation();
        friendRelation2.setUserId1(friendRequest.getSender());
        friendRelation2.setUserId2(friendRequest.getReceiver());

        friendRelationService.createFriendRelation(friendRelation1);
        friendRelationService.createFriendRelation(friendRelation2);

        deleteFriendRequest(friendRequest.getFriendRequestId());
    }

    private void deleteFriendRequest(Long friendRequestId){
        friendRequestRepository.deleteFriendRequest(friendRequestId);
    }

    public void cancelFriendRequest(CancelFriendRequestDTO cancelFriendRequestDTO) {
        deleteFriendRequest(cancelFriendRequestDTO.requestId());
    }
}
