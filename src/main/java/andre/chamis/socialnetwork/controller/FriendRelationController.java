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

/**
 * Controller class for friend relation-related operations.
 */
@SecurityRequirement(name = "jwt-token")
@JwtAuthenticated
@RestController
@RequestMapping("friend/")
@RequiredArgsConstructor
public class FriendRelationController {
    private final FriendRelationService friendRelationService;


    /**
     * Retrieves a page of friends for the current user.
     *
     * @param pageable Pageable object for pagination.
     * @return ResponseEntity containing the response message and a page of friends.
     */
    @GetMapping("all")
    public ResponseEntity<ResponseMessage<Page<GetUserDTO>>> acceptFriendRequest(Pageable pageable){
        Page<GetUserDTO> friends =  friendRelationService.listUserFriends(pageable);
        return ResponseMessageBuilder.build(friends, HttpStatus.OK);
    }

    /**
     * Retrieves a page of potential friends for the current user.
     *
     * @param pageable Pageable object for pagination.
     * @return ResponseEntity containing the response message and a page of potential friends.
     */
    @GetMapping("find")
    public ResponseEntity<ResponseMessage<Page<GetUserDTO>>> findPotentialFriends(Pageable pageable){
        Page<GetUserDTO> potentialFriends = friendRelationService.listPotentialFriends(pageable);
        return ResponseMessageBuilder.build(potentialFriends, HttpStatus.OK);
    }

    /**
     * Removes a friend relation between the current user and another user.
     *
     * @param removeFriendDTO DTO containing information about the friend to be removed.
     * @return ResponseEntity containing the response message.
     */
    @DeleteMapping("remove")
    public ResponseEntity<ResponseMessage<Void>> cancelFriendRequest(@RequestBody RemoveFriendDTO removeFriendDTO){
        friendRelationService.removeFriend(removeFriendDTO);
        return ResponseMessageBuilder.build(HttpStatus.OK);
    }
}
