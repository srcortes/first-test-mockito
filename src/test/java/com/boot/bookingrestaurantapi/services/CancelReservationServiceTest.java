package com.boot.bookingrestaurantapi.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.boot.bookingrestaurantapi.entities.Reservation;
import com.boot.bookingrestaurantapi.exceptions.BookingException;
import com.boot.bookingrestaurantapi.repositories.ReservationRespository;
import com.boot.bookingrestaurantapi.services.impl.CancelReservationServiceImpl;

public class CancelReservationServiceTest {
	private static final Reservation RESERVATION = new Reservation();
	private static final String LOCATOR = "Pruebas 5";
	private static final String MSN_DELETE_RESERVATION = "LOCATOR_DELETED";
	private static final Optional<Reservation> OPTIONAL_RESERVATION = Optional.of(RESERVATION);
	@Mock
	private ReservationRespository reservationRespository;
	@InjectMocks
	CancelReservationServiceImpl cancelReservationServiceImpl;
	@Before
	public void iniciar() {
		MockitoAnnotations.initMocks(this);
	}
	@Test
	public void deleteReservationTest() throws BookingException{
		Mockito.when(reservationRespository.findByLocator(LOCATOR)).thenReturn(OPTIONAL_RESERVATION);
		Mockito.when(reservationRespository.deleteByLocator(LOCATOR)).thenReturn(OPTIONAL_RESERVATION);
		final String msn = cancelReservationServiceImpl.deleteReservation(LOCATOR);
		assertEquals(msn, MSN_DELETE_RESERVATION);
	}
	@Test(expected = BookingException.class)
	public void deleteReservationErorTest() throws BookingException{
		Mockito.when(reservationRespository.findByLocator(LOCATOR)).thenReturn(Optional.empty());
		cancelReservationServiceImpl.deleteReservation(LOCATOR);
		fail();		
	}
	@Test(expected = BookingException.class)
	public void deleteReservationInternalServerErorTest() throws BookingException{
		Mockito.when(reservationRespository.findByLocator(LOCATOR)).thenReturn(OPTIONAL_RESERVATION);
		Mockito.doThrow(Exception.class).when(reservationRespository).deleteByLocator(LOCATOR);
		cancelReservationServiceImpl.deleteReservation(LOCATOR);
		fail();		
	}
	

}
