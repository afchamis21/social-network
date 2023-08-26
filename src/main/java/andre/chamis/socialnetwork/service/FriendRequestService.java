package andre.chamis.socialnetwork.service;

import andre.chamis.socialnetwork.context.ServiceContext;
import andre.chamis.socialnetwork.domain.exception.EntityNotFoundException;
import andre.chamis.socialnetwork.domain.exception.ForbiddenException;
import andre.chamis.socialnetwork.domain.exception.UserAlreadyFriendsException;
import andre.chamis.socialnetwork.domain.friend.relation.model.FriendRelation;
import andre.chamis.socialnetwork.domain.friend.request.dto.AcceptFriendRequestDTO;
import andre.chamis.socialnetwork.domain.friend.request.dto.CancelFriendRequestDTO;
import andre.chamis.socialnetwork.domain.friend.request.dto.GetFriendRequestDTO;
import andre.chamis.socialnetwork.domain.friend.request.dto.SendFriendRequestDTO;
import andre.chamis.socialnetwork.domain.friend.request.model.FriendRequest;
import andre.chamis.socialnetwork.domain.friend.request.repository.FriendRequestRepository;
import andre.chamis.socialnetwork.domain.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FriendRequestService {
    private final SessionService sessionService;
    private final FriendRelationService friendRelationService;
    private final FriendRequestRepository friendRequestRepository;
    private final UserService userService;

    public GetFriendRequestDTO sendFriendRequest(SendFriendRequestDTO sendFriendRequestDTO){
        Long targetId = sendFriendRequestDTO.userId();

        User currentUser = userService.findCurrentUser();

        Optional<User> targetUserOptional = userService.findUserById(targetId);
        User targetUser = targetUserOptional.orElseThrow(() -> new EntityNotFoundException(HttpStatus.BAD_REQUEST));

        boolean areUsersAlreadyFriends = friendRelationService.existsFriendRelationByUserIds(currentUser.getUserId(), targetId);
        if (areUsersAlreadyFriends) {
            throw new UserAlreadyFriendsException("Os usuários ja são amigos!");
        }

        Optional<FriendRequest> friendRequestOptional = friendRequestRepository.findFriendRequestByUserIds(currentUser.getUserId(), targetId);
        if (friendRequestOptional.isPresent()) {
            acceptFriendRequest(friendRequestOptional.get());
            return new GetFriendRequestDTO()
                    .withFriendRequest(friendRequestOptional.get())
                    .withSender(userService.findCurrentUser())
                    .withReceiver(targetUser);
        }

        FriendRequest friendRequest = new FriendRequest();
        friendRequest.setSender(currentUser.getUserId());
        friendRequest.setReceiver(targetId);

        friendRequest = friendRequestRepository.createFriendRequest(friendRequest);

        return new GetFriendRequestDTO()
                .withFriendRequest(friendRequest)
                .withSender(userService.findCurrentUser())
                .withReceiver(targetUser);

    }

    public void acceptFriendRequest(AcceptFriendRequestDTO acceptFriendRequestDTO){
        Long requestId = acceptFriendRequestDTO.requestId();
        Optional<FriendRequest> friendRequestOptional = friendRequestRepository.findFriendRequestByRequestId(requestId);
        FriendRequest friendRequest = friendRequestOptional.orElseThrow(() -> new EntityNotFoundException("Solicitação de amizade não encontrada com o id: " + requestId, HttpStatus.BAD_REQUEST));
        acceptFriendRequest(friendRequest);
    }

    private void acceptFriendRequest(FriendRequest friendRequest){
        Long currentUserId = sessionService.getCurrentUserId();

        if (!currentUserId.equals(friendRequest.getReceiver()) && !currentUserId.equals(friendRequest.getSender())) {
            throw new ForbiddenException("Você não tem permissão para isso!");
        }

        Date now = Date.from(Instant.now());

        FriendRelation friendRelation1 = new FriendRelation();
        friendRelation1.setUserId1(friendRequest.getReceiver());
        friendRelation1.setUserId2(friendRequest.getSender());
        friendRelation1.setCreateDt(now);

        FriendRelation friendRelation2 = new FriendRelation();
        friendRelation2.setUserId1(friendRequest.getSender());
        friendRelation2.setUserId2(friendRequest.getReceiver());
        friendRelation2.setCreateDt(now);

        friendRelationService.createFriendRelation(friendRelation1);
        friendRelationService.createFriendRelation(friendRelation2);

        ServiceContext.addMessage("Solicitação aceita!");

        deleteFriendRequest(friendRequest.getFriendRequestId());
    }

    private void deleteFriendRequest(Long friendRequestId){
        friendRequestRepository.deleteFriendRequest(friendRequestId);
    }

    public void cancelFriendRequest(CancelFriendRequestDTO cancelFriendRequestDTO) {
        deleteFriendRequest(cancelFriendRequestDTO.requestId());

        ServiceContext.addMessage("Solicitação cancelada!");
    }

    public Page<GetFriendRequestDTO> findAllFriendRequestsToCurrentUser(Pageable pageable){
        User currentUser = userService.findCurrentUser();

        Page<FriendRequest> friendRequests = friendRequestRepository.findByReceiverId(currentUser.getUserId(), pageable);

        return friendRequests.map(friendRequest -> {
            Optional<User> senderOptional = userService.findUserById(friendRequest.getSender());
            User sender = senderOptional.orElseThrow(() -> new EntityNotFoundException(HttpStatus.INTERNAL_SERVER_ERROR));

            return new GetFriendRequestDTO()
                    .withFriendRequest(friendRequest)
                    .withSender(sender)
                    .withReceiver(currentUser);
        });
    }
}
