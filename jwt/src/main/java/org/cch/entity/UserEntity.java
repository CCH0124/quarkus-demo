package org.cch.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.*;

@Entity
@Table(	name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "account"),
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        })
//@NamedQueries({
//        // mapper class name not db table name
//        @NamedQuery(name = "User.existsByAccount", query = "from User where account = ?1"),
//})
public class UserEntity extends PanacheEntityBase {
    @Id
    @Column(nullable = false, columnDefinition = "uuid DEFAULT uuid_generate_v4()")
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator")
    public UUID id;

    @Column(nullable = false, length = 30)
    public String account;

    @NotBlank
    @Size(max = 20)
    @Column(nullable = false, length=20)
    public String username;

    @NotBlank
    @Size(max = 50)
    @Email
    @Column(unique = true, nullable = false)
    public String email;

    @NotBlank
    @Size(max = 120)
    @Column(nullable = false)
    public String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(	name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    public Set<RoleEntity> roles = new HashSet<>();

    public static Optional<UserEntity> findByAccount(String account){
        return find("account", account).firstResultOptional();
    }

    public static Optional<UserEntity> findByUsername(String username){
        return find("username", username).firstResultOptional();
    }

//    public static Boolean existsByAccount(String account) {
//        return Objects.nonNull(find("#User.existsByAccount").firstResult());
//    }

    public static Boolean existsByUsername(String username) {
        return Objects.nonNull(find("username", username).firstResult());
    }

    public static Boolean existsByEmail(String email) {
        return Objects.nonNull(find("email", email).firstResult());
    }
}
