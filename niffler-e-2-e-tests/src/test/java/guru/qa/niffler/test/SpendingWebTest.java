package guru.qa.niffler.test;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import guru.qa.niffler.jupiter.Spend;
import guru.qa.niffler.jupiter.User;
import guru.qa.niffler.model.CurrencyValues;
import guru.qa.niffler.model.SpendJson;
import guru.qa.niffler.model.UserJson;
import io.qameta.allure.Allure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static guru.qa.niffler.jupiter.User.UserType.FRIEND;

public class SpendingWebTest extends BaseWebTest {

    @BeforeEach
    void doLogin(@User(userType = FRIEND) UserJson userForTest) {
        Selenide.open("http://127.0.0.1:3000/");
        $("input[name='username']").setValue(userForTest.username());
        $("input[name='password']").setValue(userForTest.password());
        $(".form__submit").click();
    }

    @Spend(
            username = "admin",
            description = "Рыбалка на Волге",
            category = "Рыбалка",
            amount = 10000.00,
            currency = CurrencyValues.RUB
    )
    @Test
    void deletedSpendingAfterDeleteActions(SpendJson spendJson, @User(userType = FRIEND) UserJson userForTest) {
        Allure.step("Find spend by description",
                () -> $(".MuiTableBody-root")
                        .$$("tr")
                        .find(text(spendJson.description()))
                        .$$("td")
                        .first()
                        .click());

        Allure.step("Click on Delete button",
                () -> $("#delete").click());

        Allure.step("Confirm deleting spend",
                () -> $(".MuiDialog-paperScrollPaper")
                        .shouldBe(Condition.visible)
                        .$(byText("Delete")).click());

        Allure.step("Check spending deleted",
                () -> $(".MuiTableBody-root")
                        .$$("tr")
                        .shouldHave(CollectionCondition.size(0)));

    }
}
