package org.selenium.pom.factory;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.selenium.pom.constants.DriverType;

public class DriverManagerOriginal {

    public WebDriver intializeDriver(String browser){
       WebDriver driver;
       browser =System.getProperty("browser",browser);

       switch (DriverType.valueOf(browser)){
           case CHROME ->{
               WebDriverManager.chromedriver().cachePath("Drivers").setup();
               driver = new ChromeDriver();
           }
           case FIREFOX ->{
               WebDriverManager.firefoxdriver().cachePath("Drivers").setup();
               driver = new FirefoxDriver();
           }
           default -> throw new IllegalStateException("Invalid Browser Name " + browser);
       }

        driver.manage().window().maximize();
        return driver;
    }

}
