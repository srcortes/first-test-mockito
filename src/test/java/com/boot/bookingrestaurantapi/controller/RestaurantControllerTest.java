package com.boot.bookingrestaurantapi.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.boot.bookingrestaurantapi.controllers.RestaurantController;
import com.boot.bookingrestaurantapi.exceptions.BookingException;
import com.boot.bookingrestaurantapi.jsons.RestaurantRest;
import com.boot.bookingrestaurantapi.jsons.TurnRest;
import com.boot.bookingrestaurantapi.responses.BookingResponse;
import com.boot.bookingrestaurantapi.services.RestaurantService;

public class RestaurantControllerTest {
	
	private static final Long RESTAURANT_ID = 1L;
	private static final String SUCCES_STATUS = "Succes";
	private static final String SUCCES_CODE = "200 OK";
	private static final String SUCCES_MESSAGE = "OK";
	private static final RestaurantRest RESTAURANT_REST = new RestaurantRest();
	private static final List<TurnRest> TURN_LIST = new ArrayList<>();
    private static final List<RestaurantRest> LIST_RESTAURANT_REST = new ArrayList<>();
	
	private static final String NAME = "Burger";
	private static final String DESCRIPTION = "Todo tipo de hamburguesas";
	private static final String ADDRES = "Av. Galindo";
	private static final String IMAGE = "www.image.com";
	
	@Mock
	RestaurantService restaurantService;
	
	@InjectMocks
	RestaurantController restaurantController;
	
	@Before
	public void init() throws BookingException{
		MockitoAnnotations.initMocks(this);
		
		RESTAURANT_REST.setName(NAME);
		RESTAURANT_REST.setDescription(DESCRIPTION);
		RESTAURANT_REST.setAddress(ADDRES);
		RESTAURANT_REST.setId(RESTAURANT_ID);
		RESTAURANT_REST.setImage(IMAGE);
		RESTAURANT_REST.setTurns(TURN_LIST);
		
		LIST_RESTAURANT_REST.add(RESTAURANT_REST);	
	}
	
	@Test
	public void getRestaurantByIdTest() throws BookingException{
		Mockito.when(restaurantService.getRestaurantById(RESTAURANT_ID)).thenReturn(RESTAURANT_REST);
		final BookingResponse<RestaurantRest> response = restaurantController.getRestaurantById(RESTAURANT_ID);		
		assertEquals(response.getStatus(), SUCCES_STATUS);
		assertEquals(response.getCode(), SUCCES_CODE);
		assertEquals(response.getMessage(), SUCCES_MESSAGE);
		assertEquals(response.getData(), RESTAURANT_REST);
	}
	
	@Test
	public void getRestaurantsTest() throws BookingException{
		Mockito.when(restaurantService.getRestaurants()).thenReturn(LIST_RESTAURANT_REST);
		final BookingResponse<List<RestaurantRest>> response = restaurantController.getRestaurants();
		assertEquals(response.getStatus(), SUCCES_STATUS);
		assertEquals(response.getCode(), SUCCES_CODE);
		assertEquals(response.getMessage(), SUCCES_MESSAGE);
		assertFalse(LIST_RESTAURANT_REST.isEmpty());
	}
	

}
