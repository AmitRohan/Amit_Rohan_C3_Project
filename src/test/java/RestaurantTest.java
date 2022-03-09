import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {
    Restaurant restaurant;

    @BeforeEach
    public void initializeRestaurant(){
        LocalTime openingTime = LocalTime.now().minusHours(2);
        LocalTime closingTime = LocalTime.now().plusHours(2);
        restaurant = new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        assertTrue(restaurant.isRestaurantOpen());
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        restaurant.closingTime = LocalTime.now().minusHours(1);
        assertFalse(restaurant.isRestaurantOpen());
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>ORDER VALUE<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void order_value_should_return_zero_for_no_item_in_list(){
        List<String> orders = new ArrayList<String>();
        assertEquals(0,restaurant.getOderValueFor(orders));
    }

    @Test
    public void order_value_should_not_be_zero_if_items_in_list(){
        List<String> orders = new ArrayList<String>();
        orders.add("Vegetable lasagne");
        assertNotEquals(0,restaurant.getOderValueFor(orders));
    }

    @Test
    public void order_value_should_return_sum_of_items_price_in_the_passed_list(){
        List<Item> allItems = restaurant.getMenu();
        List<String> orders = new ArrayList<String>();
        int expectedOrderValue = 0;
        for (Item item:
                allItems) {
            orders.add(item.getName());
            expectedOrderValue+=item.getPrice();
        }
        assertEquals(expectedOrderValue,restaurant.getOderValueFor(orders));
    }

}