package v1.search;

import play.mvc.QueryStringBindable;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;

public class SearchCriteria implements QueryStringBindable<SearchCriteria> {

    private String brandName;
    private String modelName;
    private Integer priceMax;
    private Integer priceMin;

    public SearchCriteria() {
    }

    public SearchCriteria(String brandName, String modelName, Integer priceMax, Integer priceMin) {
        this.brandName = brandName;
        this.modelName = modelName;
        this.priceMax = priceMax;
        this.priceMin = priceMin;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public Integer getPriceMax() {
        return priceMax;
    }

    public void setPriceMax(Integer priceMax) {
        this.priceMax = priceMax;
    }

    public Integer getPriceMin() {
        return priceMin;
    }

    public void setPriceMin(Integer priceMin) {
        this.priceMin = priceMin;
    }

    @Override
    public Optional<SearchCriteria> bind(String key, Map<String, String[]> data) {
        if (data.containsKey("brandName")) {
            setBrandName(data.get("brandName")[0]);
        }
        if (data.containsKey("modelName")) {
            setModelName(data.get("modelName")[0]);
        }
        if (data.containsKey("priceMax")) {
            setPriceMax(parseIntQueryParam(data.get("priceMax")[0]));
        }
        if (data.containsKey("priceMin")) {
            setPriceMin(parseIntQueryParam(data.get("priceMin")[0]));
        }
        return Optional.of(this);
    }

    @Override
    public String unbind(String key) {
        Stream<Map.Entry<String, Object>> values = Stream.of(
                Map.entry("brandName", brandName),
                Map.entry("modelName", modelName),
                Map.entry("priceMax", priceMax),
                Map.entry("priceMin", priceMin));

        return values.filter(e -> e.getValue() != null)
                .map(e -> e.getKey() + "=" + e.getValue())
                .collect(joining("&"));
    }

    @Override
    public String javascriptUnbind() {
        return unbind("");
    }

    private Integer parseIntQueryParam(String str) {
        try {
            return Integer.valueOf(str);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
