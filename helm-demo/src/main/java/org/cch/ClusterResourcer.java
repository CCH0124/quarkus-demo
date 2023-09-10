package org.cch;

import java.util.List;
import java.util.Objects;

import io.fabric8.kubernetes.api.model.Namespace;
import io.fabric8.kubernetes.api.model.Node;
import io.fabric8.kubernetes.client.KubernetesClient;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/cluster")
public class ClusterResourcer {

    @Inject
    private KubernetesClient kubernetesClient;

    @GET
    @Path("/namespace")
    public List<String> clusterNamespaces() {
        return kubernetesClient.namespaces().list()
                .getItems()
                .stream().map(x -> x.getMetadata().getName()).toList();
    }

    @GET
    @Path("/nodes")
    public List<String> clusterNodes() {
        return kubernetesClient.nodes().list().getItems().stream().map(x -> x.getMetadata().getName())
                .toList();
    }

    @GET
    @Path("/node/status")
    public Response clusterNodeStatus(@QueryParam("nodeName") String nodeName) {
        var node = kubernetesClient.nodes().list().getItems().stream()
                .filter(x -> Objects.equals(x.getMetadata().getName(), nodeName))
                .findFirst();

        if (node.isPresent()) {
            return Response.ok(node.get().getStatus()).build();
        }

        return Response.status(Status.NOT_FOUND).build();

    }
}
