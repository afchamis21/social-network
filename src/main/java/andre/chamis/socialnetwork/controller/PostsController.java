package andre.chamis.socialnetwork.controller;

import andre.chamis.socialnetwork.domain.auth.annotation.JwtAuthenticated;
import andre.chamis.socialnetwork.domain.post.dto.CreatePostDTO;
import andre.chamis.socialnetwork.domain.post.dto.DeletePostDTO;
import andre.chamis.socialnetwork.domain.post.dto.EditPostDTO;
import andre.chamis.socialnetwork.domain.post.dto.GetPostDTO;
import andre.chamis.socialnetwork.domain.response.ResponseMessage;
import andre.chamis.socialnetwork.domain.response.ResponseMessageBuilder;
import andre.chamis.socialnetwork.service.PostService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Controller class for post-related operations.
 */
@RestController
@JwtAuthenticated
@RequiredArgsConstructor
@RequestMapping("post/")
@SecurityRequirement(name = "jwt-token")
public class PostsController {
    private final PostService postService;

    /**
     * Creates a new post.
     *
     * @param createPostDTO DTO containing post creation information.
     * @return ResponseEntity containing the response message and created post information.
     */
    @PostMapping
    public ResponseEntity<ResponseMessage<GetPostDTO>> createPost(@RequestBody CreatePostDTO createPostDTO){
        GetPostDTO getPostDTO = postService.createPost(createPostDTO);
        return ResponseMessageBuilder.build(getPostDTO, HttpStatus.CREATED);
    }

    /**
     * Retrieves posts based on the owner's ID.
     *
     * @param ownerId Optional parameter to specify the owner's ID.
     * @param pageable Pageable object for pagination.
     * @return ResponseEntity containing the response message and a page of posts.
     */
    @GetMapping
    public ResponseEntity<ResponseMessage<Page<GetPostDTO>>> findPosts(@RequestParam Optional<Long> ownerId, Pageable pageable){
        Page<GetPostDTO> posts = postService.getPosts(ownerId, pageable);
        return ResponseMessageBuilder.build(posts, HttpStatus.OK);
    }

    /**
     * Deletes a post.
     *
     * @param deletePostDTO DTO containing post deletion information.
     * @return ResponseEntity containing the response message.
     */
    @DeleteMapping
    public ResponseEntity<ResponseMessage<Void>> deletePost(@RequestBody DeletePostDTO deletePostDTO){
        postService.deletePost(deletePostDTO);
        return ResponseMessageBuilder.build(HttpStatus.OK);
    }

    /**
     * Edits a post.
     *
     * @param editPostDTO DTO containing post editing information.
     * @return ResponseEntity containing the response message and edited post information.
     */
    @PutMapping
    public ResponseEntity<ResponseMessage<GetPostDTO>> editPost(@RequestBody EditPostDTO editPostDTO){
        GetPostDTO getPostDTO = postService.editPost(editPostDTO);
        return ResponseMessageBuilder.build(getPostDTO, HttpStatus.OK);
    }
}
