import com.google.inject.AbstractModule;
import v1.brand.BrandRepository;
import v1.brand.DbBrandRepository;
import v1.common.MyBatisModule;
import v1.model.DbModelRepository;
import v1.model.ModelRepository;
import v1.position.DbPositionRepository;
import v1.position.PositionRepository;

public class Module extends AbstractModule {

    @Override
    public void configure() {
        bind(BrandRepository.class).to(DbBrandRepository.class).asEagerSingleton();
        bind(ModelRepository.class).to(DbModelRepository.class).asEagerSingleton();
        bind(PositionRepository.class).to(DbPositionRepository.class).asEagerSingleton();
        install(new MyBatisModule());
    }
}
