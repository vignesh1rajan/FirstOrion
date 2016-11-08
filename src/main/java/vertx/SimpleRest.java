package vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;

/**
 * Created by nexis on 11/6/2016.
 */
public class SimpleRest extends AbstractVerticle {

  @Override
    public void start(Future<Void> future){

        /*
     HttpServer server  = vertx.createHttpServer();
      server.requestStream().toObservable().subscribe(req -> {
          req.response().putHeader("content-type", "text/html").end("<html><body><h1>Hello from vert.x!</h1></body></html>");
      });*/
      vertx.createHttpServer()
              .requestHandler(httpServerRequest -> {
                httpServerRequest.response().end( "Hello");
      }).listen(8080,result -> {
          if (result.succeeded()) {
              if (result.succeeded()) {
                  future.complete();
              }else {
                  future.fail(result.cause());
              }
          }
      });
  }
}
