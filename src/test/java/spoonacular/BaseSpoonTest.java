package spoonacular;

public class BaseSpoonTest {
    protected final static String SEARCH_RECIPES = "/recipes/complexSearch";
    protected final static String CLASSIFY_CUISINE = "/recipes/cuisine";
    protected final static String CONNECT_USER = "/users/connect";
    protected final static String GET_SHOPPING_LIST = "/mealplanner/{username}/shopping-list";
    protected final static String ADD_TO_SHOPPING_LIST = "/mealplanner/{username}/shopping-list/items";
    protected final static String DELETE_FROM_SHOPPING_LIST = "/mealplanner/{username}/shopping-list/items/{id}";

    protected final static int SUCCESS_STATUS_CODE = 200;
}
