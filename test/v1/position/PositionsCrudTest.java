package v1.position;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Before;
import org.junit.Test;
import play.Application;
import play.inject.guice.GuiceApplicationBuilder;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.test.WithApplication;
import v1.brand.Brand;
import v1.brand.BrandRepository;
import v1.model.Model;
import v1.model.ModelRepository;

import java.security.SecureRandom;
import java.time.Year;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static play.test.Helpers.*;

public class PositionsCrudTest extends WithApplication {

    @Override
    protected Application provideApplication() {
        return new GuiceApplicationBuilder().build();
    }

    @Test
    public void testList() {
        PositionRepository repo = app.injector().instanceOf(PositionRepository.class);

        assertTrue(requestList().isEmpty());

        Position position1 = genPosition();
        Position position2 = genPosition();

        repo.create(position1).toCompletableFuture().join();
        repo.create(position2).toCompletableFuture().join();

        assertEquals(List.of(position1, position2), requestList());
    }

    @Test
    public void testCreate() {
        PositionRepository repo = app.injector().instanceOf(PositionRepository.class);

        assertTrue(requestList().isEmpty());

        Position position1 = genPosition();
        Position position2 = genPosition();

        Position created1 = requestCreate(position1);
        Position created2 = requestCreate(position2);

        assertEquals(position1, created1);
        assertEquals(position2, created2);

        assertEquals(2, repo.list().toCompletableFuture().join().count());
    }

    @Test
    public void testUpdate() {
        PositionRepository repo = app.injector().instanceOf(PositionRepository.class);

        assertTrue(requestList().isEmpty());

        Position position1 = genPosition();
        Position position2 = genPosition();
        Position position3 = genPosition();

        repo.create(position1).toCompletableFuture().join();
        repo.create(position2).toCompletableFuture().join();

        Position updated = requestUpdate(position2.getId(), position3);
        assertEquals(position3, updated);

        position3.setId(position2.getId());
        assertEquals(List.of(position1, position3), requestList());
    }

    @Test
    public void testDelete() {
        PositionRepository repo = app.injector().instanceOf(PositionRepository.class);

        assertTrue(requestList().isEmpty());

        Position position1 = genPosition();
        Position position2 = genPosition();

        repo.create(position1).toCompletableFuture().join();
        repo.create(position2).toCompletableFuture().join();

        Result result = requestDelete(position2.getId());
        assertEquals(200, result.status());

        assertEquals(List.of(position1), requestList());
    }

    @Test
    public void testGet() {
        PositionRepository repo = app.injector().instanceOf(PositionRepository.class);

        assertTrue(requestList().isEmpty());

        Position position1 = genPosition();
        Position position2 = genPosition();
        Position position3 = genPosition();

        repo.create(position1).toCompletableFuture().join();
        repo.create(position2).toCompletableFuture().join();
        repo.create(position3).toCompletableFuture().join();

        Http.RequestBuilder req = new Http.RequestBuilder().method(GET).uri("/v1/positions/" + position2.getId().toString());
        Result result = route(app, req);
        assertEquals(200, result.status());
        assertEquals(position2, parseValue(result));
    }

    @Test
    public void testUpdate404() {
        Http.RequestBuilder req = new Http.RequestBuilder()
                .method(PUT)
                .uri("/v1/positions/" + UUID.randomUUID().toString())
                .bodyJson(toJsonNode(genPosition()));
        assertEquals(404, route(app, req).status());
    }

    @Test
    public void testGet404() {
        Http.RequestBuilder req = new Http.RequestBuilder()
                .method(GET)
                .uri("/v1/positions/" + UUID.randomUUID().toString());
        assertEquals(404, route(app, req).status());
    }

    @Test
    public void testDelete404() {
        Http.RequestBuilder req = new Http.RequestBuilder()
                .method(DELETE)
                .uri("/v1/positions/" + UUID.randomUUID().toString());
        assertEquals(404, route(app, req).status());
    }

    private List<Position> requestList() {
        Http.RequestBuilder req = new Http.RequestBuilder()
                .method(GET)
                .uri("/v1/positions");
        Result result = route(app, req);
        assertEquals(200, result.status());
        return parseValues(result);
    }

    private Position requestCreate(Position position) {
        Http.RequestBuilder req = new Http.RequestBuilder()
                .method(POST)
                .uri("/v1/positions")
                .bodyJson(toJsonNode(position));
        Result result = route(app, req);
        assertEquals(201, result.status());
        return parseValue(result);
    }

    private Result requestDelete(UUID id) {
        Http.RequestBuilder req = new Http.RequestBuilder()
                .method(DELETE)
                .uri("/v1/positions/" + id.toString());
        return route(app, req);
    }

    private Position requestUpdate(UUID id, Position position) {
        Http.RequestBuilder req = new Http.RequestBuilder()
                .method(PUT)
                .uri("/v1/positions/" + id.toString())
                .bodyJson(toJsonNode(position));
        Result result = route(app, req);
        assertEquals(200, result.status());
        return parseValue(result);
    }

    private List<Position> parseValues(Result result) {
        String body = contentAsString(result);
        try {
            return Json.mapper().readValue(body, new TypeReference<List<Position>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private Position parseValue(Result result) {
        return Json.fromJson(Json.parse(contentAsString(result)), Position.class);
    }

    private JsonNode toJsonNode(Object obj) {
        return Json.toJson(obj);
    }

    private final Brand brand = new Brand("default brand", "ru");
    private final Model model = new Model("default model", Year.of(2003), null);

    @Before
    public void beforeTest() {
        BrandRepository brands = app.injector().instanceOf(BrandRepository.class);
        ModelRepository models = app.injector().instanceOf(ModelRepository.class);
        CompletableFuture.allOf(
                brands.create(brand).toCompletableFuture(),
                models.create(model).toCompletableFuture()
        ).join();
    }

    private Position genPosition() {
        Random random = new SecureRandom();
        UUID id = UUID.randomUUID();
        Year year = Year.of(random.nextInt(5678) + 1900);
        int run = random.nextInt(100000);
        int price = random.nextInt(10000) + 1;
        return new Position(id, brand, model, year, run, price);
    }
}
