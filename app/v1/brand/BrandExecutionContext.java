package v1.brand;

import akka.actor.ActorSystem;
import play.libs.concurrent.CustomExecutionContext;

import javax.inject.Inject;

public class BrandExecutionContext extends CustomExecutionContext {

    @Inject
    public BrandExecutionContext(ActorSystem actorSystem) {
        super(actorSystem, "brand.repository");
    }

}
