package com.shopme.admin.user;

import java.awt.Color;
import java.io.IOException;
import java.util.List;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.shopme.common.entity.User;

import jakarta.servlet.http.HttpServletResponse;

public class UserPdfExporter extends AbstractExporter {

	public void export(List<User> listUsers, HttpServletResponse response) throws IOException {
		super.setResponseHeader(response, "application/pdf", ".pdf");

		Document document = new Document(PageSize.A4);
		PdfWriter.getInstance(document, response.getOutputStream());

		document.open();

		Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		font.setSize(18);
		font.setColor(Color.BLUE);

		Paragraph paragraph = new Paragraph("使用者列表", font);
		paragraph.setAlignment(Paragraph.ALIGN_CENTER);

		document.add(paragraph);

		PdfPTable table = new PdfPTable(6);
		table.setWidthPercentage(100f);
		table.setSpacingBefore(10);
		table.setWidths(new float[] { 1.2f, 3.5f, 3.0f, 3.0f, 3.0f, 1.7f });

		writeTableHeader(table);
		writeTableData(table, listUsers);

		document.add(table);

		document.close();

	}

	private void writeTableData(PdfPTable table, List<User> listUsers) {
		for (User user : listUsers) {
			table.addCell(String.valueOf(user.getId()));
			table.addCell(user.getEmail());
			table.addCell(user.getFirstName());
			table.addCell(user.getLastName());
			table.addCell(user.getRoles().toString());
			table.addCell(String.valueOf(user.isEnabled()));
		}
	}

	private void writeTableHeader(PdfPTable table) throws IOException {
		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(Color.BLUE);
		cell.setPadding(5);

		Font font = FontFactory.getFont(FontFactory.HELVETICA);
		font.setColor(Color.WHITE);

		cell.setPhrase(new Phrase("使用者編號", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("使用者信箱", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("使用者姓氏", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("使用者名字", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("使用者權限", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("使用者是否啟用", font));
		table.addCell(cell);
	}
}
