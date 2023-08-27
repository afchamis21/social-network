package andre.chamis.socialnetwork.service;

import andre.chamis.socialnetwork.domain.exception.EntityNotFoundException;
import andre.chamis.socialnetwork.domain.exception.ForbiddenException;
import andre.chamis.socialnetwork.domain.post.dto.CreatePostDTO;
import andre.chamis.socialnetwork.domain.post.dto.DeletePostDTO;
import andre.chamis.socialnetwork.domain.post.dto.EditPostDTO;
import andre.chamis.socialnetwork.domain.post.dto.GetPostDTO;
import andre.chamis.socialnetwork.domain.post.model.Post;
import andre.chamis.socialnetwork.domain.post.repository.PostRepository;
import andre.chamis.socialnetwork.domain.user.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;

/**
 * Service class responsible for managing posts.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {
    private final SessionService sessionService;
    private final UserService userService;
    private final PostRepository postRepository;

    /**
     * Creates a new post.
     *
     * @param createPostDTO The DTO containing post creation data.
     * @return The created post DTO.
     */
    public GetPostDTO createPost(CreatePostDTO createPostDTO){
        Long currentUserId = sessionService.getCurrentUserId();

        Date now = Date.from(Instant.now());

        Post post = new Post();
        post.setOwnerId(currentUserId);
        post.setContent(createPostDTO.content());
        post.setCreateDt(now);
        post.setUpdateDt(now);

        post = postRepository.save(post);

        User user = userService.findCurrentUser();

        return new GetPostDTO()
                .withPost(post)
                .withUser(user);
    }

    /**
     * Retrieves posts by user ID.
     *
     * @param ownerIdOptional An optional owner ID.
     * @param pageable The paging and sorting information.
     * @return A page of post DTOs.
     */
    public Page<GetPostDTO> getPostsByUserId(Optional<Long> ownerIdOptional, Pageable pageable){
        Long ownerId = ownerIdOptional.orElse(sessionService.getCurrentUserId());
        Optional<User> userOptional = userService.findUserById(ownerId);
        User user = userOptional.orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com o id: " + ownerId, HttpStatus.BAD_REQUEST));

        Page<Post> posts = postRepository.findPostsByUserId(ownerId, pageable);

        return posts.map(post -> new GetPostDTO().withPost(post).withUser(user));
    }

    /**
     * Deletes a post.
     *
     * @param deletePostDTO The DTO containing post deletion data.
     */
    public void deletePost(DeletePostDTO deletePostDTO){
        Long currentUserId = sessionService.getCurrentUserId();

        postRepository.delete(deletePostDTO.postId(), currentUserId);
    }

    /**
     * Edits a post.
     *
     * @param editPostDTO The DTO containing post edit data.
     * @return The edited post DTO.
     */
    public GetPostDTO editPost(EditPostDTO editPostDTO){
        Optional<Post> postOptional = postRepository.findPostById(editPostDTO.postId());
        Post post = postOptional.orElseThrow(() -> new EntityNotFoundException("Post não encontrado com o id: " + editPostDTO.postId(), HttpStatus.BAD_REQUEST));

        Long currentUserId = sessionService.getCurrentUserId();
        if (!post.getOwnerId().equals(currentUserId)){
            throw new ForbiddenException("Você não tem permissão para editar esse post!");
        }

        if (editPostDTO.content() != null){
            post.setContent(editPostDTO.content());
            post.setUpdateDt(Date.from(Instant.now()));
            post = postRepository.save(post);
        }

        User user = userService.findCurrentUser();

        return new GetPostDTO()
                .withPost(post)
                .withUser(user);
    }

    /**
     * Deletes all posts.
     */
    public void deleteAllPosts() {
        log.warn("Deleting all posts");
        postRepository.deleteAllPosts();
    }

    /**
     * Checks if a post exists by its ID.
     *
     * @param postId The ID of the post to check.
     * @return True if the post exists, otherwise false.
     */
    public boolean checkExistsPostById(Long postId){
        return postRepository.existsById(postId);
    }

    /**
     * Finds a post by its ID.
     *
     * @param postId The ID of the post to find.
     * @return An optional containing the post if found.
     */
    public Optional<Post> findPostById(Long postId){
        return postRepository.findPostById(postId);
    }
}
