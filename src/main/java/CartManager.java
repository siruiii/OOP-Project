import java.util.ArrayList;

public class CartManager {
    public static ArrayList<Item> cart_items = new ArrayList<Item>();

    public static ArrayList<Item> readCart() {
        return cart_items;
    }

    public static void addItem(Item item) {
        cart_items.add(item);
    }

    public static void editItem(Item item, int quantity, int size) {
        //edit a selected item
    }
    public static void deleteItem(Item item) {
        cart_items.remove(item);
    }
}