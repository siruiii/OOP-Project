import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Comparator;


public class CartManager {
    public static List<Item> cart_items = new ArrayList<Item>();
    public static int totalcount;
    public static double totalprice;

    public static List<Item> readCartItem() {
        return cart_items;
    }

    public static int getTotalCount() {
        totalcount=0;
        for (Item item : CartManager.readCartItem()) {
            totalcount+=item.getQuantity();
        }
        return totalcount;
    } 
    public static double getTotalPrice() {
        totalprice=0;
        for (Item item : CartManager.readCartItem()) {
            if(item.getSize() == "Small"){
                totalprice+=item.getSmallPrice()*item.getQuantity();
            }else if(item.getSize() == "Medium"){
                totalprice+=item.getMediumPrice()*item.getQuantity();
            }else if(item.getSize() == "Large"){
                totalprice+=item.getLargePrice()*item.getQuantity();
            }
        }
        return totalprice;
    } 


    public static void addItem(Item item) {
        cart_items.add(item);
    }

    public static void editItem(int Index, int quantity, String size) {
        //edit a selected item
        Item item = cart_items.get(Index);
            item.setQuantity(quantity);
            item.setSize(size);
    }
    public static void deleteItem(int Index) {
        cart_items.remove(Index);
    }
    public static void resetCart(){
        cart_items.clear();
    }

    // Sorts the cart items alphabetically by item name
    public static List<Item> getSortedCart() {
        combineDup();
        Collections.sort(cart_items, new Comparator<Item>() {
            @Override
            public int compare(Item item1, Item item2) {
                return item1.getName().compareToIgnoreCase(item2.getName());
            }
        });
        return cart_items;
    }

    // Consolidates items in the cart that have the same name and size
    private static void combineDup() {
        for (int i = 0; i < cart_items.size(); i++) {
            Item item1 = cart_items.get(i);
            for (int j = i + 1; j < cart_items.size(); j++) {
                Item item2 = cart_items.get(j);
                if (item1.getName().equalsIgnoreCase(item2.getName()) &&
                    item1.getSize().equalsIgnoreCase(item2.getSize())) {

                    // Combine quantities & price
                    item1.setQuantity(item1.getQuantity() + item2.getQuantity());

                    // Remove the duplicate item
                    cart_items.remove(j);
                    j--; // Adjust index after removal
                }
            }
        }
    }
}