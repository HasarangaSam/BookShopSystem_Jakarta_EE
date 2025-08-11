package util;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import com.lowagie.text.pdf.draw.LineSeparator;

import model.Bill;
import model.BillItem;

import java.awt.Color;
import java.io.FileOutputStream;
import java.util.List;

public class PDFGenerator {

    public static String generateInvoicePDF(Bill bill, List<BillItem> items, String outputPath) {
        try {
            Document document = new Document(PageSize.A4, 50, 50, 50, 50); // page margins
            PdfWriter.getInstance(document, new FileOutputStream(outputPath));
            document.open();

            // Fonts
            Font titleFont = new Font(Font.HELVETICA, 18, Font.BOLD);
            Font labelFont = new Font(Font.HELVETICA, 12, Font.BOLD);
            Font normalFont = new Font(Font.HELVETICA, 12);
            Font tableHeaderFont = new Font(Font.HELVETICA, 12, Font.BOLD);

            // Header
            Paragraph title = new Paragraph("Pahana Edu Bookshop - Invoice", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            document.add(new Paragraph(" "));
            document.add(new LineSeparator());

            // Bill Details
            document.add(new Paragraph("Invoice Details", labelFont));
            document.add(new Paragraph(" "));

            PdfPTable details = new PdfPTable(2);
            details.setWidthPercentage(100);
            details.setSpacingBefore(5);
            details.setSpacingAfter(10);
            details.getDefaultCell().setBorder(Rectangle.NO_BORDER);

            details.addCell(new Phrase("Bill ID:", labelFont));
            details.addCell(new Phrase(String.valueOf(bill.getBillId()), normalFont));

            details.addCell(new Phrase("Date:", labelFont));
            details.addCell(new Phrase(bill.getBillDate().toString(), normalFont));

            details.addCell(new Phrase("Customer:", labelFont));
            details.addCell(new Phrase(bill.getCustomerName(), normalFont));

            details.addCell(new Phrase("Cashier:", labelFont));
            details.addCell(new Phrase(bill.getCashierName(), normalFont));

            document.add(details);

            // Item Table
            document.add(new Paragraph("Purchased Items", labelFont));
            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{3f, 2f, 1f, 2f, 2f});
            table.setSpacingBefore(10f);

            // Table Header
            String[] headers = {"Item", "Brand", "Qty", "Unit Price (Rs.)", "Subtotal (Rs.)"};
            for (String header : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(header, tableHeaderFont));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor(new Color(230, 230, 250));
                table.addCell(cell);
            }

            // Table Rows
            for (BillItem item : items) {
                table.addCell(new Phrase(item.getItemName(), normalFont));
                table.addCell(new Phrase(item.getBrandName(), normalFont));
                table.addCell(new Phrase(String.valueOf(item.getQuantity()), normalFont));
                table.addCell(new Phrase(String.format("Rs. %.2f", item.getUnitPrice()), normalFont));
                table.addCell(new Phrase(String.format("Rs. %.2f", item.getSubtotal()), normalFont));
            }

            // Total
            PdfPCell totalLabelCell = new PdfPCell(new Phrase("Total", tableHeaderFont));
            totalLabelCell.setColspan(4);
            totalLabelCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            totalLabelCell.setBackgroundColor(new Color(245, 245, 245));
            table.addCell(totalLabelCell);

            PdfPCell totalValueCell = new PdfPCell(new Phrase(String.format("Rs. %.2f", bill.getTotalAmount()), tableHeaderFont));
            totalValueCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            totalValueCell.setBackgroundColor(new Color(245, 245, 245));
            table.addCell(totalValueCell);

            document.add(table);

            // Footer
            document.add(new Paragraph(" "));
            document.add(new LineSeparator());
            Paragraph thankYou = new Paragraph("Thank you for shopping with Pahana Edu Bookshop!", normalFont);
            thankYou.setAlignment(Element.ALIGN_CENTER);
            document.add(thankYou);

            document.close();
            return outputPath;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
