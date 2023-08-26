package andre.chamis.socialnetwork.domain.comment.repository;

import andre.chamis.socialnetwork.domain.comment.model.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CommentRepository {
    private final CommentJpaRepository commentJpaRepository;

    public Comment save(Comment comment){
        return commentJpaRepository.save(comment);
    }

    public void deleteById(Long commentId) {
        commentJpaRepository.deleteById(commentId);
    }

    public Optional<Comment> findById(Long commentId) {
        return commentJpaRepository.findById(commentId);
    }

    public Page<Comment> findAllCommentsByPostId(Long postId, Pageable pageable) {
        return commentJpaRepository.findAllByPostId(postId, pageable);
    }
}
