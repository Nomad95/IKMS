package pl.politechnika.ikms.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.validator.constraints.Email;
import pl.politechnika.ikms.commons.abstracts.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

import static javax.persistence.GenerationType.SEQUENCE;

@Data
@Entity
@Table(name = "users")
@EqualsAndHashCode(callSuper = false, exclude = "addresses")
@ToString(exclude = {"password","addresses"})
@SequenceGenerator(name="users_seq_name",sequenceName="users_seq", allocationSize=1, initialValue = 5)
public class UserEntity extends AbstractEntity{

    @Id
    @GeneratedValue(strategy = SEQUENCE,generator = "users_seq_name")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "username")
    @Size(min = 5, max = 30)
    private String username;

    @NotNull
    @JsonIgnore
    @Column(name = "password")
    @Size(min = 5, max = 100)
    private String password;

    @NotNull
    @Email
    @Column(name = "email")
    @Size(min = 5, max = 70)
    private String email;

    @Column(name = "enabled")
    private boolean enabled;

    @NotNull
    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "last_logged")
    private Date lastLogged;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "role")
    private Role role;

}
