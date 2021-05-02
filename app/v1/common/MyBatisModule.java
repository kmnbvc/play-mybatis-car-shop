package v1.common;


import com.google.inject.name.Names;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import play.db.Database;
import v1.brand.BrandMapper;
import v1.model.ModelMapper;
import v1.position.PositionMapper;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import javax.sql.DataSource;
import java.time.Year;
import java.util.List;
import java.util.UUID;

public class MyBatisModule extends org.mybatis.guice.MyBatisModule {

    @Override
    protected void initialize() {
        environmentId("development");
        bindConstant().annotatedWith(Names.named("mybatis.configuration.failFast")).to(true);
        bindDataSourceProviderType(PlayDataSourceProvider.class);
        bindTransactionFactoryType(JdbcTransactionFactory.class);
        addTypeHandlerClass(UUIDTypeHandler.class);
        addSimpleAlias(UUIDTypeHandler.class);
        addSimpleAlias(UUID.class);
        addSimpleAlias(Year.class);
        addMapperClasses(List.of(BrandMapper.class, ModelMapper.class, PositionMapper.class));
    }

    @Singleton
    public static class PlayDataSourceProvider implements Provider<DataSource> {
        private final Database db;

        @Inject
        public PlayDataSourceProvider(Database db) {
            this.db = db;
        }

        @Override
        public DataSource get() {
            return db.getDataSource();
        }
    }
}
