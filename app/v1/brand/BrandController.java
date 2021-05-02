package v1.brand;

import play.libs.concurrent.HttpExecutionContext;
import v1.common.EntityController;

import javax.inject.Inject;

public class BrandController extends EntityController<Brand, String> {
    @Inject
    public BrandController(BrandRepository repository, HttpExecutionContext ec) {
        super(repository, ec, Brand.class, String.class);
    }
}
