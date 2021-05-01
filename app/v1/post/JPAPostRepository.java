package v1.post;

import net.jodah.failsafe.CircuitBreaker;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.stream.Stream;

@Singleton
public class JPAPostRepository implements PostRepository {

    private final PostExecutionContext ec;
    private final CircuitBreaker<Optional<PostData>> circuitBreaker = new CircuitBreaker<Optional<PostData>>().withFailureThreshold(1).withSuccessThreshold(3);

    @Inject
    public JPAPostRepository(PostExecutionContext ec) {
        this.ec = ec;
    }

    @Override
    public CompletionStage<Stream<PostData>> list() {
        return null;
    }

    @Override
    public CompletionStage<PostData> create(PostData postData) {
        return null;
    }

    @Override
    public CompletionStage<Optional<PostData>> get(Long id) {
        return null;
    }

    @Override
    public CompletionStage<Optional<PostData>> update(Long id, PostData postData) {
        return null;
    }
}
