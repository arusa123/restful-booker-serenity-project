package com.restful.booker.restfulcrudtest;

import com.restful.booker.bookingsteps.BookingSteps;
import com.restful.booker.model.BookingPojo;
import com.restful.booker.testbase.TestBase;
import com.restful.booker.utils.TestUtils;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;

@RunWith(SerenityRunner.class)

public class RestfulCrudTest extends TestBase {
    static String firstname = "John" + TestUtils.getRandomValue();
    static String lastname = "Tester" + TestUtils.getRandomValue();
    static int totalprice = Integer.parseInt(1 + TestUtils.getRandomValue());
    static boolean depositpaid = true;
    static String additionalneeds = "pillow";

    static String token;
    static int id;
    @Steps
    BookingSteps bookingSteps;


    @Title("This method will create a Token")
    @Test
    public void test001() {
        ValidatableResponse response = bookingSteps.tokenAuthorisation().statusCode(200);
        token = response.extract().path("token");
        System.out.println(token);
    }

    @Title("This method will create a booking")
    @Test
    public void test002() {
        BookingPojo.BookingDates bookingdates = new BookingPojo.BookingDates();
        bookingdates.setCheckin("2023-08-01");
        bookingdates.setCheckout("2023-08-10");
        ValidatableResponse response = bookingSteps.createBooking(firstname, lastname, totalprice, depositpaid, bookingdates, additionalneeds).statusCode(200);
        id = response.extract().path("bookingid");
    }

    @Test
    public void test003() {
        bookingSteps.getSingleBookingById(id).statusCode(200);
    }

    @Test
    public void test004() {
        additionalneeds = "dinner";
        BookingPojo.BookingDates bookingdates = new BookingPojo.BookingDates();
        bookingdates.setCheckin("2023-08-01");
        bookingdates.setCheckout("2023-08-10");
        bookingSteps.updateBooking(id, token, firstname, lastname, totalprice, depositpaid, bookingdates, additionalneeds);
        ValidatableResponse response = bookingSteps.getSingleBookingById(id);
        HashMap<String, ?> update = response.extract().path("");
        Assert.assertThat(update, hasValue("dinner"));
    }

    @Title("This method will delete a booking with ID")
    @Test
    public void test005() {
        bookingSteps.deleteBookingById(id,token).statusCode(201);
        bookingSteps.getSingleBookingById(id).statusCode(404);
    }


}
