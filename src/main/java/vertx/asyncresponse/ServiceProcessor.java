package vertx.asyncresponse;

/**
 * Created by nexis on 11/7/2016.
 */
import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.HashMap;
import java.util.Map;


public class ServiceProcessor extends AbstractVerticle {

    private Map<String, JsonObject> products = new HashMap<>();

    public ServiceProcessor() {

        // Setup initial data
        addProduct(new JsonObject().put("id", "phone123").put("name", "Dr. Strange").put("address", "Volcano").put("status", "unprocessed"));
        addProduct(new JsonObject().put("id", "phone222").put("name", "Iron man").put("address", "Stark Industries").put("status", "unprocessed"));
        addProduct(new JsonObject().put("id", "phone333").put("name", "Monty Python").put("address", "Wonka land").put("status", "unprocessed"));
    }

    @Override
    public void start() throws Exception {

        // A simple backend
        vertx.eventBus().<JsonObject>consumer("backend", msg -> {

            JsonObject json = msg.body();

            switch (json.getString("op", "")) {
                case "get": {
                    String productID = json.getString("id");
                    msg.reply(products.get(productID));
                    break;
                }
                case "add": {
                    String productID = json.getString("id");
                    JsonObject product = json.getJsonObject("person");
                    product.put("id", productID);
                    msg.reply(addProduct(product));
                    break;
                }
                case "list": {
                    JsonArray arr = new JsonArray();
                    products.forEach((k, v) -> arr.add(v));
                    msg.reply(arr);
                    break;
                }
                default: {
                    msg.fail(0, "operation not permitted");
                }
            }
        });
    }

    private boolean addProduct(JsonObject product) {
        if (product.containsKey("name") || product.containsKey("address") || product.containsKey("status")) {
            if (product.getString("status").equalsIgnoreCase("unprocessed")){
                product.put("status","processed");
            }
            products.put(product.getString("id"), product);
            return true;
        } else {
            return false;
        }
    }
}
