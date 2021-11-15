package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

abstract public class BasePage {
    WebDriver webDriver;
    WebDriverWait wait;

    public BasePage(WebDriver newDriver, WebDriverWait newWait) {
        webDriver = newDriver;
        wait = newWait;
        PageFactory.initElements(webDriver, this);
    }

    public void WaitForElementToBeVisible(WebElement element){
        wait.until(ExpectedConditions.visibilityOf(element));
    }
}
