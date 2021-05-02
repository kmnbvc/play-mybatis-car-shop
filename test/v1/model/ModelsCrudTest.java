package v1.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Test;
import play.Application;
import play.inject.guice.GuiceApplicationBuilder;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.test.WithApplication;

import java.time.Year;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static play.test.Helpers.*;

public class ModelsCrudTest extends WithApplication {

    @Override
    protected Application provideApplication() {
        return new GuiceApplicationBuilder().build();
    }

    @Test
    public void testList() {
        ModelRepository repo = app.injector().instanceOf(ModelRepository.class);

        assertTrue(requestList().isEmpty());

        Model model1 = new Model("m1", Year.of(2001), null);
        Model model2 = new Model("m2", Year.of(2009), Year.of(2021));

        repo.create(model1).toCompletableFuture().join();
        repo.create(model2).toCompletableFuture().join();

        assertEquals(List.of(model1, model2), requestList());
    }


    @Test
    public void testCreate() {
        ModelRepository repo = app.injector().instanceOf(ModelRepository.class);

        assertTrue(requestList().isEmpty());

        Model model1 = new Model("m1", Year.of(2001), null);
        Model model2 = new Model("m2", Year.of(2009), Year.of(2021));

        Model created1 = requestCreate(model1);
        Model created2 = requestCreate(model2);

        assertEquals(model1, created1);
        assertEquals(model2, created2);

        assertEquals(2, repo.list().toCompletableFuture().join().count());
    }

    @Test
    public void testUpdate() {
        ModelRepository repo = app.injector().instanceOf(ModelRepository.class);

        assertTrue(requestList().isEmpty());

        Model model1 = new Model("m1", Year.of(2001), null);
        Model model2 = new Model("m2", Year.of(2009), Year.of(2021));
        Model model3 = new Model("m3", Year.of(2002), Year.of(2029));

        repo.create(model1).toCompletableFuture().join();
        repo.create(model2).toCompletableFuture().join();

        Model updated = requestUpdate(model2.getName(), model3);
        assertEquals(model3, updated);

        assertEquals(List.of(model1, model3), requestList());
    }

    @Test
    public void testDelete() {
        ModelRepository repo = app.injector().instanceOf(ModelRepository.class);

        assertTrue(requestList().isEmpty());

        Model model1 = new Model("m1", Year.of(2001), null);
        Model model2 = new Model("m2", Year.of(2009), Year.of(2021));

        repo.create(model1).toCompletableFuture().join();
        repo.create(model2).toCompletableFuture().join();

        Result result = requestDelete(model2.getName());
        assertEquals(200, result.status());

        assertEquals(List.of(model1), requestList());
    }

    @Test
    public void testGet() {
        ModelRepository repo = app.injector().instanceOf(ModelRepository.class);

        assertTrue(requestList().isEmpty());

        Model model1 = new Model("m1", Year.of(2001), null);
        Model model2 = new Model("m2", Year.of(2003), Year.of(2020));
        Model model3 = new Model("m3", Year.of(2004), null);

        repo.create(model1).toCompletableFuture().join();
        repo.create(model2).toCompletableFuture().join();
        repo.create(model3).toCompletableFuture().join();

        Http.RequestBuilder req = new Http.RequestBuilder().method(GET).uri("/v1/models/" + model2.getName());
        Result result = route(app, req);
        assertEquals(200, result.status());
        assertEquals(model2, parseValue(result));
    }

    @Test
    public void testUpdate404() {
        Http.RequestBuilder req = new Http.RequestBuilder()
                .method(PUT)
                .uri("/v1/models/somename")
                .bodyJson(toJsonNode(new Model("rand", Year.of(2001), null)));
        assertEquals(404, route(app, req).status());
    }

    @Test
    public void testGet404() {
        Http.RequestBuilder req = new Http.RequestBuilder()
                .method(GET)
                .uri("/v1/models/somename");
        assertEquals(404, route(app, req).status());
    }

    @Test
    public void testDelete404() {
        Http.RequestBuilder req = new Http.RequestBuilder()
                .method(DELETE)
                .uri("/v1/models/somename");
        assertEquals(404, route(app, req).status());
    }

    private List<Model> requestList() {
        Http.RequestBuilder req = new Http.RequestBuilder()
                .method(GET)
                .uri("/v1/models");
        Result result = route(app, req);
        assertEquals(200, result.status());
        return parseValues(result);
    }

    private Model requestCreate(Model model) {
        Http.RequestBuilder req = new Http.RequestBuilder()
                .method(POST)
                .uri("/v1/models")
                .bodyJson(toJsonNode(model));
        Result result = route(app, req);
        assertEquals(201, result.status());
        return parseValue(result);
    }

    private Result requestDelete(String name) {
        Http.RequestBuilder req = new Http.RequestBuilder()
                .method(DELETE)
                .uri("/v1/models/" + name);
        return route(app, req);
    }

    private Model requestUpdate(String name, Model model) {
        Http.RequestBuilder req = new Http.RequestBuilder()
                .method(PUT)
                .uri("/v1/models/" + name)
                .bodyJson(toJsonNode(model));
        Result result = route(app, req);
        assertEquals(200, result.status());
        return parseValue(result);
    }

    private List<Model> parseValues(Result result) {
        String body = contentAsString(result);
        try {
            return Json.mapper().readValue(body, new TypeReference<List<Model>>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private Model parseValue(Result result) {
        return Json.fromJson(Json.parse(contentAsString(result)), Model.class);
    }

    private JsonNode toJsonNode(Object obj) {
        return Json.toJson(obj);
    }

}
