package v1.position;

import v1.brand.Brand;
import v1.model.Model;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.time.Year;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.stream.Stream;

@Singleton
public class DbPositionRepository implements PositionRepository {

    private final PositionExecutionContext ec;

    @Inject
    public DbPositionRepository(PositionExecutionContext ec) {
        this.ec = ec;
    }

    @Override
    public CompletionStage<Stream<Position>> list() {
        return CompletableFuture.completedStage(Stream.of(new Position(new Brand("vaz", "ru"), new Model("210045", Year.of(2222), null), Year.of(2221), 123, 45535)));
    }

    @Override
    public CompletionStage<Position> create(Position entity) {
        return null;
    }

    @Override
    public CompletionStage<Optional<Position>> get(UUID uuid) {
        return null;
    }

    @Override
    public CompletionStage<Optional<Position>> update(UUID uuid, Position entity) {
        return null;
    }

    @Override
    public CompletionStage<Optional<Position>> delete(UUID uuid) {
        return null;
    }
}
