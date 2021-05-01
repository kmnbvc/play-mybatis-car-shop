package v1.common;

import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.stream.Stream;

public interface EntityRepository<T, ID> {
    CompletionStage<Stream<T>> list();
    CompletionStage<T> create(T entity);
    CompletionStage<Optional<T>> get(ID id);
    CompletionStage<Optional<T>> update(ID id, T entity);
    CompletionStage<Optional<T>> delete(ID id);
}
