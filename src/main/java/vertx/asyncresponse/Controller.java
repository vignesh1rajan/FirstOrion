package vertx.asyncresponse;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import javax.ws.rs.*;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by nexis on 11/6/2016.
 */
@Path("/")
public class Controller {

    @GET
    @Path("/persons/{personID}")
    @Produces({MediaType.APPLICATION_JSON})
    public void get(

            // Suspend the request
            @Suspended final AsyncResponse asyncResponse,

            // Inject the Vertx instance
            @Context Vertx vertx,

            @PathParam("personID") String personId
    ) {

        if (personId == null) {
            asyncResponse.resume(Response.status(Response.Status.BAD_REQUEST).build());
            return;
        }

        // Send a get message to the backend
        vertx.eventBus().<JsonObject>send("backend", new JsonObject()
                .put("op", "get")
                .put("id", personId), msg -> {

            // When we get the response we resume the Jax-RS async response
            if (msg.succeeded()) {
                JsonObject json = msg.result().body();
                if (json != null) {
                    asyncResponse.resume(json.encode());
                } else {
                    asyncResponse.resume(Response.status(Response.Status.NOT_FOUND).build());
                }
            } else {
                asyncResponse.resume(Response.status(Response.Status.INTERNAL_SERVER_ERROR).build());
            }
        });
    }

    @PUT
    @Path("/persons/{personID}")
    @Produces({MediaType.APPLICATION_JSON})
    public void put(

            // Suspend the request
            @Suspended final AsyncResponse asyncResponse,

            // Inject the Vertx instance
            @Context Vertx vertx,

            @PathParam("personID") String personID,  String product
    ) {

        if (personID == null || product == null) {
            asyncResponse.resume(Response.status(Response.Status.BAD_REQUEST).build());
            return;
        }

        JsonObject personJson;
        try {
            personJson = new JsonObject(product);
        } catch (Exception e) {
            asyncResponse.resume(Response.status(Response.Status.BAD_REQUEST).build());
            return;
        }

        // Send an add message to the backend
        vertx.eventBus().<Boolean>send("backend", new JsonObject()
                .put("op", "add")
                .put("id", personID)
                .put("person", personJson), msg -> {

            // When we get the response we resume the Jax-RS async response
            if (msg.succeeded()) {
                if (msg.result().body()) {
                    asyncResponse.resume(Response.status(Response.Status.OK).build());
                } else {
                    asyncResponse.resume(Response.status(Response.Status.BAD_REQUEST).build());
                }
            } else {
                asyncResponse.resume(Response.status(Response.Status.INTERNAL_SERVER_ERROR).build());
            }
        });
    }

    @GET
    @Path("/persons")
    @Produces({MediaType.APPLICATION_JSON})
    public void list(

            // Suspend the request
            @Suspended final AsyncResponse asyncResponse,

            // Inject the Vertx instance
            @Context Vertx vertx) {

        // Send a list message to the backend
        vertx.eventBus().<JsonArray>send("backend", new JsonObject().put("op", "list"), msg -> {

            // When we get the response we resume the Jax-RS async response
            if (msg.succeeded()) {
                JsonArray json = msg.result().body();
                if (json != null) {
                    asyncResponse.resume(json.encode());
                } else {
                    asyncResponse.resume(Response.status(Response.Status.NOT_FOUND).build());
                }
            } else {
                asyncResponse.resume(Response.status(Response.Status.INTERNAL_SERVER_ERROR).build());
            }
        });
    }
}