package v1.search;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SearchMapper {
    List<SearchResult> search(@Param("criteria") SearchCriteria criteria);
}
