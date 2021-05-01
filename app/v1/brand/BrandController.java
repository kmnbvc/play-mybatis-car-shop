package v1.brand;

import com.fasterxml.jackson.databind.JsonNode;
import play.libs.Json;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Results;

import javax.inject.Inject;
import java.util.concurrent.CompletionStage;

import static java.util.stream.Collectors.toList;

public class BrandController extends Controller {

    private final HttpExecutionContext ec;
    private final BrandRepository repository;

    @Inject
    public BrandController(HttpExecutionContext ec, BrandRepository repository) {
        this.ec = ec;
        this.repository = repository;
    }

    public CompletionStage<Result> list() {
        return repository.list().thenApplyAsync(brands -> ok(Json.toJson(brands.collect(toList()))));
    }

    public CompletionStage<Result> get(String name) {
        return repository.get(name).thenApplyAsync(result -> result.map(value -> ok(Json.toJson(value))).orElseGet(Results::notFound), ec.current());
    }

    public CompletionStage<Result> update(Http.Request request, String name) {
        JsonNode json = request.body().asJson();
        Brand entity = Json.fromJson(json, Brand.class);
        return repository.update(name, entity).thenApplyAsync(result -> result.map(value -> ok(Json.toJson(value))).orElseGet(Results::notFound), ec.current());
    }

    public CompletionStage<Result> create(Http.Request request) {
        JsonNode json = request.body().asJson();
        Brand entity = Json.fromJson(json, Brand.class);
        return repository.create(entity).thenApplyAsync(result -> created(Json.toJson(result)), ec.current());
    }
}
