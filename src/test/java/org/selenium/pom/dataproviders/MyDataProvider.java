package org.selenium.pom.dataproviders;

import org.selenium.pom.objects.Product;
import org.selenium.pom.utils.JacksonUtils;
import org.testng.annotations.DataProvider;

import java.io.IOException;

public class MyDataProvider {
    @DataProvider(name = "getFeatureProducts",parallel = false)
    public Object[] getFeatureProducts() throws IOException {
        return JacksonUtils.deserializeJson("products.json", Product[].class);
    }
}
