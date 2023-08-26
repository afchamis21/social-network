package andre.chamis.socialnetwork.controller;

import andre.chamis.socialnetwork.domain.auth.annotation.JwtAuthenticated;
import andre.chamis.socialnetwork.domain.friend.relation.dto.RemoveFriendDTO;
import andre.chamis.socialnetwork.domain.response.ResponseMessage;
import andre.chamis.socialnetwork.domain.response.ResponseMessageBuilder;
import andre.chamis.socialnetwork.domain.user.dto.GetUserDTO;
import andre.chamis.socialnetwork.service.FriendRelationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@SecurityRequirement(name = "jwt-token")
@JwtAuthenticated
@RestController
@RequestMapping("friend/")
@RequiredArgsConstructor
public class FriendRelationController {
    private final FriendRelationService friendRelationService;

    @GetMapping("all")
    public ResponseEntity<ResponseMessage<Page<GetUserDTO>>> acceptFriendRequest(Pageable pageable){
        Page<GetUserDTO> friends =  friendRelationService.listUserFriends(pageable);
        return ResponseMessageBuilder.build(friends, HttpStatus.OK);
    }

    @DeleteMapping("remove")
    public ResponseEntity<ResponseMessage<Void>> cancelFriendRequest(@RequestBody RemoveFriendDTO removeFriendDTO){
        friendRelationService.removeFriend(removeFriendDTO);
        return ResponseMessageBuilder.build(HttpStatus.OK);
    }
}
