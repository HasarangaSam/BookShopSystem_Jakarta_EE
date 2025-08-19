package dao;

import model.Bill;
import model.BillItem;
import java.util.List;

public interface BillDAOInterface {

    int addBill(Bill bill);

    void addBillItems(List<BillItem> items, int billId);

    List<Bill> getAllBills();

    Bill getBillById(int billId);

    List<BillItem> getBillItemsByBillId(int billId);
}