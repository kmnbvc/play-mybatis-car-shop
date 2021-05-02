package v1.model;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.stream.Stream;

@Singleton
public class DbModelRepository implements ModelRepository {

    private final ModelExecutionContext ec;

    @Inject
    public DbModelRepository(ModelExecutionContext ec) {
        this.ec = ec;
    }

    @Override
    public CompletionStage<Stream<Model>> list() {
        return null;
    }

    @Override
    public CompletionStage<Model> create(Model entity) {
        return null;
    }

    @Override
    public CompletionStage<Optional<Model>> get(String s) {
        return null;
    }

    @Override
    public CompletionStage<Optional<Model>> update(String s, Model entity) {
        return null;
    }

    @Override
    public CompletionStage<Boolean> delete(String s) {
        return null;
    }
}
