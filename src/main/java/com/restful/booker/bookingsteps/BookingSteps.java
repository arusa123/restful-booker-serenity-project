package com.restful.booker.bookingsteps;

import com.restful.booker.constants.EndPoints;
import com.restful.booker.model.AuthorisationPojo;
import com.restful.booker.model.BookingPojo;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

public class BookingSteps {

    @Step("Token authroisation : {0}")
    public ValidatableResponse tokenAuthorisation() {

        AuthorisationPojo authorisationPojo = new AuthorisationPojo();
        authorisationPojo.setUsername("admin");
        authorisationPojo.setPassword("password123");
        return SerenityRest.given().log().all()
                .header("Content-Type", "application/json")
                .when()
                .body(authorisationPojo)
                .post("https://restful-booker.herokuapp.com/auth")
                .then().log().all().statusCode(200);
    }

    @Step("Creating new booking wiht firstName : {0}, lastName: {1}, email: {2}, totalprice: {3} depositpaid: {4}, bookingdates: {5} and additonalneeds: {6}")
    public ValidatableResponse createBooking(String firstname, String lastname, int totalprice, boolean depositpaid, BookingPojo.BookingDates bookingdates, String additionalneeds) {

        BookingPojo bookingPojo = new BookingPojo();
        bookingPojo.setFirstname(firstname);
        bookingPojo.setLastname(lastname);
        bookingPojo.setTotalprice(totalprice);
        bookingPojo.setDepositpaid(depositpaid);
        bookingPojo.setBookingdates(bookingdates);
        bookingPojo.setAdditionalneeds(additionalneeds);
        return SerenityRest.given()
                .header("Content-Type", "application/json")
                .when()
                .body(bookingPojo)
                .post()
                .then().log().all();

    }

    @Step
    public ValidatableResponse getAllTheBookings(int id) {
        return SerenityRest.given()
                .pathParam("id", id)
                .when().get(EndPoints.GET_ALL_BOOKINGS_IDS)
                .then().log().all();
    }

    @Step("Updating record with id:{0}, token{1}, firstName: {2}, lastName: {3}, email: {4}, totalprice: {5} depositpaid: {6}, bookingdates: {7} and additonalneeds: {8} ")
    public ValidatableResponse updateBooking(int id, String token, String firstname, String lastname, int totalprice, boolean depositpaid, BookingPojo.BookingDates bookingdates, String additionalneeds) {
        BookingPojo bookingPojo = new BookingPojo();

        bookingPojo.setFirstname(firstname);
        bookingPojo.setLastname(lastname);
        bookingPojo.setTotalprice(totalprice);
        bookingPojo.setDepositpaid(depositpaid);
        bookingPojo.setBookingdates(bookingdates);
        bookingPojo.setAdditionalneeds(additionalneeds);

        return SerenityRest.given().log().all()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .header("Cookie", "token=" + token)
                .pathParam("id", id)
                .body(bookingPojo)
                .when().put(EndPoints.UPDATE_USER_BY_ID)
                .then().log().all().statusCode(200);
    }

    @Step
    public ValidatableResponse deleteBookingById(int id, String token) {
        return SerenityRest.given().log().all()
                .header("Content-Type", "application/json")
                .header("Cookie", "token=" + token)
                .pathParam("id", id)
                .when().delete(EndPoints.DELETE_BOOKING_BY_ID)
                .then().log().all();
    }

    @Step
    public ValidatableResponse getSingleBookingById(int id) {
        return SerenityRest.given()
                .pathParam("id", id)
                .when().get(EndPoints.GET_SINGLE_BOOKING_BY_ID)
                .then().log().all();
    }

}
