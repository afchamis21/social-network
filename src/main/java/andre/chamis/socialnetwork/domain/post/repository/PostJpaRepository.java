package andre.chamis.socialnetwork.domain.post.repository;

import andre.chamis.socialnetwork.domain.post.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing post entities using JPA.
 */
@Repository
public interface PostJpaRepository extends JpaRepository<Post, Long> {
    /**
     * Retrieves a page of posts owned by a specified user ID.
     *
     * @param ownerId  The ID of the owner of the posts.
     * @param pageable The pagination information.
     * @return A page containing the posts owned by the user.
     */
    Page<Post> findByOwnerId(Long ownerId, Pageable pageable);
}
