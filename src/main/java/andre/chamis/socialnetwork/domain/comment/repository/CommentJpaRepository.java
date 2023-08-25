package andre.chamis.socialnetwork.domain.comment.repository;

import andre.chamis.socialnetwork.domain.comment.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentJpaRepository extends JpaRepository<Comment, Long> {
}
