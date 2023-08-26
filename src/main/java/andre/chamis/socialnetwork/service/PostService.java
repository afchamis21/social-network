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

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {
    private final SessionService sessionService;
    private final UserService userService;
    private final PostRepository postRepository;

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

    public Page<GetPostDTO> getPostsByUserId(Optional<Long> ownerIdOptional, Pageable pageable){
        Long ownerId = ownerIdOptional.orElse(sessionService.getCurrentUserId());
        Optional<User> userOptional = userService.findUserById(ownerId);
        User user = userOptional.orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com o id: " + ownerId, HttpStatus.BAD_REQUEST));

        Page<Post> posts = postRepository.findPostsByUserId(ownerId, pageable);

        return posts.map(post -> new GetPostDTO().withPost(post).withUser(user));
    }

    public void deletePost(DeletePostDTO deletePostDTO){
        Long currentUserId = sessionService.getCurrentUserId();

        postRepository.delete(deletePostDTO.postId(), currentUserId);
    }

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

    public void deleteAllPosts() {
        log.warn("Deleting all posts");
        postRepository.deleteAllPosts();
    }

    public boolean checkExistsPostById(Long postId){
        return postRepository.existsById(postId);
    }

    public Optional<Post> findPostById(Long postId){
        return postRepository.findPostById(postId);
    }
}
