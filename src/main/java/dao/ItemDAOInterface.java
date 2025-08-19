package dao;

import model.BillItem;
import model.Item;
import java.util.List;

public interface ItemDAOInterface {

    void addItem(Item item);

    List<Item> getAllItems();

    Item getItemById(int id);

    void updateItem(Item item);

    void deleteItem(int id);

    List<Item> searchItemsByName(String name);

    List<Item> searchItemsByNameAndCategory(String name, int categoryId);

    List<Item> getItemsByCategory(int categoryId);

    void updateStock(int itemId, int newStockQuantity);

}

