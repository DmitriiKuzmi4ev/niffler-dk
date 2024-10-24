package guru.qa.niffler.test;

import com.codeborne.selenide.Selenide;
import guru.qa.niffler.jupiter.User;
import guru.qa.niffler.model.UserJson;
import io.qameta.allure.Allure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$x;
import static guru.qa.niffler.jupiter.User.UserType.INVITE_RECEIVED;

public class InvitationWebTest extends BaseWebTest {

    @BeforeEach
    void doLogin(@User(userType = INVITE_RECEIVED) UserJson userForTest) {
        Selenide.open("http://127.0.0.1:3000/");
        $("input[name='username']").setValue(userForTest.username());
        $("input[name='password']").setValue(userForTest.password());
        $(".form__submit").click();
    }

    @Test
    void checkThatUserHasInvite1(@User(userType = INVITE_RECEIVED) UserJson userJson){
        Allure.step("Click on profile",
                () -> $("button[aria-label='Menu']").click());

        Allure.step("Click on Friends",
                () -> $(".MuiMenu-list")
                        .$$("li").find(text("Friends")).click());

        Allure.step("Check there Accept is showing",
                () -> $$x("//tr[contains(@class,'MuiTableRow')]")
                        .first()
                        .$(byText("Accept"))
                        .shouldBe(exist));
    }

    @Test
    void checkThatUserHasInvite2(@User(userType = INVITE_RECEIVED) UserJson userJson){
        Allure.step("Click on profile",
                () -> $("button[aria-label='Menu']").click());

        Allure.step("Click on Friends",
                () -> $(".MuiMenu-list")
                        .$$("li").find(text("Friends")).click());

        Allure.step("Check there Decline is showing",
                () -> $$x("//tr[contains(@class,'MuiTableRow')]")
                        .first()
                        .$(byText("Decline"))
                        .shouldBe(exist));
    }

}
