package test;

import dao.ItemDAO;
import model.Item;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ItemDAOTest {

    private ItemDAO itemDAO;

    @Before
    public void setUp() {
        itemDAO = new ItemDAO();
    }

    @Test
    public void testAddAndGetItem() {
        // Use constructor for creating new item (without itemId, with categoryId)
        Item item = new Item(0, 1, "JUnit Item", "TestBrand", 250.0, 20);

        itemDAO.addItem(item);

        List<Item> results = itemDAO.searchItemsByName("JUnit Item");
        assertFalse(results.isEmpty());

        Item saved = results.get(0);
        assertEquals("JUnit Item", saved.getName());
        assertEquals("TestBrand", saved.getBrand());
        assertTrue(saved.getUnitPrice() >= 250.0);
    }

    @Test
    public void testUpdateItem() {
        List<Item> items = itemDAO.searchItemsByName("JUnit Item");
        if (!items.isEmpty()) {
            Item oldItem = items.get(0);

            // Use constructor to recreate the updated object
            Item updatedItem = new Item(
                oldItem.getItemId(),
                oldItem.getCategoryId(),
                oldItem.getCategoryName(),
                oldItem.getName(),
                oldItem.getBrand(),
                299.99,
                30
            );

            itemDAO.updateItem(updatedItem);

            Item updated = itemDAO.getItemById(oldItem.getItemId());
            assertEquals(299.99, updated.getUnitPrice(), 0.01);
            assertEquals(30, updated.getStockQuantity());
        }
    }

    @Test
    public void testUpdateStock() {
        List<Item> items = itemDAO.searchItemsByName("JUnit Item");
        if (!items.isEmpty()) {
            Item item = items.get(0);
            itemDAO.updateStock(item.getItemId(), 55);

            Item updated = itemDAO.getItemById(item.getItemId());
            assertEquals(55, updated.getStockQuantity());
        }
    }

    @Test
    public void testDeleteItem() {
        List<Item> items = itemDAO.searchItemsByName("JUnit Item");
        if (!items.isEmpty()) {
            int id = items.get(0).getItemId();
            itemDAO.deleteItem(id);

            Item deleted = itemDAO.getItemById(id);
            assertNull(deleted);
        }
    }
}
