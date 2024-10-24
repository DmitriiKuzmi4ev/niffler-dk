package guru.qa.niffler.jupiter;

import guru.qa.niffler.api.SpendService;
import guru.qa.niffler.model.CategoryJson;
import guru.qa.niffler.model.SpendJson;
import okhttp3.OkHttpClient;
import org.junit.jupiter.api.extension.*;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.util.Date;

public class SpendExtension implements BeforeEachCallback {

    public static ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace.create(SpendExtension.class);

    private static final OkHttpClient httpClient = new OkHttpClient().newBuilder().build();
    private static final Retrofit retrofit = new Retrofit.Builder()
            .client(httpClient)
            .baseUrl("http://127.0.0.1:8093")
            .addConverterFactory(JacksonConverterFactory.create())
            .build();

    private final SpendService spendService = retrofit.create(SpendService.class);


    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        Spend spend = context.getRequiredTestMethod().getAnnotation(Spend.class);
        if (spend != null) {
            SpendJson json = SpendJson.builder()
                    .username(spend.username())
                    .description(spend.description())
                    .amount(spend.amount())
                    .spendDate(new Date())
                    .currency(spend.currency())
                    .category(CategoryJson.builder()
                            .name(spend.category())
                            .username(spend.username())
                            .build())
                    .build();

            SpendJson createdSpend = spendService.addSpend(json).execute().body();
            context.getStore(NAMESPACE).put("spend", createdSpend);
        }
    }
}
