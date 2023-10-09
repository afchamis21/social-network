package andre.chamis.socialnetwork.domain.friend.relation.repository;

import andre.chamis.socialnetwork.domain.user.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class PotentialFriendsRowMapper implements RowMapper<Page<User>> {
    private final Pageable pageable;
    public PotentialFriendsRowMapper(Pageable pageable) {
        this.pageable = pageable;
    }

    @Override
    public Page<User> mapRow(ResultSet rs, int rowNum) throws SQLException {
        List<User> users = new ArrayList<>();
        Long totalValues = null;
        do {
            if (totalValues == null) {
                totalValues = rs.getLong("total_results");
            }
            User user = new User();
            user.setUserId(rs.getLong("user_id"));
            user.setEmail(rs.getString("email"));
            user.setUsername(rs.getString("username"));
            user.setCreateDt(rs.getDate("create_dt"));
            users.add(user);
        } while (rs.next());

        return new PageImpl<>(users, pageable, totalValues);
    }
}
