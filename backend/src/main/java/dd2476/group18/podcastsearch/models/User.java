package dd2476.group18.podcastsearch.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = {"email"}))
public class User {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "email")
    private String email;
    @Column(name = "first_name", nullable = true)
    private String firstName;

    @Column(name = "last_name", nullable = true)
    private String lastName;

    @Column(name = "spotify_access_token", nullable = true)
    private String spotifyAccessToken;

    @Column(name = "spotify_refressh_token", nullable = true)
    private String spotifyRefreshToken;

    @Column(name = "spotify_expire_in", nullable = true)
    private Integer spotifyExpireIn;
}
