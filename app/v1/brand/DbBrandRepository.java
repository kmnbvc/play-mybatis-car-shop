package v1.brand;

import v1.common.AbstractDbRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DbBrandRepository extends AbstractDbRepository<Brand, String> implements BrandRepository {
    @Inject
    public DbBrandRepository(BrandExecutionContext ec, BrandMapper mapper) {
        super(ec, mapper);
    }
}
