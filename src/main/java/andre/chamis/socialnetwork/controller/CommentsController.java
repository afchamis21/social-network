package andre.chamis.socialnetwork.controller;

import andre.chamis.socialnetwork.domain.auth.annotation.JwtAuthenticated;
import andre.chamis.socialnetwork.domain.comment.dto.CreateCommentDTO;
import andre.chamis.socialnetwork.domain.comment.dto.DeleteCommentDTO;
import andre.chamis.socialnetwork.domain.comment.dto.EditCommentDTO;
import andre.chamis.socialnetwork.domain.comment.dto.GetCommentDTO;
import andre.chamis.socialnetwork.domain.response.ResponseMessage;
import andre.chamis.socialnetwork.domain.response.ResponseMessageBuilder;
import andre.chamis.socialnetwork.service.CommentService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Controller class for managing comments on posts.
 */
@RestController
@JwtAuthenticated
@RequiredArgsConstructor
@RequestMapping("comment/")
@SecurityRequirement(name = "jwt-token")
public class CommentsController {
    private final CommentService commentService;

    /**
     * Create a new comment.
     *
     * @param createCommentDTO The DTO containing the comment content and related details.
     * @return ResponseEntity containing the newly created comment and a status code.
     */
    @PostMapping
    public ResponseEntity<ResponseMessage<GetCommentDTO>> createComment(@RequestBody CreateCommentDTO createCommentDTO){
        GetCommentDTO getCommentDTO = commentService.createComment(createCommentDTO);
        return ResponseMessageBuilder.build(getCommentDTO, HttpStatus.CREATED);
    }

    /**
     * Delete a comment.
     *
     * @param deleteCommentDTO The DTO containing the ID of the comment to be deleted.
     * @return ResponseEntity with a success message and status code.
     */
    @DeleteMapping
    public ResponseEntity<ResponseMessage<Void>> deleteComment(@RequestBody DeleteCommentDTO deleteCommentDTO){
        commentService.deleteComment(deleteCommentDTO);
        return ResponseMessageBuilder.build(HttpStatus.OK);
    }

    /**
     * Update a comment's content.
     *
     * @param editCommentDTO The DTO containing the comment ID and updated content.
     * @return ResponseEntity containing the updated comment and a status code.
     */
    @PutMapping
    public ResponseEntity<ResponseMessage<GetCommentDTO>> updateComment(@RequestBody EditCommentDTO editCommentDTO){
        GetCommentDTO getCommentDTO = commentService.editComment(editCommentDTO);
        return ResponseMessageBuilder.build(getCommentDTO, HttpStatus.OK);
    }

    /**
     * Retrieve all comments associated with a specific post.
     *
     * @param postId The ID of the post for which to retrieve comments.
     * @param pageable Pageable object for pagination.
     * @return ResponseEntity containing a page of comments and a status code.
     */
    @GetMapping
    public ResponseEntity<ResponseMessage<Page<GetCommentDTO>>> findAllByPostId(@RequestParam Optional<Long> postId, Pageable pageable){
        Page<GetCommentDTO> comments = commentService.findAllCommentsByPostId(postId, pageable);
        return ResponseMessageBuilder.build(comments, HttpStatus.OK);
    }
}
