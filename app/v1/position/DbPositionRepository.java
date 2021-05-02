package v1.position;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Optional;
import java.util.UUID;
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
        return null;
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
    public CompletionStage<Boolean> delete(UUID uuid) {
        return null;
    }
}
