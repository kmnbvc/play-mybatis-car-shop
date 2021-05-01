package v1.common;

import com.fasterxml.jackson.databind.JsonNode;
import play.libs.Json;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Results;

import java.util.concurrent.CompletionStage;

import static java.util.stream.Collectors.toList;

public abstract class EntityController<T, ID> extends Controller {

    protected final HttpExecutionContext ec;
    protected final EntityRepository<T, ID> repository;
    protected final Class<T> entityType;

    public EntityController(EntityRepository<T, ID> repository, HttpExecutionContext ec, Class<T> entityType, Class<ID> idType) {
        this.repository = repository;
        this.ec = ec;
        this.entityType = entityType;
    }

    public CompletionStage<Result> list() {
        return repository.list().thenApplyAsync(values -> ok(Json.toJson(values.collect(toList()))));
    }

    public CompletionStage<Result> get(ID id) {
        return repository.get(id).thenApplyAsync(result -> result.map(value -> ok(Json.toJson(value))).orElseGet(Results::notFound), ec.current());
    }

    public CompletionStage<Result> update(Http.Request request, ID id) {
        JsonNode json = request.body().asJson();
        T entity = Json.fromJson(json, entityType);
        return repository.update(id, entity).thenApplyAsync(result -> result.map(value -> ok(Json.toJson(value))).orElseGet(Results::notFound), ec.current());
    }

    public CompletionStage<Result> create(Http.Request request) {
        JsonNode json = request.body().asJson();
        T entity = Json.fromJson(json, entityType);
        return repository.create(entity).thenApplyAsync(result -> created(Json.toJson(result)), ec.current());
    }

    public CompletionStage<Result> delete(ID id) {
        return repository.delete(id).thenApplyAsync(result -> result.map(value -> ok(Json.toJson(value))).orElseGet(Results::notFound), ec.current());
    }
}
