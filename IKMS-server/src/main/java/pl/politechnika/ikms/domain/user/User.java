package pl.politechnika.ikms.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import pl.politechnika.ikms.commons.abstracts.AbstractEntity;
import pl.politechnika.ikms.commons.util.CommonConstants;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import java.util.Collection;
import java.util.Date;

import static javax.persistence.GenerationType.AUTO;
import static javax.persistence.GenerationType.SEQUENCE;

@Data
@Entity
@Table(name = "users")
@EqualsAndHashCode(callSuper = false)
@ToString(exclude = "password")
@SequenceGenerator(name="users_seq_name",sequenceName="users_seq", allocationSize=1)
public class User extends AbstractEntity{

    @Id
    @GeneratedValue(strategy = SEQUENCE,generator = "users_seq_name")
    @NotNull
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "username")
    private String username;

    @NotNull
    @JsonIgnore
    @Column(name = "password")
    private String password;

    @NotNull
    @Pattern(regexp = CommonConstants.EMAIL_REGEXP)
    @Column(name = "email")
    private String email;

    @NotNull
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
