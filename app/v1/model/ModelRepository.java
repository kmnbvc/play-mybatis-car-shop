package v1.model;

import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.stream.Stream;

public interface ModelRepository {
    CompletionStage<Stream<Model>> list();
    CompletionStage<Model> create(Model model);
    CompletionStage<Optional<Model>> get(String name);
    CompletionStage<Optional<Model>> update(String name, Model model);
}
