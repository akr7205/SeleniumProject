package org.selenium.pom.api.actions;

import org.selenium.pom.objects.User;
import org.selenium.pom.utils.FakerUtils;

public class DummyClass {
    public static void main(String[] args) {
       String username ="demouser" +new FakerUtils().generateRandomNumber();
       User user = new User().
               setUsername(username).
               setPassword("demopwd").
               setEmail(username + "@askomdch.com");
       SignUpApi signUpApi = new SignUpApi();
       signUpApi.register(user);
       BillingApi billingApi = new BillingApi(signUpApi.getCookies());
       billingApi.getBillingAddress();


//       System.out.println("get user "+signUpApi.register(user));
//        System.out.println("GET COOKIES "+signUpApi.getCookies());
//
//        CartApi cartApi = new CartApi();
//        cartApi.addToCart(1215, 1);
//        System.out.println("CART COOKIES: " + cartApi.getCookies());

    }
}
