package org.cch.resource;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.cch.definition.ERole;
import org.cch.entity.RoleEntity;
import org.cch.entity.UserEntity;
import org.cch.request.AuthenticationRequest;
import org.cch.request.RegisterRequest;
import org.cch.response.AuthenticationResponse;
import org.cch.service.UserService;
import org.eclipse.microprofile.jwt.JsonWebToken;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {
    @Inject
    UserService userService;

    @Inject
    JsonWebToken jwt;

    @PermitAll
    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(AuthenticationRequest authRequest) throws Exception {
        final String token = userService.authenticate(authRequest);
        return Response.ok(new AuthenticationResponse(token)).build();
    }

    @PermitAll
    @POST
    @Path("/register")
    @Produces(MediaType.APPLICATION_JSON)
    public Response register(@NotNull @Valid RegisterRequest registerRequest, @Context UriInfo uriInfo) throws Exception {
        userService.register(registerRequest);
        URI uri = uriInfo.getAbsolutePathBuilder().build();
        return Response.created(uri).build();
    }

    @RolesAllowed("USER")
    @GET
    @Path("/role/users")
    public Response userAccess() {
        List<UserEntity> res = new ArrayList<>();
        List<UserEntity> collect = UserEntity.<UserEntity>streamAll()
                .collect(Collectors.toList());
        for (UserEntity userEntity : collect) {
            var roleEntity = userEntity.roles.stream().filter(r -> Objects.equals(r.name.name(), ERole.USER.name())).findFirst().orElse(null);
            if (Objects.nonNull(roleEntity)) {
                res.add(userEntity);
            }
        }
        return Response.ok(res.stream().map(x -> x.username).collect(Collectors.toList())).build();
    }

    @RolesAllowed("MODERATOR")
    @GET
    @Path("/role/moderator")
    public Response moderatorAccess() {
        List<UserEntity> res = new ArrayList<>();
        List<UserEntity> collect = UserEntity.<UserEntity>streamAll()
                .collect(Collectors.toList());
        for (UserEntity userEntity : collect) {
            var roleEntity = userEntity.roles.stream().filter(r -> Objects.equals(r.name.name(), ERole.MODERATOR.name())).findFirst().orElse(null);
            if (Objects.nonNull(roleEntity)) {
                res.add(userEntity);
            }
        }
        return Response.ok(res.stream().map(x -> x.username).collect(Collectors.toList())).build();
    }

    @RolesAllowed("ADMIN")
    @GET
    @Path("/role/admin")
    public Response adminAccess() {
        List<String> user = UserEntity.<UserEntity>streamAll()
                .map(n -> n.username)
                .collect(Collectors.toList());
        return Response.ok(user).build();
    }

}
