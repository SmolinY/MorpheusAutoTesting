package MorpheusAutoTesting.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.TimeoutException;
import ru.yandex.qatools.htmlelements.element.TextInput;
import ru.yandex.qatools.htmlelements.element.Button;

import java.util.concurrent.TimeUnit;

public class Login extends ViewPage {
    public Login(WebDriver driver){
        super(driver, "login-view");
    }

    @FindBy(xpath = "//input[../../../tr/td[text()='Login'] and @type='text' and @placeholder='Username']")
    private TextInput usernameInput;

    @FindBy(xpath = "//input[../../../tr/td[text()='Password'] and @type='password' and @placeholder='Password']")
    private TextInput passwordInput;

    @FindBy(xpath = "//div[span[text()='Log In']]")
    private Button loginBtn;

    @FindBy(id = "error-view")
    public WebElement error;

    public boolean authorization(String username, String password){
        usernameInput.sendKeys(username);
        passwordInput.sendKeys(password);
        loginBtn.click();
        try {
            wait.withTimeout(2, TimeUnit.SECONDS).until(ExpectedConditions.visibilityOf(error));
            return !error.isDisplayed();
        }
        catch (TimeoutException e){
            return true;
        }
    }
}
