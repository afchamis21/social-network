package andre.chamis.socialnetwork.domain.auth.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "refresh_tokens")
public class RefreshTokenEntity {
    @Id
    @GeneratedValue
    @Column(name = "refresh_token_id")
    private Long refreshTokenId;

    private String username;

    private String token;

    @Column(name = "create_dt")
    private Date createDt;

    @Column(name = "expire_dt")
    private Date expireDt;
}
