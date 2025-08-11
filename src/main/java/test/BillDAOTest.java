package test;

import dao.BillDAO;
import model.Bill;
import model.BillItem;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class BillDAOTest {

    private BillDAO billDAO;

    @Before
    public void setUp() {
        billDAO = new BillDAO();
    }

    @Test
    public void testAddBillAndItemsAndGetById() {
        // Step 1: Create bill
        Bill bill = new Bill();
        bill.setCustomerId(1);     
        bill.setCashierId(1);      
        bill.setBillDate(LocalDateTime.now());
        bill.setTotalAmount(1000.0);

        int billId = billDAO.addBill(bill);
        assertTrue(billId > 0);

        // Step 2: Add bill items
        BillItem item1 = new BillItem();
        item1.setItemId(1);       
        item1.setQuantity(2);
        item1.setUnitPrice(500.0);
        item1.setSubtotal(1000.0);

        billDAO.addBillItems(Arrays.asList(item1), billId);

        // Step 3: Get bill
        Bill fetched = billDAO.getBillById(billId);
        assertNotNull(fetched);
        assertEquals(bill.getTotalAmount(), fetched.getTotalAmount(), 0.01);

        // Step 4: Get bill items
        List<BillItem> items = billDAO.getBillItemsByBillId(billId);
        assertFalse(items.isEmpty());
        assertEquals(2, items.get(0).getQuantity());
    }

    @Test
    public void testGetAllBills() {
        List<Bill> bills = billDAO.getAllBills();
        assertNotNull(bills);
        assertTrue(bills.size() >= 0); 
    }
}

