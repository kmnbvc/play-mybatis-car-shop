package v1.model;

import v1.common.AbstractDbRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DbModelRepository extends AbstractDbRepository<Model, String> implements ModelRepository {
    @Inject
    public DbModelRepository(ModelExecutionContext ec, ModelMapper mapper) {
        super(ec, mapper);
    }
}
