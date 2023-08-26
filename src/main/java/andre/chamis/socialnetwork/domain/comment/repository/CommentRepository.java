package andre.chamis.socialnetwork.domain.comment.repository;

import andre.chamis.socialnetwork.domain.comment.model.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository class for managing comment-related operations.
 */
@Repository
@RequiredArgsConstructor
public class CommentRepository {
    private final CommentJpaRepository commentJpaRepository;

    /**
     * Saves a comment.
     *
     * @param comment The comment to be saved.
     * @return The saved comment.
     */
    public Comment save(Comment comment){
        return commentJpaRepository.save(comment);
    }

    /**
     * Deletes a comment by its ID.
     *
     * @param commentId The ID of the comment to be deleted.
     */
    public void deleteById(Long commentId) {
        commentJpaRepository.deleteById(commentId);
    }

    /**
     * Retrieves a comment by its ID.
     *
     * @param commentId The ID of the comment to retrieve.
     * @return An Optional containing the retrieved comment, if present.
     */
    public Optional<Comment> findById(Long commentId) {
        return commentJpaRepository.findById(commentId);
    }

    /**
     * Retrieves a page of comments associated with a specified post ID.
     *
     * @param postId   The ID of the post to which the comments belong.
     * @param pageable The pagination information.
     * @return A page containing the comments associated with the post.
     */
    public Page<Comment> findAllCommentsByPostId(Long postId, Pageable pageable) {
        return commentJpaRepository.findAllByPostId(postId, pageable);
    }
}
