package andre.chamis.socialnetwork.domain.post.repository;

import andre.chamis.socialnetwork.domain.post.model.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PostRepository {
    private final PostJpaRepository postJpaRepository;

    public Post save(Post post) {
        return postJpaRepository.save(post);
    }

    public Page<Post> findPostsByUserId(Long ownerId, Pageable pageable) {
        return postJpaRepository.findByOwnerId(ownerId, pageable);
    }

    public void delete(Long postId, Long ownerId) {
        postJpaRepository.deleteByPostIdAndOwnerId(postId, ownerId);
    }

    public Optional<Post> findPostById(Long postId) {
        return postJpaRepository.findById(postId);
    }

    public int deleteAllPosts() {
        return postJpaRepository.deleteAllPosts();
    }
}