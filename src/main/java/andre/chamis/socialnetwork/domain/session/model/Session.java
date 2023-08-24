package andre.chamis.socialnetwork.domain.session.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "sessions")
public class Session {
    @Id
    @GeneratedValue
    @Column(name = "session_id")
    private Long sessionId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "create_dt")
    private Date createDt;

    @Column(name = "expire_dt")
    private Date expireDt;
}
