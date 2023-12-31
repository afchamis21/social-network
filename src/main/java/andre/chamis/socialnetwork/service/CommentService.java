package andre.chamis.socialnetwork.service;

import andre.chamis.socialnetwork.context.ServiceContext;
import andre.chamis.socialnetwork.domain.comment.dto.CreateCommentDTO;
import andre.chamis.socialnetwork.domain.comment.dto.DeleteCommentDTO;
import andre.chamis.socialnetwork.domain.comment.dto.EditCommentDTO;
import andre.chamis.socialnetwork.domain.comment.dto.GetCommentDTO;
import andre.chamis.socialnetwork.domain.comment.model.Comment;
import andre.chamis.socialnetwork.domain.comment.repository.CommentRepository;
import andre.chamis.socialnetwork.domain.exception.EntityNotFoundException;
import andre.chamis.socialnetwork.domain.exception.ForbiddenException;
import andre.chamis.socialnetwork.domain.exception.InvalidDataException;
import andre.chamis.socialnetwork.domain.post.model.Post;
import andre.chamis.socialnetwork.domain.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;

/**
 * Service class for managing comments on posts.
 */
@Service
@RequiredArgsConstructor
public class CommentService {
    private final PostService postService;
    private final UserService userService;
    private final SessionService sessionService;
    private final CommentRepository commentRepository;

    /**
     * Creates a new comment on a post.
     *
     * @param createCommentDTO The DTO containing comment creation data.
     * @return DTO representation of the created comment.
     */
    public GetCommentDTO createComment(CreateCommentDTO createCommentDTO){
        boolean postExists = postService.checkExistsPostById(createCommentDTO.postId());
        if (!postExists) {
            throw new EntityNotFoundException("Nenhum post encontrado com o id: " + createCommentDTO.postId(), HttpStatus.BAD_REQUEST);
        }

        User currentUser = userService.findCurrentUser();

        Date now = Date.from(Instant.now());

        Comment comment = new Comment();
        comment.setPostId(createCommentDTO.postId());
        comment.setOwnerId(currentUser.getUserId());
        comment.setContent(createCommentDTO.content());
        comment.setCreateDt(now);
        comment.setUpdateDt(now);

        comment = commentRepository.save(comment);

        return new GetCommentDTO()
                .withComment(comment)
                .withUser(currentUser);
    }

    /**
     * Deletes a comment.
     *
     * @param deleteCommentDTO The DTO containing comment ID to be deleted.
     */
    public void deleteComment(DeleteCommentDTO deleteCommentDTO){
        Long currentUserId = sessionService.getCurrentUserId();

        Optional<Comment> commentOptional = commentRepository.findById(deleteCommentDTO.commentId());
        Comment comment = commentOptional.orElseThrow(() -> new EntityNotFoundException("Nenhum comentário encontrado com o id: " + deleteCommentDTO.commentId(), HttpStatus.BAD_REQUEST));

        Optional<Post> postOptional = postService.findPostById(comment.getPostId());
        Post post = postOptional.orElseThrow(() -> new EntityNotFoundException("Nenhum post encontrado com o id: " + comment.getPostId(), HttpStatus.BAD_REQUEST));

        if (!comment.getOwnerId().equals(currentUserId) && !post.getOwnerId().equals(currentUserId)){
            throw new ForbiddenException("Você não tem permissão para apagar esse comentário!");
        }

        commentRepository.deleteById(deleteCommentDTO.commentId());
        ServiceContext.addMessage("Comentário deletado!");
    }

    /**
     * Edits an existing comment.
     *
     * @param editCommentDTO The DTO containing comment ID and updated content.
     * @return DTO representation of the edited comment.
     */
    public GetCommentDTO editComment(EditCommentDTO editCommentDTO){
        Optional<Comment> commentOptional = commentRepository.findById(editCommentDTO.commentId());
        Comment comment = commentOptional.orElseThrow(() -> new EntityNotFoundException("Nenhum comentário encontrado com o id: " + editCommentDTO.commentId(), HttpStatus.BAD_REQUEST));

        User currentUser = userService.findCurrentUser();

        if (!comment.getOwnerId().equals(currentUser.getUserId())){
            throw new ForbiddenException("Você não tem permissão para editar esse comentário!");
        }

        if (editCommentDTO.content() != null){
            comment.setContent(editCommentDTO.content());
            comment.setUpdateDt(Date.from(Instant.now()));
            comment = commentRepository.save(comment);
        }

        return new GetCommentDTO()
                .withComment(comment)
                .withUser(currentUser);
    }

    /**
     * Retrieves all comments for a given post.
     *
     * @param postIdOptional The optional post ID to retrieve comments for.
     * @param pageable The pagination parameters.
     * @return A page of DTO representations of comments on the post.
     */
    public Page<GetCommentDTO> findAllCommentsByPostId(Optional<Long> postIdOptional, Pageable pageable){
        Long postId = postIdOptional.orElseThrow(() -> new InvalidDataException("Faltando parâmetro de requisição postId", HttpStatus.BAD_REQUEST));
        Page<Comment> comments = commentRepository.findAllCommentsByPostId(postId, pageable);

        return comments.map(comment -> {
            Optional<User> userOptional = userService.findUserById(comment.getOwnerId());
            User user = userOptional.orElseThrow(() -> new EntityNotFoundException(HttpStatus.INTERNAL_SERVER_ERROR));
            return new GetCommentDTO()
                    .withComment(comment)
                    .withUser(user);
        });
    }
}
