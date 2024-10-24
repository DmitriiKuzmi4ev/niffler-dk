package guru.qa.niffler.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.Date;
import java.util.UUID;

@Builder
public record SpendJson(
        @JsonProperty("id")
        UUID id,
        @JsonProperty("spendDate")
        Date spendDate,
        @JsonProperty("category")
        CategoryJson category,
        @JsonProperty("currency")
        CurrencyValues currency,
        @JsonProperty("amount")
        Double amount,
        @JsonProperty("description")
        String description,
        @JsonProperty("username")
        String username){

}
