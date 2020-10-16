package com.boot.bookingrestaurantapi.controller;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.boot.bookingrestaurantapi.controllers.CancelReservationController;
import com.boot.bookingrestaurantapi.exceptions.BookingException;
import com.boot.bookingrestaurantapi.responses.BookingResponse;
import com.boot.bookingrestaurantapi.services.CancelReservationService;

public class CancelReservationControllerTest {
	private static final String SUCCES_STATUS = "Succes";
	private static final String SUCCES_CODE = "200 OK";
	private static final String SUCCES_MESSAGE = "OK";	
	private static final String LOCATOR = "Burger 7";
	private static final String MSN_DELETE_RESERVATION = "LOCATOR_DELETED";
	@Mock
	private CancelReservationService cancelReservationService;
	@InjectMocks
	private CancelReservationController cancelReservationController;
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}
	@Test
	public void deleteReservationTest() throws BookingException {
		Mockito.when(cancelReservationService.deleteReservation(LOCATOR)).thenReturn(MSN_DELETE_RESERVATION);
		BookingResponse<String> response = cancelReservationController.deleteReservation(LOCATOR);
		assertEquals(response.getCode(), SUCCES_CODE);
		assertEquals(response.getMessage(), SUCCES_MESSAGE);
		assertEquals(response.getStatus(), SUCCES_STATUS);
		assertEquals(response.getData(), MSN_DELETE_RESERVATION);
	}

}
