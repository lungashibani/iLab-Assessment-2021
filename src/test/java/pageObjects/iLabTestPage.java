package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

public class iLabTestPage extends BasePage{

    @FindBy(xpath = "//li[@id='menu-item-1373']")
    public WebElement lnkCareerTab;

    @FindBy(xpath = "/html/body/section/div[2]/div/div/div/div[3]/div[2]/div/div/div[3]/div[2]/div/div/div[4]/a")
    public WebElement lnkSouthAfrica;

    @FindBy(xpath = "//a[contains(text(), 'Interns - BSC Computer Science, National Diploma: IT Development Graduates')]")
    public WebElement jobHyperlink;

    @FindBy(xpath = "//a[contains(text(), 'Apply Online ')]")
    public WebElement btnApplyOnline;

    @FindBy(name = "applicant_name")
    public WebElement txtApplicantName;

    @FindBy(id = "email")
    public WebElement txtEmail;

    @FindBy(id = "phone")
    public WebElement txtPhoneNumber;

    @FindBy(id = "message")
    public WebElement txtMessage;


    @FindBy(id = "wpjb_submit")
    public WebElement btnSendApplication;




    public iLabTestPage(WebDriver newDriver, WebDriverWait newWait) {
        super(newDriver, newWait);
    }
}
