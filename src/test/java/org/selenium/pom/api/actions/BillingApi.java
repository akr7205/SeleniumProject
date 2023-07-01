package org.selenium.pom.api.actions;

import io.restassured.http.Cookies;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.selenium.pom.objects.BillingAddress;
import org.selenium.pom.utils.ConfigLoader;

import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class BillingApi {
    private Cookies cookies;

    public BillingApi(){}
    public BillingApi(Cookies cookies){
        this.cookies = cookies;
    }

    public Response addBillingAddress(BillingAddress billingAddress){
        Header header = new Header("Content-Type","application/x-www-form-urlencoded");
        Headers headers= new Headers(header);
        HashMap<String, String> formParams = new HashMap<>();
        formParams.put("billing_first_name", billingAddress.getFirstName());
        formParams.put("billing_last_name",billingAddress.getLastName());
        formParams.put("billing_company",billingAddress.getCompany());
        formParams.put("billing_country", "IN");
        formParams.put("billing_address_1",billingAddress.getAddressLineOne());
        formParams.put("billing_address_2","");
        formParams.put("billing_city",billingAddress.getCity());
        formParams.put("billing_state","MH");
        formParams.put("billing_postcode",billingAddress.getPostalCode());

        formParams.put("billing_phone","123456789");
        formParams.put("billing_email",billingAddress.getEmail());
        formParams.put("save_address","Save address");
        formParams.put("woocommerce-edit-address-nonce",fetchEditBillingAddressNonceValueUsingJsoup());
        formParams.put("_wp_http_referer","/account/edit-address/billing/");
        formParams.put("action","edit_address");



        Response response =
                given().
                        baseUri(ConfigLoader.getInstance().getBaseUrl()).
                        headers(headers).
                        formParams(formParams).
                        cookies(cookies).
                        log().all().
                        when().
                        post("/account/edit-address/billing/").
                        then().
                        log().all().
                        extract().
                        response();
        if(response.getStatusCode()!=302)
        {
            throw new RuntimeException("Failed to edit the address of the account -" +response.getStatusCode());
        }
        this.cookies = response.getDetailedCookies();
        return response;



    }



    public String fetchEditBillingAddressNonceValueUsingJsoup(){
        Response response = getBillingAddress();
        Document doc = Jsoup.parse(response.body().prettyPrint());
        Element element = doc.selectFirst("#woocommerce-edit-address-nonce");
        return element.attr("value");
    }
    public Response getBillingAddress(){

        Response response = given().
                baseUri(ConfigLoader.getInstance().getBaseUrl()).
                cookies(cookies).
                log().all().
                when().
                get("/account/edit-address/billing").
                then().
                log().all().
                extract().
                response();
        if(response.getStatusCode() != 200){
            throw new RuntimeException("Failed to fetch Billing Address, HTTP Status Code: " + response.getStatusCode());
        }
        return response;
    }

}
