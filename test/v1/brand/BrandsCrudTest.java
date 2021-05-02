package v1.brand;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import play.Application;
import play.inject.guice.GuiceApplicationBuilder;
import play.mvc.Http;
import play.mvc.Result;
import play.test.WithApplication;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static play.test.Helpers.*;

public class BrandsCrudTest extends WithApplication {

    private final ObjectMapper jsonMapper = new ObjectMapper();

    @Override
    protected Application provideApplication() {
        return new GuiceApplicationBuilder().build();
    }

    @Test
    public void testList() {
        BrandRepository repo = app.injector().instanceOf(BrandRepository.class);

        assertTrue(requestList().isEmpty());

        Brand brand1 = new Brand("korchvagentop", "by");
        Brand brand2 = new Brand("korchvagenultra", "by");

        repo.create(brand1).toCompletableFuture().join();
        repo.create(brand2).toCompletableFuture().join();

        assertEquals(List.of(brand1, brand2), requestList());
    }

    @Test
    public void testCreate() {
        BrandRepository repo = app.injector().instanceOf(BrandRepository.class);

        assertTrue(requestList().isEmpty());

        Brand brand1 = new Brand("korchvagenextra", "by");
        Brand brand2 = new Brand("korchvagensuper", "by");

        Brand created1 = requestCreate(brand1);
        Brand created2 = requestCreate(brand2);

        assertEquals(brand1, created1);
        assertEquals(brand2, created2);

        assertEquals(2, repo.list().toCompletableFuture().join().count());
    }

    @Test
    public void testUpdate() {
        BrandRepository repo = app.injector().instanceOf(BrandRepository.class);

        assertTrue(requestList().isEmpty());

        Brand brand1 = new Brand("korchvagen1", "by");
        Brand brand2 = new Brand("korchvagen2", "by");
        Brand brand3 = new Brand("korchvagen3", "by");

        repo.create(brand1).toCompletableFuture().join();
        repo.create(brand2).toCompletableFuture().join();

        Brand updated = requestUpdate(brand2.getName(), brand3);
        assertEquals(brand3, updated);

        assertEquals(List.of(brand1, brand3), requestList());
    }

    @Test
    public void testDelete() {
        BrandRepository repo = app.injector().instanceOf(BrandRepository.class);

        assertTrue(requestList().isEmpty());

        Brand brand1 = new Brand("korchvagenduper", "by");
        Brand brand2 = new Brand("korchvagenmega", "by");

        repo.create(brand1).toCompletableFuture().join();
        repo.create(brand2).toCompletableFuture().join();

        Result result = requestDelete(brand2.getName());
        assertEquals(200, result.status());

        assertEquals(List.of(brand1), requestList());
    }

    @Test
    public void testGet() {
        BrandRepository repo = app.injector().instanceOf(BrandRepository.class);

        assertTrue(requestList().isEmpty());

        Brand brand1 = new Brand("korchvagen1", "by");
        Brand brand2 = new Brand("korchvagen2", "by");
        Brand brand3 = new Brand("korchvagen3", "by");

        repo.create(brand1).toCompletableFuture().join();
        repo.create(brand2).toCompletableFuture().join();
        repo.create(brand3).toCompletableFuture().join();

        Http.RequestBuilder req = new Http.RequestBuilder().method(GET).uri("/v1/brands/" + brand2.getName());
        Result result = route(app, req);
        assertEquals(200, result.status());
        assertEquals(brand2, parseValue(result));
    }

    @Test
    public void testUpdate404() {
        Http.RequestBuilder req = new Http.RequestBuilder()
                .method(PUT)
                .uri("/v1/brands/somename")
                .bodyJson(toJsonNode(new Brand("rand", "ru")));
        assertEquals(404, route(app, req).status());
    }

    @Test
    public void testGet404() {
        Http.RequestBuilder req = new Http.RequestBuilder()
                .method(GET)
                .uri("/v1/brands/somename");
        assertEquals(404, route(app, req).status());
    }

    @Test
    public void testDelete404() {
        Http.RequestBuilder req = new Http.RequestBuilder()
                .method(DELETE)
                .uri("/v1/brands/somename");
        assertEquals(404, route(app, req).status());
    }

    private List<Brand> requestList() {
        Http.RequestBuilder req = new Http.RequestBuilder()
                .method(GET)
                .uri("/v1/brands");
        Result result = route(app, req);
        assertEquals(200, result.status());
        return parseValues(result);
    }

    private Brand requestCreate(Brand brand) {
        Http.RequestBuilder req = new Http.RequestBuilder()
                .method(POST)
                .uri("/v1/brands")
                .bodyJson(toJsonNode(brand));
        Result result = route(app, req);
        assertEquals(201, result.status());
        return parseValue(result);
    }

    private Result requestDelete(String name) {
        Http.RequestBuilder req = new Http.RequestBuilder()
                .method(DELETE)
                .uri("/v1/brands/" + name);
        return route(app, req);
    }

    private Brand requestUpdate(String name, Brand brand) {
        Http.RequestBuilder req = new Http.RequestBuilder()
                .method(PUT)
                .uri("/v1/brands/" + name)
                .bodyJson(toJsonNode(brand));
        Result result = route(app, req);
        assertEquals(200, result.status());
        return parseValue(result);
    }

    private List<Brand> parseValues(Result result) {
        String body = contentAsString(result);
        try {
            return jsonMapper.readValue(body, new TypeReference<List<Brand>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private Brand parseValue(Result result) {
        try {
            return jsonMapper.readValue(contentAsString(result), Brand.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private JsonNode toJsonNode(Object obj) {
        return jsonMapper.valueToTree(obj);
    }
}
