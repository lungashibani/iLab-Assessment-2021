package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

public class glob_HudPage extends BasePage{

    @FindBy(xpath = "//*[@data-testid='universalPlus']")
    public WebElement universalPlusIcon;

    @FindBy(xpath = "//*[@data-testid='createCandidate']")
    public WebElement universalPlusIcon_CreateCandidate;

    @FindBy(xpath = "//*[@data-testid='createClient Contact']")
    public WebElement universalPlusIcon_CreateClientContact;

    @FindBy(xpath = "//*[@data-cy='created_candidate_snackbar']")
    public WebElement notification_CreatedCandidate;

    @FindBy(xpath = "//*[text()='View']")
    public WebElement notification_ViewRecordButton;

    //#####################SEARCH BUTTON#############################
    @FindBy(xpath = "//button[@data-testid='openSearch']")
    @CacheLookup
    public WebElement btnOpenSearch;

    @FindBy(xpath = "//input[@type='search']")
    @CacheLookup
    public WebElement txtSearchField;

    @FindBy(id = "scrollable-auto-tab-Candidates")
    @CacheLookup
    public WebElement btnScrollableCandidates;

    @FindBy(id = "scrollable-auto-tab-Client Contacts")
    @CacheLookup
    public WebElement btnScrollableClientContacts;

    @FindBy(id = "scrollable-auto-tab-Organisations")
    @CacheLookup
    public WebElement btnScrollableOrganisation;

    @FindBy(id = "scrollable-auto-tab-Jobs")
    @CacheLookup
    public WebElement btnScrollableJobs;




    // This element looks like a button or box depending on the dimension of the window
    // Icon Button when < 1440
    // Search Box when >= 1440
    @FindBy(xpath = "//*[@data-cy='searchBox']")
    public WebElement searchButtonOrBox;

    @FindBy(xpath = "//*[@data-testid='createOrganisation']")
    public WebElement universalPlusIcon_CreateOrganisation;

    @FindBy(xpath = "//*[@data-testid='createJob']")
    public WebElement universalPlusIcon_CreateJob;

    @FindBy(xpath = "//*[@data-testid='createList']")
    public WebElement universalPlusIcon_CreateList;


    @FindBy(xpath = "//button[@data-testid='open-menu']")
    @CacheLookup
    public WebElement btnOpenMenu;

    ////a[@data-testid='jobsMenuItem']
    @FindBy(xpath = "//span[contains(text(), 'Jobs')]")
    public WebElement linkMyJobs;

    @FindBy(xpath = "//a[@data-testid='candidatesListsLink']")
    public WebElement candidateList;


    @FindBy(xpath = "//a[@data-testid='allClientsLink']")
    public WebElement allClientContacts;


    @FindBy(xpath = "//a[@data-testid='clientsListsLink']")
    public WebElement clientList;

    @FindBy(xpath = "//button[@data-testid='addToButton']")
    public WebElement btnClientContactPlusTop;

    @FindBy(xpath = "//button[@data-testid='ClientActivities_add_button']")
    public WebElement btnActivityPlus;

    @FindBy(xpath = "//button[@data-testid='ClientActivities_add_button']")
    public WebElement btnClientContactActivityPlus;


    @FindBy(xpath = "//a[@data-testid='allOrganisationsLink']")
    public WebElement allOrganisations;

    @FindBy(xpath = "//a[@data-testid='organisationsListsLink']")
    public WebElement OrganisationList;


    @FindBy(xpath = "//*[@data-cy='created_organisation_snackbar']")
    public WebElement notification_CreatedOrganisation;

    @FindBy(xpath = "//button[@data-testid='dropdown']")
    public WebElement status_Candidate;

    @FindBy(xpath = "//button[@data-testid='addToButton']")
    public WebElement btnTopPlus;

    @FindBy(xpath = "//h6[contains(text(), 'Add to list')]")
    public WebElement btnAddToList;





    public glob_HudPage(WebDriver newDriver, WebDriverWait newWait) {
        super(newDriver, newWait);
    }
}
