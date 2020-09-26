package com.boot.bookingrestaurantapi.controller;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.boot.bookingrestaurantapi.controllers.ReservationController;
import com.boot.bookingrestaurantapi.entities.Reservation;
import com.boot.bookingrestaurantapi.exceptions.BookingException;
import com.boot.bookingrestaurantapi.jsons.CreateReservationRest;
import com.boot.bookingrestaurantapi.jsons.ReservationRest;
import com.boot.bookingrestaurantapi.responses.BookingResponse;
import com.boot.bookingrestaurantapi.services.ReservationService;

public class ReservationControllerTest {
	
	private static final String SUCCES_STATUS = "Succes";
	private static final String SUCCES_CODE = "200 OK";
	private static final String SUCCES_MESSAGE = "OK";
	
	private static final Long RESTAURANT_ID = 1L;
	private static final Long PERSON = 2L;
	private static final Long TURN_ID = 3L;
	private static final Long RESERVATION_ID = 8L; 
	private static final CreateReservationRest CREATE_RESERVATION_REST = new CreateReservationRest();
	private static final ReservationRest RESERVATION_REST = new ReservationRest();
	private static final Reservation RESERVATION_ENTITY = new Reservation();
	private static final String LOCATOR = RESTAURANT_ID+""+CREATE_RESERVATION_REST;
	
	@Mock
	ReservationService reservationService;
	
	@InjectMocks
	ReservationController reservationController;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		Date date = new Date();
		CREATE_RESERVATION_REST.setDate(date);
		CREATE_RESERVATION_REST.setPerson(PERSON);
		CREATE_RESERVATION_REST.setRestaurantId(RESTAURANT_ID);
		CREATE_RESERVATION_REST.setTurnId(TURN_ID);
		
		RESERVATION_REST.setDate(date);
		RESERVATION_REST.setLocator(LOCATOR);
		RESERVATION_REST.setPerson(PERSON);
		RESERVATION_REST.setRestaurantId(RESTAURANT_ID);
		RESERVATION_REST.setTurnId(TURN_ID);
	}
	
	@Test
	public void getReservationByIdTest() throws BookingException{
		Mockito.when(reservationService.getReservation(RESERVATION_ID)).thenReturn(RESERVATION_REST);
		final BookingResponse<ReservationRest> response = reservationController.getReservationById(RESERVATION_ID);
		assertEquals(response.getStatus(),SUCCES_STATUS);
		assertEquals(response.getCode(), SUCCES_CODE);
		assertEquals(response.getMessage(),SUCCES_MESSAGE);
		assertEquals(response.getData(),RESERVATION_REST);
	}
	
	@Test
	public void createReservationTest() throws BookingException{
		Mockito.when(reservationService.createReservation(CREATE_RESERVATION_REST)).thenReturn(LOCATOR);
		final BookingResponse<String> response = reservationController.createReservation(CREATE_RESERVATION_REST);
		assertEquals(response.getStatus(),SUCCES_STATUS);
		assertEquals(response.getCode(), SUCCES_CODE);
		assertEquals(response.getMessage(),SUCCES_MESSAGE);
		assertEquals(response.getData(),LOCATOR);
		
	}

}
