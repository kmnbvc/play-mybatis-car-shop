package v1.search;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.stream.Stream;

@Singleton
public class SearchService {

    private final SearchExecutionContext ec;
    private final SearchMapper mapper;

    @Inject
    public SearchService(SearchExecutionContext ec, SearchMapper mapper) {
        this.ec = ec;
        this.mapper = mapper;
    }

    public CompletionStage<Stream<SearchResult>> search(SearchCriteria criteria) {
        return CompletableFuture.supplyAsync(() -> mapper.search(criteria).stream(), ec);
    }
}
