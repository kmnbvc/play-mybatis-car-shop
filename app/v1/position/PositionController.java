package v1.position;

import play.libs.concurrent.HttpExecutionContext;
import v1.common.EntityController;

import javax.inject.Inject;
import java.util.UUID;

public class PositionController extends EntityController<Position, UUID> {
    @Inject
    public PositionController(PositionRepository repository, HttpExecutionContext ec) {
        super(repository, ec, Position.class, UUID.class);
    }
}
