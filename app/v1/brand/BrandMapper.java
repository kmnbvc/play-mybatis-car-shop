package v1.brand;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BrandMapper {
    List<Brand> list();
    Brand get(@Param("name") String name);
    int insert(@Param("brand") Brand brand);
    int update(@Param("name") String name, @Param("brand") Brand brand);
    int delete(@Param("name") String name);
}
