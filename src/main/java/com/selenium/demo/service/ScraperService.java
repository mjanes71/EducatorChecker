package com.selenium.demo.service;

import lombok.AllArgsConstructor;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;

@Service
@AllArgsConstructor
public class ScraperService {
    private static final String URL = "https://core.ode.state.oh.us/Core4/ODE.CORE.Lic.Profile.Public.UI/";

    private final ChromeDriver driver;


    @PostConstruct
    void postConstructFirst() {
        //scrape("OH3126609");
        scrape("OH1224108");
    }


    public void scrape(String educatorID) {
        //navigate to ODE site and search for particular educator ID
        driver.get(URL);
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        WebElement searchID = driver.findElement(By.xpath("//*[@id='divEducProfSearchForm']"));
        searchID.findElement(By.xpath("//html/body//div[2]/form/div[1]/div[1]/div/input")).sendKeys(educatorID + "\n");

       try{
           Thread.sleep(2000);
       }catch (InterruptedException e) {
           System.out.println("The page had trouble loading. Check the ID or try again.");
       }

        //take credential screenshot
        scrollToSection("//*[@id='divEducCredentials']");
        takeScreenshot("/Users/janes.m/OneDrive - Procter and Gamble/Desktop/license_screenshot.png");

        //take documents screenshot
        scrollToSection("//*[@id='divEducDocuments']");
        takeScreenshot("/Users/janes.m/OneDrive - Procter and Gamble/Desktop/documents_screenshot.png");

        //take current position screenshot
        scrollToSection("//*[@id='divEducAssignments']");
        takeScreenshot("/Users/janes.m/OneDrive - Procter and Gamble/Desktop/currentPosition_screenshot.png");

        driver.quit();
    }

    public void takeScreenshot(String filePath) {
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            BufferedImage img = ImageIO.read(screenshot);
            File screenshotLocation = new File(filePath);
            try {
                FileUtils.copyFile(screenshot, screenshotLocation);
            } catch (IOException e) {
                System.out.println("There was an issue capturing the screenshot for filepath" + filePath);
            }

        } catch (IllegalArgumentException | IOException e) {
            System.out.println("There was an issue capturing the screenshot for filepath" + filePath);
        }

    }
    
    public void scrollToSection(String xPath) {
        WebElement ele = driver.findElement(By.xpath(xPath));
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("arguments[0].scrollIntoView(true);", ele);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            System.out.println("There was an error finding this element.");
        }
    }
}

