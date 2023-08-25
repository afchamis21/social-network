package andre.chamis.socialnetwork.controller;

import andre.chamis.socialnetwork.domain.auth.annotation.JwtAuthenticated;
import andre.chamis.socialnetwork.domain.post.dto.CreatePostDTO;
import andre.chamis.socialnetwork.domain.post.dto.DeletePostDTO;
import andre.chamis.socialnetwork.domain.post.dto.EditPostDTO;
import andre.chamis.socialnetwork.domain.post.dto.GetPostDTO;
import andre.chamis.socialnetwork.service.PostService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@JwtAuthenticated
@RequiredArgsConstructor
@RequestMapping("post/")
@SecurityRequirement(name = "jwt-token")
public class PostsController {
    private final PostService postService;

    @PostMapping
    public ResponseEntity<GetPostDTO> createPost(@RequestBody CreatePostDTO createPostDTO){
        GetPostDTO getPostDTO = postService.createPost(createPostDTO);
        return ResponseEntity.ok(getPostDTO);
    }

    @GetMapping
    public ResponseEntity<Page<GetPostDTO>> findPosts(@RequestParam Optional<Long> ownerId, Pageable pageable){
        Page<GetPostDTO> posts = postService.getPostsByUserId(ownerId, pageable);
        return ResponseEntity.ok(posts);
    }

    @DeleteMapping
    public ResponseEntity<Void> deletePost(@RequestBody DeletePostDTO deletePostDTO){
        postService.deletePost(deletePostDTO);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<GetPostDTO> editPost(@RequestBody EditPostDTO editPostDTO){
        GetPostDTO getPostDTO = postService.editPost(editPostDTO);
        return ResponseEntity.ok(getPostDTO);
    }
}
