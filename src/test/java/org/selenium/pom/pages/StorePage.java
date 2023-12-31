package org.selenium.pom.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.selenium.pom.base.BasePage;
import org.selenium.pom.pages.components.ProductThumbnail;


public class StorePage extends BasePage {
    private final By searchFld = By.id("woocommerce-product-search-field-0");
    private final By searchBtn = By.cssSelector("button[value='Search']");
    private final By title = By.cssSelector(".woocommerce-products-header__title.page-title");
    private ProductThumbnail productThumbnail;
      public ProductThumbnail getProductThumbnail() {
          return productThumbnail;
      }
    public StorePage(WebDriver driver) {
        super(driver);
        productThumbnail = new ProductThumbnail(driver);
    }

    private StorePage enterTextInSearchFld(String txt){
        driver.findElement(searchFld).sendKeys(txt);
        return this;
    }

    public StorePage load(){
        load("/store");
        return this;
    }

    public StorePage search(String txt){
        enterTextInSearchFld(txt).clickSearchBtn();
        return this;
    }

    private StorePage clickSearchBtn(){
        driver.findElement(searchBtn).click();
        return this;
    }

    public String getTitle(){
        return driver.findElement(title).getText();
    }


}
