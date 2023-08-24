package andre.chamis.socialnetwork.controller;

import andre.chamis.socialnetwork.domain.auth.annotation.JwtAuthenticated;
import andre.chamis.socialnetwork.domain.friend.request.dto.AcceptFriendRequestDTO;
import andre.chamis.socialnetwork.domain.friend.request.dto.CancelFriendRequestDTO;
import andre.chamis.socialnetwork.domain.friend.request.dto.SendFriendRequestDTO;
import andre.chamis.socialnetwork.service.FriendRequestService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@SecurityRequirement(name = "jwt-token")
@JwtAuthenticated
@RestController
@RequestMapping("friend/request/")
@RequiredArgsConstructor
public class FriendRequestController {
    private final FriendRequestService friendRequestService;
    @PostMapping("send")
    public ResponseEntity<Void> sendFriendRequest(@RequestBody SendFriendRequestDTO sendFriendRequestDTO){
        friendRequestService.sendFriendRequest(sendFriendRequestDTO);
        return ResponseEntity.ok().build();
    }

    @PostMapping("accept")
    public ResponseEntity<Void> acceptFriendRequest(@RequestBody AcceptFriendRequestDTO acceptFriendRequestDTO){
        friendRequestService.acceptFriendRequest(acceptFriendRequestDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("cancel")
    public ResponseEntity<Void> cancelFriendRequest(@RequestBody CancelFriendRequestDTO cancelFriendRequestDTO){
        friendRequestService.cancelFriendRequest(cancelFriendRequestDTO);
        return ResponseEntity.ok().build();
    }
}
