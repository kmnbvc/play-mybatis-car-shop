package v1.position;

import akka.actor.ActorSystem;
import play.libs.concurrent.CustomExecutionContext;

import javax.inject.Inject;

public class PositionExecutionContext extends CustomExecutionContext {
    @Inject
    public PositionExecutionContext(ActorSystem actorSystem) {
        super(actorSystem, "position.repository");
    }
}
