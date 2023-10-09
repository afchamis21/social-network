package andre.chamis.socialnetwork.domain.post.repository;

import andre.chamis.socialnetwork.domain.post.model.Post;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository class for managing post-related operations.
 */
@Repository
@RequiredArgsConstructor
public class PostRepository {
    private final PostJpaRepository postJpaRepository;
    private final PostDAO postDAO;

    /**
     * Saves a post.
     *
     * @param post The post to be saved.
     * @return The saved post.
     */
    public Post save(Post post) {
        return postJpaRepository.save(post);
    }

    /**
     * Retrieves a page of posts owned by a specified user ID.
     *
     * @param ownerId  The ID of the owner of the posts.
     * @param pageable The pagination information.
     * @return A page containing the posts owned by the user.
     */
    public Page<Post> findPostsByUserId(Long ownerId, Pageable pageable) {
        return postJpaRepository.findByOwnerId(ownerId, pageable);
    }

    /**
     * Deletes a post by its ID and owner ID, along with its associated comments.
     *
     * @param postId  The ID of the post to be deleted.
     * @param ownerId The ID of the owner of the post.
     */
    public void delete(Long postId, Long ownerId) {
        postDAO.deletePostById(postId, ownerId);
    }

    /**
     * Retrieves a post by its ID.
     *
     * @param postId The ID of the post.
     * @return An optional containing the retrieved post.
     */
    public Optional<Post> findPostById(Long postId) {
        return postJpaRepository.findById(postId);
    }

    /**
     * Deletes all posts.
     */
    @Transactional
    public void deleteAllPosts() {
        postJpaRepository.deleteAll();
    }

    /**
     * Checks if a post exists with the specified post ID.
     *
     * @param postId The ID of the post.
     * @return True if a post exists with the specified ID, false otherwise.
     */
    public boolean existsById(Long postId) {
        return postJpaRepository.existsById(postId);
    }

    /**
     * Retrieves a page of posts.
     *
     * @param pageable The pagination information.
     * @return A page containing the posts owned by the user.
     */
    public Page<Post> findPosts(Pageable pageable) {
        return postJpaRepository.findAll(pageable);
    }
}
