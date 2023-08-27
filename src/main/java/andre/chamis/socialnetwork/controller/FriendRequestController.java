package andre.chamis.socialnetwork.controller;

import andre.chamis.socialnetwork.domain.auth.annotation.JwtAuthenticated;
import andre.chamis.socialnetwork.domain.friend.request.dto.AcceptFriendRequestDTO;
import andre.chamis.socialnetwork.domain.friend.request.dto.CancelFriendRequestDTO;
import andre.chamis.socialnetwork.domain.friend.request.dto.GetFriendRequestDTO;
import andre.chamis.socialnetwork.domain.friend.request.dto.SendFriendRequestDTO;
import andre.chamis.socialnetwork.domain.response.ResponseMessage;
import andre.chamis.socialnetwork.domain.response.ResponseMessageBuilder;
import andre.chamis.socialnetwork.service.FriendRequestService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller class for friend request-related operations.
 */
@SecurityRequirement(name = "jwt-token")
@JwtAuthenticated
@RestController
@RequestMapping("friend/request/")
@RequiredArgsConstructor
public class FriendRequestController {
    private final FriendRequestService friendRequestService;


    /**
     * Sends a friend request.
     *
     * @param sendFriendRequestDTO DTO containing friend request information.
     * @return ResponseEntity containing the response message and sent friend request information.
     */
    @PostMapping("send")
    public ResponseEntity<ResponseMessage<GetFriendRequestDTO>> sendFriendRequest(@RequestBody SendFriendRequestDTO sendFriendRequestDTO){
        GetFriendRequestDTO getFriendRequestDTO = friendRequestService.sendFriendRequest(sendFriendRequestDTO);
        return ResponseMessageBuilder.build(getFriendRequestDTO, HttpStatus.CREATED);
    }

    /**
     * Accepts a friend request.
     *
     * @param acceptFriendRequestDTO DTO containing friend request acceptance information.
     * @return ResponseEntity containing the response message.
     */
    @PostMapping("accept")
    public ResponseEntity<ResponseMessage<Void>> acceptFriendRequest(@RequestBody AcceptFriendRequestDTO acceptFriendRequestDTO){
        friendRequestService.acceptFriendRequest(acceptFriendRequestDTO);
        return ResponseMessageBuilder.build(HttpStatus.CREATED);
    }

    /**
     * Cancels a friend request.
     *
     * @param cancelFriendRequestDTO DTO containing friend request cancellation information.
     * @return ResponseEntity containing the response message.
     */
    @DeleteMapping("cancel")
    public ResponseEntity<ResponseMessage<Void>> cancelFriendRequest(@RequestBody CancelFriendRequestDTO cancelFriendRequestDTO){
        friendRequestService.cancelFriendRequest(cancelFriendRequestDTO);
        return ResponseMessageBuilder.build(HttpStatus.OK);
    }

    /**
     * Retrieves all friend requests to the current user.
     *
     * @param pageable Pageable object for pagination.
     * @return ResponseEntity containing the response message and a page of friend requests.
     */
    @GetMapping("all")
    public ResponseEntity<ResponseMessage<Page<GetFriendRequestDTO>>> getAll(Pageable pageable){
        Page<GetFriendRequestDTO> getFriendRequestDTO = friendRequestService.findAllFriendRequestsToCurrentUser(pageable);
        return ResponseMessageBuilder.build(getFriendRequestDTO, HttpStatus.OK);
    }
}
