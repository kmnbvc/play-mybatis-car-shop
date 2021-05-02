package v1.common;

import play.libs.concurrent.CustomExecutionContext;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.stream.Stream;

public abstract class AbstractDbRepository<T, ID> implements EntityRepository<T, ID> {

    protected final CustomExecutionContext ec;
    protected final EntityMapper<T, ID> mapper;

    public AbstractDbRepository(CustomExecutionContext ec, EntityMapper<T, ID> mapper) {
        this.ec = ec;
        this.mapper = mapper;
    }

    @Override
    public CompletionStage<Stream<T>> list() {
        return CompletableFuture.supplyAsync(() -> mapper.list().stream(), ec.current());
    }

    @Override
    public CompletionStage<Optional<T>> get(ID id) {
        return CompletableFuture.supplyAsync(() -> Optional.ofNullable(mapper.get(id)), ec.current());
    }

    @Override
    public CompletionStage<T> create(T entity) {
        return CompletableFuture.supplyAsync(() -> {
            mapper.insert(entity);
            return entity;
        }, ec.current());
    }

    @Override
    public CompletionStage<Optional<T>> update(ID id, T entity) {
        return CompletableFuture.supplyAsync(() -> {
            int rows = mapper.update(id, entity);
            return rows > 0 ? Optional.of(entity) : Optional.empty();
        }, ec.current());
    }

    @Override
    public CompletionStage<Boolean> delete(ID id) {
        return CompletableFuture.supplyAsync(() -> {
            int rows = mapper.delete(id);
            return rows > 0;
        }, ec.current());
    }

}
