package guru.qa.niffler.test;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Selenide;
import guru.qa.niffler.jupiter.User;
import guru.qa.niffler.model.UserJson;
import io.qameta.allure.Allure;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$x;
import static guru.qa.niffler.jupiter.User.UserType.FRIEND;

public class FriendsWebTest extends BaseWebTest {

    @BeforeEach
    void doLogin(@User(userType = FRIEND) UserJson userForTest) {
        Selenide.open("http://127.0.0.1:3000/");
        $("input[name='username']").setValue(userForTest.username());
        $("input[name='password']").setValue(userForTest.password());
        $(".form__submit").click();
    }

    @Test
    void friendsShouldBeDisplayedInTable(@User(userType = FRIEND) UserJson userForTest) throws InterruptedException {

        Allure.step("Click on profile",
                () -> $("button[aria-label='Menu']").click());

        Allure.step("Click on Friends",
                () -> $(".MuiMenu-list")
                        .$$("li").find(text("Friends")).click());

        var friend = $$x("//tr[contains(@class,'MuiTableRow')]").first();

        Allure.step("Check that friends are showing",
                () -> Assertions.assertTrue(
                        friend.getText().contains(
                                (userForTest.username()
                                        .equalsIgnoreCase("admin"))
                                        ? "admin"
                                        : "admin1")
                ));

    }

}
