package andre.chamis.socialnetwork.domain.auth.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@Table(name = "refresh_tokens")
@AllArgsConstructor
@NoArgsConstructor
public class RefreshTokenEntity {
    @Id
    @GeneratedValue
    private Long id;

    private String username;

    private String token;

    @Column(name = "create_dt")
    private Date createDt;

    @Column(name = "expire_dt")
    private Date expireDt;
}
