package v1.search;

import play.libs.Json;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.concurrent.CompletionStage;

import static java.util.stream.Collectors.toList;

public class SearchController extends Controller {

    private final SearchService service;
    private final HttpExecutionContext ec;

    @Inject
    public SearchController(HttpExecutionContext ec, SearchService service) {
        this.ec = ec;
        this.service = service;
    }

    public CompletionStage<Result> search(SearchCriteria criteria) {
        return service.search(criteria).thenApplyAsync(result -> ok(Json.toJson(result.collect(toList()))), ec.current());
    }
}
