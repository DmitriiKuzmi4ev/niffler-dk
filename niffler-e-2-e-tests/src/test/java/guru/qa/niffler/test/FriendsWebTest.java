package guru.qa.niffler.test;

import com.codeborne.selenide.Selenide;
import guru.qa.niffler.jupiter.User;
import guru.qa.niffler.model.UserJson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.$;
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
    void friendsShouldBeDisplayedInTable0(@User(userType = FRIEND) UserJson userForTest) throws InterruptedException {
        Thread.sleep(3000);
    }

    @Test
    void friendsShouldBeDisplayedInTable1(@User(userType = FRIEND) UserJson userForTest) throws InterruptedException {
        Thread.sleep(3000);
    }

    @Test
    void friendsShouldBeDisplayedInTable2(@User(userType = FRIEND) UserJson userForTest) throws InterruptedException {
    }

}
