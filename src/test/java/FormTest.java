import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;

import org.openqa.selenium.Keys;


import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.*;


public class FormTest {
    private String genDate(int days, String date) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern(date));
    }

    @Test
    void formSendSuccess() {
        open("http://localhost:9999/");
        String formDate = genDate(5, "dd.MM.yyyy");
        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").sendKeys(Keys.SHIFT, Keys.HOME, Keys.DELETE);
        $("[data-test-id='date'] input").setValue(formDate);
        $("[data-test-id='name'] input").setValue("Иванов Иван");
        $("[data-test-id='phone'] input").setValue("+71231231212");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $(".notification__content")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Встреча успешно забронирована на " + formDate));
    }
}
