package vertx;

import io.vertx.core.Vertx;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import vertx.asyncresponse.Server;
import vertx.asyncresponse.ServiceProcessor;

@RunWith(VertxUnitRunner.class)
public class MyFirstVerticleTest {

    private Vertx vertx;

    @Before
    public void setUp(TestContext context) {
        vertx = Vertx.vertx();
        //vertx.deployVerticle(Main.class.getName(),     context.asyncAssertSuccess());
        vertx.deployVerticle(Server.class.getName(), context.asyncAssertSuccess());
        vertx.deployVerticle(ServiceProcessor.class.getName(), context.asyncAssertSuccess());
    }

    @After
    public void tearDown(TestContext context) {
        vertx.close(context.asyncAssertSuccess());
    }

    @Test
    public void testMyApplication(TestContext context) {
        final Async async = context.async();

        vertx.createHttpClient().getNow(8080, "localhost", "/products/",
                response -> {
                    response.handler(body -> {
                        //System.out.println(body.toJsonObject().getJsonArray("id").contains("prod3568"));
                       context.assertTrue(body.toJsonObject().getJsonArray("id").contains("prod3568"));
                        async.complete();
                    });
                });
    }
}