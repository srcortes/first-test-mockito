package com.boot.bookingrestaurantapi.services;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Date;
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
import com.boot.bookingrestaurantapi.jsons.CreateReservationRest;
import com.boot.bookingrestaurantapi.repositories.ReservationRespository;
import com.boot.bookingrestaurantapi.repositories.RestaurantRepository;
import com.boot.bookingrestaurantapi.repositories.TurnRepository;
import com.boot.bookingrestaurantapi.services.impl.ReservationServiceImpl;
import java.util.Optional;

public class ReservationServiceTest {
	
	private static final CreateReservationRest CREATE_RESERVATION_REST = new CreateReservationRest();	
	private static final Restaurant RESTAURANT = new Restaurant();
	private static final Reservation RESERVATION_TURN_AND_RESTAUTANTID = new Reservation();
	private static final Long RESTAURANT_ID = 1L;
	private static final Long PERSON = 2L;
	private static final Long TURN_ID = 3L;
	private static final String LOCATOR = RESTAURANT_ID+""+CREATE_RESERVATION_REST;
	private static final String NAME = "Burger";
	private static final String DESCRIPTION = "Todo tipo de hamburguesas";
	private static final String ADDRES = "Av. Galindo";
	private static final String IMAGE = "www.image.com";	
	private static final List<Turn> TURN_LIST = new ArrayList<>();
	private static final List<Board> BOARD = new ArrayList<>();
	private static final List<Reservation> RESERVATION = new ArrayList<>();
	private static final Optional<Restaurant> OPTIONAL_RESTAURANT = Optional.of(RESTAURANT);
	private static final Optional<Restaurant> OPTIONAL_RESTAURANT_EMPTY = Optional.empty();
	private static final Turn TURNS = new Turn();
	private static final Optional<Turn> OPTIONAL_TURN = Optional.of(TURNS);
	private static final Optional<Turn> OPTIONAL_TURN_EMPTY = Optional.empty();
	private static final Optional<Reservation> OPTIONAL_RESERVATION_TURN_AND_RESTAUTANTID = Optional.of(RESERVATION_TURN_AND_RESTAUTANTID);
	
	@Mock
	RestaurantRepository restaurantRepository;
	
	@Mock
	TurnRepository turnRepository;
	
	@Mock
	ReservationRespository reservationRespository;
	
	@InjectMocks
	ReservationServiceImpl reservationServiceImpl;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		
		Date date = new Date();
		CREATE_RESERVATION_REST.setDate(date);
		CREATE_RESERVATION_REST.setPerson(PERSON);
		CREATE_RESERVATION_REST.setRestaurantId(RESTAURANT_ID);
		CREATE_RESERVATION_REST.setTurnId(TURN_ID);		
		RESTAURANT.setName(NAME);
		RESTAURANT.setDescription(DESCRIPTION);
		RESTAURANT.setAddress(ADDRES);
		RESTAURANT.setId(RESTAURANT_ID);
		RESTAURANT.setImage(IMAGE);
		RESTAURANT.setTurns(TURN_LIST);
		RESTAURANT.setBoards(BOARD);
		RESTAURANT.setReservations(RESERVATION);
		TURNS.setId(TURN_ID);
		TURNS.setName(NAME);
		TURNS.setRestaurant(RESTAURANT);
	}
	
	@Test
	public void createReservationTest() throws BookingException{
		Mockito.when(restaurantRepository.findById(RESTAURANT_ID)).thenReturn(OPTIONAL_RESTAURANT);
		Mockito.when(turnRepository.findById(TURN_ID)).thenReturn(OPTIONAL_TURN);
		Mockito.when(reservationRespository.findByTurnAndRestaurantId(TURNS.getName(), RESTAURANT.getId())).thenReturn(Optional.empty());
		Mockito.when(reservationRespository.save(Mockito.any(Reservation.class))).thenReturn(new Reservation());
		reservationServiceImpl.createReservation(CREATE_RESERVATION_REST);		
	}
	@Test( expected = BookingException.class)
	public void createReservationFindByIdTestError() throws BookingException{
		Mockito.when(restaurantRepository.findById(RESTAURANT_ID)).thenReturn(OPTIONAL_RESTAURANT_EMPTY);		
		reservationServiceImpl.createReservation(CREATE_RESERVATION_REST);
		fail();		
	}
	@Test( expected = BookingException.class)
	public void createReservationFindByIdRepositoryTestError() throws BookingException{
		Mockito.when(restaurantRepository.findById(RESTAURANT_ID)).thenReturn(OPTIONAL_RESTAURANT);
		Mockito.when(turnRepository.findById(CREATE_RESERVATION_REST.getTurnId())).thenReturn(OPTIONAL_TURN_EMPTY);		
		reservationServiceImpl.createReservation(CREATE_RESERVATION_REST);
		fail();		
	}
	@Test(expected = BookingException.class)
	public void createReservationFindByTurnAndRestaurantIdTestError() throws BookingException {
		Mockito.when(restaurantRepository.findById(RESTAURANT_ID)).thenReturn(OPTIONAL_RESTAURANT);
		Mockito.when(turnRepository.findById(TURN_ID)).thenReturn(OPTIONAL_TURN);
		Mockito.when(reservationRespository.findByTurnAndRestaurantId(TURNS.getName(), RESTAURANT.getId())).thenReturn(OPTIONAL_RESERVATION_TURN_AND_RESTAUTANTID);		
		reservationServiceImpl.createReservation(CREATE_RESERVATION_REST);	
		fail();		
	}
	@Test(expected = BookingException.class)
	public void createReservationInternalServerErrorTest() throws BookingException{
		Mockito.when(restaurantRepository.findById(RESTAURANT_ID)).thenReturn(OPTIONAL_RESTAURANT);
		Mockito.when(turnRepository.findById(TURN_ID)).thenReturn(OPTIONAL_TURN);
		Mockito.when(reservationRespository.findByTurnAndRestaurantId(TURNS.getName(), RESTAURANT.getId())).thenReturn(Optional.empty());
		Mockito.doThrow(Exception.class).when(reservationRespository).save(Mockito.any(Reservation.class));
		reservationServiceImpl.createReservation(CREATE_RESERVATION_REST);
		fail();
	}
}
