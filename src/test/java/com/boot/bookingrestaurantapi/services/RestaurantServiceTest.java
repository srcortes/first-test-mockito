package com.boot.bookingrestaurantapi.services;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.boot.bookingrestaurantapi.entities.Board;
import com.boot.bookingrestaurantapi.entities.Reservation;
import com.boot.bookingrestaurantapi.entities.Restaurant;
import com.boot.bookingrestaurantapi.entities.Turn;
import com.boot.bookingrestaurantapi.exceptions.BookingException;
import com.boot.bookingrestaurantapi.jsons.TurnRest;
import com.boot.bookingrestaurantapi.repositories.RestaurantRepository;
import com.boot.bookingrestaurantapi.services.impl.ReservationServiceImpl;
import com.boot.bookingrestaurantapi.services.impl.RestaurantServiceImpl;

import java.util.Optional;

public class RestaurantServiceTest {
	
	private static final Long RESTAURANT_ID = 1L;
	private static final Restaurant RESTAURANT = new Restaurant();
	private static final List<Turn> TURN = new ArrayList<>();
	private static final List<Board> BOARD = new ArrayList<>();
	private static final List<Reservation> RESERVATION = new ArrayList<>();
	private static final String NAME = "Burger";
	private static final String DESCRIPTION = "Todo tipo de hamburguesas";
	private static final String ADDRES = "Av. Galindo";
	private static final String IMAGE = "www.image.com";
	
	@Mock
	RestaurantRepository restaurantRepository;
	
	@InjectMocks
	RestaurantServiceImpl restaurantServiceImpl;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		
		RESTAURANT.setName(NAME);
		RESTAURANT.setDescription(DESCRIPTION);
		RESTAURANT.setAddress(ADDRES);
		RESTAURANT.setId(RESTAURANT_ID);
		RESTAURANT.setImage(IMAGE);
		RESTAURANT.setTurns(TURN);
		RESTAURANT.setBoards(BOARD);
		RESTAURANT.setReservations(RESERVATION);
		
	}
	
	@Test
	public void getRestaurantByIdTest() throws BookingException{
		Mockito.when(restaurantRepository.findById(RESTAURANT_ID)).thenReturn(Optional.of(RESTAURANT));
		restaurantServiceImpl.getRestaurantById(RESTAURANT_ID);
	}
}
