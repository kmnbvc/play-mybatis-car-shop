package v1.brand;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.stream.Stream;

@Singleton
public class DbBrandRepository implements BrandRepository {

    private final BrandExecutionContext ec;
    private final BrandMapper mapper;

    @Inject
    public DbBrandRepository(BrandExecutionContext ec, BrandMapper mapper) {
        this.ec = ec;
        this.mapper = mapper;
    }

    @Override
    public CompletionStage<Stream<Brand>> list() {
        return CompletableFuture.supplyAsync(() -> mapper.list().stream(), ec.current());
    }

    @Override
    public CompletionStage<Optional<Brand>> get(String name) {
        return CompletableFuture.supplyAsync(() -> Optional.ofNullable(mapper.get(name)), ec.current());
    }

    @Override
    public CompletionStage<Brand> create(Brand brand) {
        return CompletableFuture.supplyAsync(() -> {
            mapper.insert(brand);
            return brand;
        }, ec.current());
    }

    @Override
    public CompletionStage<Optional<Brand>> update(String name, Brand brand) {
        return CompletableFuture.supplyAsync(() -> {
            int rows = mapper.update(name, brand);
            return rows > 0 ? Optional.of(brand) : Optional.empty();
        }, ec.current());
    }

    @Override
    public CompletionStage<Boolean> delete(String name) {
        return CompletableFuture.supplyAsync(() -> {
            int rows = mapper.delete(name);
            return rows > 0;
        }, ec.current());
    }

}
