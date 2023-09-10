package org.cch;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.jboss.logging.Logger;
import org.jboss.resteasy.reactive.RestPath;

import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.client.KubernetesClient;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/pod")
public class PodResource {
    @Inject
    private KubernetesClient kubernetesClient;

    @Inject
    Logger log;

    @GET
    @Path("/list/name")
    public List<String> podHostName(@QueryParam("namespace") final String namespace) {
        return kubernetesClient.pods().inNamespace(namespace).list()
        .getItems().stream()
            .map(x -> x.getMetadata().getName())
            .toList();
    }

    @GET
    @Path("/list/status/{name}")
    public Response pods(@QueryParam("namespace") final String namespace, @RestPath final String name) {
        log.infof("Pod name: %s", name);
        Optional<Pod> pod = kubernetesClient.pods().inNamespace(namespace).list().getItems()
            .stream()
            .filter(x -> Objects.equals(name, x.getMetadata().getName()))
            .findFirst();
        if (pod.isPresent()) {
           return Response.ok(pod.get()).build();
        } 
        return Response.status(Status.NOT_FOUND).build();
    }
}