import com.google.inject.AbstractModule;
import v1.brand.BrandRepository;
import v1.brand.DbBrandRepository;

public class Module extends AbstractModule {

    @Override
    public void configure() {
        bind(BrandRepository.class).to(DbBrandRepository.class).asEagerSingleton();
    }
}
