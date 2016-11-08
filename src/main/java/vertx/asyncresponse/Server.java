package vertx.asyncresponse;

/**
 * Created by vrajan on 11/7/2016.
 */
import io.vertx.core.AbstractVerticle;
import org.jboss.resteasy.plugins.server.vertx.VertxRequestHandler;
import org.jboss.resteasy.plugins.server.vertx.VertxResteasyDeployment;


public class Server extends AbstractVerticle {

    @Override
    public void start() throws Exception {

        // Build the Jax-RS controller deployment
        VertxResteasyDeployment deployment = new VertxResteasyDeployment();
        deployment.start();
        deployment.getRegistry().addPerInstanceResource(Controller.class);

        // Start the front end server using the Jax-RS controller
        vertx.createHttpServer()
                .requestHandler(new VertxRequestHandler(vertx, deployment))
                .listen(8080, ar -> {
                    System.out.println("Server started on port "+ ar.result().actualPort());
                });
    }
}
