package v1.common;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EntityMapper<T, ID> {
    List<T> list();
    T get(@Param("id") ID id);
    int insert(@Param("entity") T entity);
    int update(@Param("id") ID id, @Param("entity") T entity);
    int delete(@Param("id") ID id);
}
