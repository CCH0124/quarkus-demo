package org.cch.service;

import org.cch.entity.UserEntity;
import org.cch.exception.AuthenticationAccountException;
import org.cch.exception.AuthenticationPasswordException;
import org.cch.exception.AuthenticationRoleException;
import org.cch.request.AuthenticationRequest;
import org.cch.request.RegisterRequest;
import org.cch.utils.Token;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class UserService {

    private static final Logger LOG = Logger.getLogger(UserService.class);

    @Inject
    CryptoService cryptoService;

    public String authenticate(final AuthenticationRequest authRequest)
            throws Exception {
        var user = UserEntity.findByAccount(authRequest.getAccount())
                .orElseThrow(AuthenticationAccountException::new);
        if (user.password.equals(cryptoService.encrypt(authRequest.getPassword()))){
            var roles = user.roles.stream().map(x -> x.name).collect(Collectors.toSet());
            return Token.generateToken(user.username, user.account, user.email, user.id.toString(), roles);
        }
        throw new AuthenticationPasswordException();
    }

    @Transactional
    public void register(final RegisterRequest registerRequest) throws AuthenticationAccountException, AuthenticationRoleException {
        LOG.debugf("roles: %s", registerRequest.getRoles().toString());
        var user = RegisterRequest.toUser(registerRequest);
        Optional<UserEntity> byAccount = UserEntity.findByAccount(registerRequest.getAccount());
        if (byAccount.isPresent()) {
            throw new AuthenticationAccountException();
        }
        user.password = cryptoService.encrypt(user.password);
        user.persist();
    }


}
