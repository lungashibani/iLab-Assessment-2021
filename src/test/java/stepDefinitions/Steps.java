package stepDefinitions;

import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.hotkey.Keys;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import pageObjects.UsefulMethods;
import pageObjects.glob_LoginPage;
import pageObjects.iLabTestPage;
//import sun.font.SunFontManager;


public class Steps {

    public static WebDriver driver;
    public static WebDriverWait wait;
    public static Actions actions;
    public glob_LoginPage glob_loginPage;
    public iLabTestPage ilabtestpage;




    @Before
    public void doSomethingBefore(Scenario scenario) {
        if(System.getProperty("env") == null){
            System.setProperty("env", "sit");
        }
        System.out.println("Environment set to: " + System.getProperty("env"));
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "//Drivers/chromedriver_v102.exe");
        //System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "//Drivers/geckodriver_v1.exe");
        //System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "//Drivers/selenium-ie-driver.exe");
        //System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "//Drivers/operadriver.exe");
        driver = new ChromeDriver();


        //driver = new FirefoxDriver();
        //driver = new InternetExplorerDriver();
        //driver = new OperaDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, 30);
        actions = new Actions(driver);
        glob_loginPage = new glob_LoginPage(driver, wait);
        ilabtestpage = new iLabTestPage(driver, wait);




    }

    @After
    public void doSomethingAfter(Scenario scenario) {
        if (scenario.isFailed()) {
            // Take a screenshot...
            final byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            // embed it in the report.
            scenario.attach(screenshot, "image/png",scenario.getName());
        }
        if (driver != null) {
            driver.quit();
        }
    }

    @Given("User launch Chrome browser")
    public void user_launch_chrome_browser() {
        //System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "//Driver/chromedriver_v95.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        driver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    /*
        THIS IS JUST FOR EXPERIMENTAL PURPOSES
     */

    @Given("I have opened the Login page")
    public void iHaveOpenedTheLoginPage() throws Exception {
        glob_loginPage.OpenPage();
    }


    @When("User opens URL {string}")
    public void user_opens_url(String url) {
        driver.get(url);
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }


    /*
        THIS IS JUST FOR EXPERIMENTAL PURPOSES
     */


    @Given("I have Logged via the backend and stored id token")
    public void iHaveLoggedInViaTheBackEndAndStoredTheIdToken() {

        String baseURL = null;
        String clientID = null;
        if(System.getProperty("env").equals("sit")){
            baseURL = "https://authsit.digitalzenith.io";
            clientID = "0bb1fef9-57a1-4b5d-809b-8e8d873f79d9";
        } else if (System.getProperty("env").equals("dev")) {
            baseURL = "https://authdev.digitalzenith.io";
            clientID = "d13f746b-1ce3-4897-9b07-a73f78809fda";
        }
        RestAssured.baseURI = baseURL;
        Response response =
                given()
                        .params("grant_type", "password")
                        .params("client_id", clientID)
                        .params("loginId", "ckent@digitalzenith.io")
                        .params("password", "Superm@n")
                        .params("scope", "openid")
                        //.body(requestBody)
                        .when()
                        .post("/oauth2/token");

        UsefulMethods.access_token = response.body().jsonPath().getJsonObject("access_token");
        UsefulMethods.id_token = response.body().jsonPath().getJsonObject("id_token");
        Assert.assertEquals(200,response.statusCode());
        System.out.println("1. Login Success Details -----------------------------------------------------------");
        System.out.println(response.getBody().prettyPeek());
        System.out.println("------------------------------------------------------------------------------------");

    }

    @Given("I have deleted Candidate with the family name {string}")
    public void iHaveDeletedCandidateWithTheFamilyName(String familyName) {

        String baseURL = null;
        if(System.getProperty("env").equals("sit")){
            baseURL = "https://apisit.digitalzenith.io";
        } else if (System.getProperty("env").equals("dev")) {
            baseURL = "https://apidev.digitalzenith.io";
        }

        // This class is ued to create the GraphQL Query needed to seach for all Candidates with a specific family name
        // And to return all the ID's
        class GraphQLQuery {

            private String query;

            public void setQuery(String query) {
                this.query = query;
            }

            public String getQuery() {
                return query;
            }
        }

        GraphQLQuery query = new GraphQLQuery();
        query.setQuery("{ ksql_refactor_v_candidate_library {\n" +
                "        Candidates(where: {family_name: {_ilike: \"%"+familyName+"%\"}}) {\n" +
                "          id\n" +
                "        }\n" +
                "      } }");

        // This is currently hardcoded to DEV. A more elegant solution would be to store this somewhere else
        RestAssured.baseURI = baseURL;
        System.out.println("2. GraphQL Query -------------------------------------------------------------");
        System.out.println(query.getQuery());

        System.out.println("------------------------------------------------------------------------------");
        Response response =
                given()
                        .header("Authorization", "Bearer "+UsefulMethods.id_token)
                        .accept(ContentType.JSON)
                        .contentType(ContentType.JSON)
                        .body(query)
                        .when()
                        .post("/query/graphql/v1/graphql");
        System.out.println("3. GraphQL Response ----------------------------------------------------------");
        response.getBody().prettyPrint();
        System.out.println("------------------------------------------------------------------------------");
        Assert.assertEquals(200,response.statusCode());
        // Now we have a list of Candidate ID's
        List<Map<String,String>> candidateListIDs = response.jsonPath().getJsonObject("data.ksql_refactor_v_candidate_library[0].Candidates");

        // For each ID found we run the DELETE api call
        // Run the delete script if any Candidate ID's were found
        System.out.println("4. Records Deleted ---------------------------------------------------------");
        if(!candidateListIDs.equals(null)){
            for(Map<String,String> candidate : candidateListIDs){
                Response response2 =
                        given()
                                .header("Authorization", "Bearer "+UsefulMethods.access_token)
                                .when()
                                .delete("/command/candidates/"+candidate.get("id"));
                assertThat(response2.getStatusCode(),equalTo(202));
                System.out.println("Deleted ID = " + response2.getBody().prettyPrint());
            }
        }else{
            System.out.println("No Candidates found with family name - " + familyName);
        }
        System.out.println("----------------------------------------------------------------------------");
    }

    //#######################################################iLab Test Feature Definition############################################################

    @When("User sends an online application")
    public void user_sends_an_online_application() {

        wait.until(ExpectedConditions.visibilityOf(ilabtestpage.lnkCareerTab));
        ilabtestpage.lnkCareerTab.click();

        wait.until(ExpectedConditions.visibilityOf(ilabtestpage.lnkSouthAfrica));
        ilabtestpage.lnkSouthAfrica.click();

        wait.until(ExpectedConditions.visibilityOf(ilabtestpage.jobHyperlink));
        ilabtestpage.jobHyperlink.click();

        wait.until(ExpectedConditions.visibilityOf(ilabtestpage.btnApplyOnline));
        ilabtestpage.btnApplyOnline.click();

        wait.until(ExpectedConditions.visibilityOf(ilabtestpage.txtApplicantName));
        ilabtestpage.txtApplicantName.sendKeys("John");

        wait.until(ExpectedConditions.visibilityOf(ilabtestpage.txtEmail));
        ilabtestpage.txtEmail.sendKeys("automationAssessment@iLABQuality.com");

        wait.until(ExpectedConditions.visibilityOf(ilabtestpage.txtPhoneNumber));
        ilabtestpage.txtPhoneNumber.sendKeys("084 456 1234");

        wait.until(ExpectedConditions.visibilityOf(ilabtestpage.txtMessage));
        ilabtestpage.txtMessage.sendKeys("Good working environment");

        wait.until(ExpectedConditions.visibilityOf(ilabtestpage.btnSendApplication));
        ilabtestpage.btnSendApplication.click();


    }



}


















