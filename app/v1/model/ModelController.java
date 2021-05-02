package v1.model;

import play.libs.concurrent.HttpExecutionContext;
import v1.common.EntityController;

import javax.inject.Inject;

public class ModelController extends EntityController<Model, String> {
    @Inject
    public ModelController(ModelRepository repository, HttpExecutionContext ec) {
        super(repository, ec, Model.class, String.class);
    }
}
