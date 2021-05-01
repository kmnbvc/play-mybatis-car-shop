package v1.brand;

import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.stream.Stream;

public interface BrandRepository {
    CompletionStage<Stream<Brand>> list();
    CompletionStage<Brand> create(Brand brand);
    CompletionStage<Optional<Brand>> get(String name);
    CompletionStage<Optional<Brand>> update(String name, Brand brand);
}
