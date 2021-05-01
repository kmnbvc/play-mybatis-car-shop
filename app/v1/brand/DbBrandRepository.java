package v1.brand;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.stream.Stream;

@Singleton
public class DbBrandRepository implements BrandRepository {

    private final BrandExecutionContext ec;
    private List<Brand> values = new ArrayList<>();

    @Inject
    public DbBrandRepository(BrandExecutionContext ec) {
        this.ec = ec;
    }

    @Override
    public CompletionStage<Stream<Brand>> list() {
        return CompletableFuture.completedStage(values.stream());
    }

    @Override
    public CompletionStage<Brand> create(Brand brand) {
        values.add(brand);
        return CompletableFuture.completedStage(brand);
    }

    @Override
    public CompletionStage<Optional<Brand>> get(String name) {
        return CompletableFuture.completedStage(values.stream().filter(brand -> brand.getName().equals(name)).findFirst());
    }

    @Override
    public CompletionStage<Optional<Brand>> update(String name, Brand brand) {
        return get(name);
    }

}
