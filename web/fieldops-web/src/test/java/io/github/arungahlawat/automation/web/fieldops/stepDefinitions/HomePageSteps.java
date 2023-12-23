package io.github.arungahlawat.automation.web.fieldops.stepDefinitions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import java.time.Duration;

public class HomePageSteps {
    @FindBy(how = How.ID, using = "myid")
    private WebElement navigationLinkDevelopers;


    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.browserstack.com");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        driver.quit();
    }
}
