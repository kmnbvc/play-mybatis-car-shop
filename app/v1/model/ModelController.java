package v1.model;

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

public class ModelController extends Controller {

    private final HttpExecutionContext ec;
    private final ModelRepository repository;

    @Inject
    public ModelController(HttpExecutionContext ec, ModelRepository repository) {
        this.ec = ec;
        this.repository = repository;
    }

    public CompletionStage<Result> list() {
        return repository.list().thenApplyAsync(values -> ok(Json.toJson(values.collect(toList()))));
    }

    public CompletionStage<Result> get(String name) {
        return repository.get(name).thenApplyAsync(result -> result.map(value -> ok(Json.toJson(value))).orElseGet(Results::notFound), ec.current());
    }

    public CompletionStage<Result> update(Http.Request request, String name) {
        JsonNode json = request.body().asJson();
        Model entity = Json.fromJson(json, Model.class);
        return repository.update(name, entity).thenApplyAsync(result -> result.map(value -> ok(Json.toJson(value))).orElseGet(Results::notFound), ec.current());
    }

    public CompletionStage<Result> create(Http.Request request) {
        JsonNode json = request.body().asJson();
        Model entity = Json.fromJson(json, Model.class);
        return repository.create(entity).thenApplyAsync(result -> created(Json.toJson(result)), ec.current());
    }
}
