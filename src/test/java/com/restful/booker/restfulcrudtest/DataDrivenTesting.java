package com.restful.booker.restfulcrudtest;

import com.restful.booker.bookingsteps.BookingSteps;
import com.restful.booker.model.BookingPojo;
import com.restful.booker.testbase.TestBase;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Test;

//@Concurrent(threads = "4x")
//@UseTestDataFrom("src/test/java/resources/testdata/bookinginfo.csv")
//@RunWith(SerenityParameterizedRunner.class)

public class DataDrivenTesting extends TestBase {

    private String firstname;
    private String lastname;
    private int totalprice;
    private boolean depositpaid;
    private String checkin;
    private String checkout;
    private String additionalneeds;

    @Steps
    BookingSteps bookingSteps;

    @Title("Data driven test for creating multiple bookings")
    @Test
    public void createMultipleBookings() {
        BookingPojo.BookingDates bookingdates = new BookingPojo.BookingDates();
        bookingdates.setCheckin(checkin);
        bookingdates.setCheckout(checkout);
        bookingSteps.createBooking(firstname, lastname, totalprice, depositpaid, bookingdates, additionalneeds).statusCode(200);
    }
}


