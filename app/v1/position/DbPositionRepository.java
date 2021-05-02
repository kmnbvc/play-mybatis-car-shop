package v1.position;

import v1.common.AbstractDbRepository;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.UUID;

@Singleton
public class DbPositionRepository extends AbstractDbRepository<Position, UUID> implements PositionRepository {
    @Inject
    public DbPositionRepository(PositionExecutionContext ec, PositionMapper mapper) {
        super(ec, mapper);
    }
}
