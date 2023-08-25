package andre.chamis.socialnetwork.domain.post.repository;

import andre.chamis.socialnetwork.domain.post.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostJpaRepository extends JpaRepository<Post, Long> {
}
