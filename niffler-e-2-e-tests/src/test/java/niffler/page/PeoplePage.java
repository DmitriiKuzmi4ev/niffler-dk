package niffler.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import niffler.page.component.PeopleTable;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class PeoplePage extends BasePage<PeoplePage> {

    public static final String URL = CFG.frontUrl() + "people";

    private final SelenideElement tableContainer = $(".people-content");
    private final PeopleTable table = new PeopleTable($(".table"));

    @Override
    public PeoplePage waitForPageLoaded() {
        tableContainer.shouldBe(Condition.visible);
        return this;
    }

    @Step("Send invitation to user: {username}")
    public PeoplePage sendFriendInvitationToUser(String username) {
        SelenideElement friendRow = table.getRowByUsername(username);
        SelenideElement actionsCell = table.getActionsCell(friendRow);
        actionsCell.$(".button-icon_type_add")
                .click();
        return this;
    }

    @Step("Send invitation status for user: {username}")
    public PeoplePage checkInvitationSentToUser(String username) {
        SelenideElement friendRow = table.getRowByUsername(username);
        SelenideElement actionsCell = table.getActionsCell(friendRow);
        actionsCell.shouldHave(text("Pending invitation"));
        return this;
    }

}
