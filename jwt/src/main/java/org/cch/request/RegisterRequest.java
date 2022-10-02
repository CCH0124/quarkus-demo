package org.cch.request;

import org.cch.definition.ERole;
import org.cch.entity.RoleEntity;
import org.cch.entity.UserEntity;
import org.cch.exception.AuthenticationRoleException;
import org.cch.service.UserService;
import org.jboss.logging.Logger;

import javax.transaction.Transactional;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class RegisterRequest {

    private static final Logger LOG = Logger.getLogger(RegisterRequest.class);

    @NotNull
    private String account;

    @NotNull
    private String username;

    @Email
    @NotNull
    private String email;

    @NotNull
    private String password;

    @NotEmpty
    private Set<ERole> roles;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<ERole> getRoles() {
        return roles;
    }

    public void setRoles(Set<ERole> roles) {
        this.roles = roles;
    }

    public RegisterRequest() {
    }

    public RegisterRequest(String account, String username, String email, String password, Set<ERole> roles) {
        this.account = account;
        this.username = username;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    @Transactional
    public static UserEntity toUser(RegisterRequest registerRequest) throws AuthenticationRoleException {
        var user = new UserEntity();
        user.account = registerRequest.getAccount();
        user.username = registerRequest.getUsername();
        user.password = registerRequest.getPassword();
        var collect = registerRequest.getRoles().stream().map(RoleEntity::findByName).collect(Collectors.toSet());
        LOG.debugf("registerRequest Roles: %s", collect.toString());
        user.roles = collect;
        user.email = registerRequest.getEmail();
        return user;
    }
}
