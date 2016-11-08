package vertx.asyncresponse;

import io.vertx.core.AbstractVerticle;
import vertx.util.*;

/**
 * Created by nexis on 11/6/2016.
 */
public class Main extends AbstractVerticle {


    public static void main(String[] args) {
        Runner.runExample(Main.class);
    }

    @Override
    public void start() throws Exception {
        vertx.deployVerticle(Server.class.getName());
        vertx.deployVerticle(ServiceProcessor.class.getName());
    }
}



/**
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
                System.out.println("Server started on port "+ ar.result());
            });
}

*/

