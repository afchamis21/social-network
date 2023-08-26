package andre.chamis.socialnetwork.domain.comment.repository;

import andre.chamis.socialnetwork.domain.comment.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing comment-related operations.
 */
@Repository
public interface CommentJpaRepository extends JpaRepository<Comment, Long> {
    /**
     * Retrieves a page of comments associated with a specified post ID.
     *
     * @param postId   The ID of the post to which the comments belong.
     * @param pageable The pagination information.
     * @return A page containing the comments associated with the post.
     */
    Page<Comment> findAllByPostId(Long postId, Pageable pageable);
}
