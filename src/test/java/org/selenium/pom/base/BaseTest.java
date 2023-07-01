package org.selenium.pom.base;

import io.restassured.http.Cookies;
import org.openqa.selenium.WebDriver;
import org.selenium.pom.constants.DriverType;
import org.selenium.pom.factory.DriverManagerFactory;
import org.selenium.pom.factory.DriverManagerOriginal;
import org.selenium.pom.utils.CookieUtils;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.openqa.selenium.Cookie;

import java.io.IOException;
import java.util.List;



public class BaseTest {
    private final ThreadLocal<WebDriver> driverManager =new ThreadLocal<>();
    private void setDriverManager(WebDriver driver){
        this.driverManager.set(driver);
    }
    protected WebDriver getDriver(){
        return this.driverManager.get();
    }

    @Parameters("browser")
    @BeforeMethod
    public void startDriver(@Optional String browser){
        browser = System.getProperty("browser", browser);
        if(browser == null) browser ="CHROME";
//        setDriver(new DriverManagerOriginal().intializeDriver(browser));
        setDriverManager(DriverManagerFactory.getManager(DriverType.valueOf(browser)).createDriver());

        System.out.println("CURRENT THREAD " + Thread.currentThread().getId() + " , "+
                "DRIVER = "+ getDriver());


    }
    @Parameters("browser")
    @AfterMethod
    public void quitDriver(@Optional String browser, ITestResult result) throws IOException {
        System.out.println("CURRENT THREAD " + Thread.currentThread().getId() + " , "+
                "DRIVER = "+ getDriver());

//        if(result.getStatus() == ITestResult.FAILURE){
//            File desFile = new File("screnshoot",File.separator + browser +File.separator+
//                    result.getTestClass().getRealClass().getSimpleName() + "_" +
//                    result.getMethod().getMethodName() + ".png");
////            takeScreenshot(desFile);
////            takeScreenshotUsingAShot(desFile);
//
//        }

        getDriver().quit();
    }

//    private void takeScreenshot(File destFile) throws IOException {
//        TakesScreenshot takesScreenshot = (TakesScreenshot) getDriver();
//        File srcFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
//        FileUtils.copyFile(srcFile, destFile);
//    }

//    private void takeScreenshotUsingAShot(File destFile){
//        Screenshot screenshot = new AShot()
//                .shootingStrategy(ShootingStrategies.viewportPasting(100))
//                .takeScreenshot(getDriver());
//        try{
//            ImageIO.write(screenshot.getImage(), "PNG", destFile);
//        }catch (IOException e){
//            e.printStackTrace();
//        }
//    }

    public void injectCookiesToBrowser(Cookies cookies){
        List<Cookie> seleniumCookies = new CookieUtils().convertRestAssuredCookiesToSeleniumCookies(cookies);
        for(Cookie cookie: seleniumCookies){
            System.out.println(cookie.toString());
            getDriver().manage().addCookie(cookie);
        }
    }


}
