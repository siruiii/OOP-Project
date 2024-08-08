import java.util.ArrayList;
import java.util.List;


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

    public static void editItem(Item item, int quantity, int size) {
        //edit a selected item
    }
    public static void deleteItem(Item item) {
        cart_items.remove(item);
    }
}