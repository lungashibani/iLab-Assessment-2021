package pageObjects;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public final class UsefulMethods {

    /*
        Here I have some static variable so we can store any of the Login details
        When we login using the backend method.
     */

    public static String access_token;
    public static String expires_in;
    public static String id_token;
    public static String token_type;
    public static String user_id;

    /*
       This is to store the URL of the candidate page the we have currently open.
       The purpose is to be able to store the latest Candidate we have created and know it's URL
       That way we can easily navigate to it when during any other test.
    */
    public static void setTestData_CandidateURL(String driverURL) throws IOException {
        FileWriter file = new FileWriter("./src/test/resources/CandidateURL.txt",false);
        file.write(driverURL);
        System.out.println(driverURL);
        file.close();
    }

    /* Get the URL of the stored of the last candidate created. */
    public static String getTestData_CandidateURL() throws IOException {
        Path filePath = Paths.get("./src/test/resources/CandidateURL.txt");
        String candidateURL;
        List<String> lines = Files.readAllLines(filePath);
        candidateURL = lines.get(0);
        System.out.println(candidateURL);
        return candidateURL;
    }

}