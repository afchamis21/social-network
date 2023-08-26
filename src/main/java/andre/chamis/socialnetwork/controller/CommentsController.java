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


@RestController
@JwtAuthenticated
@RequiredArgsConstructor
@RequestMapping("comment/")
@SecurityRequirement(name = "jwt-token")
public class CommentsController {
    private final CommentService commentService;
    @PostMapping
    public ResponseEntity<ResponseMessage<GetCommentDTO>> createComment(@RequestBody CreateCommentDTO createCommentDTO){
        GetCommentDTO getCommentDTO = commentService.createComment(createCommentDTO);
        return ResponseMessageBuilder.build(getCommentDTO, HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<ResponseMessage<Void>> deleteComment(@RequestBody DeleteCommentDTO deleteCommentDTO){
        commentService.deleteComment(deleteCommentDTO);
        return ResponseMessageBuilder.build(HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<ResponseMessage<GetCommentDTO>> updateComment(@RequestBody EditCommentDTO editCommentDTO){
        GetCommentDTO getCommentDTO = commentService.editComment(editCommentDTO);
        return ResponseMessageBuilder.build(getCommentDTO, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ResponseMessage<Page<GetCommentDTO>>> findAllByPostId(@RequestParam Optional<Long> postId, Pageable pageable){
        Page<GetCommentDTO> comments = commentService.findAllCommentsByPostId(postId, pageable);
        return ResponseMessageBuilder.build(comments, HttpStatus.OK);
    }
}
