import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.xhtmlrenderer.pdf.ITextRenderer;
import java.io.FileOutputStream;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;

public class GeneratePdfServlet extends HttpServlet{
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		String dose = request.getParameter("dose");
		String name = request.getParameter("name");
		String age = request.getParameter("age");
		String gender = request.getParameter("gender");
		String adharNo = request.getParameter("adharNo");
		String date = request.getParameter("date");
		String city = request.getParameter("city");
		OutputStream out = response.getOutputStream(); /* Get the output stream from the response object */
		OutputStream outputStream = new FileOutputStream(new File("D:\\TestImageFile.pdf"));
		try {
                /* Set the output response type */
                /* If the below property is not set, the browser will simply dump the PDF as a text file as an output */
                response.setContentType("application/pdf"); /* We have to set this response type for the browser to open the PDF properly */            
                ITextRenderer renderer = new ITextRenderer();
                /* Accept the input provided by user in the HTML form */
                renderer.setDocumentFromString("<table width='80%' border='2' align='center'>"
                		+ "<tr>"
                		+ "<td colspan='2' style=\"text-align: center;font-weight:bold;color:green;\">Provisional Certificate for Covid 19 Vaccination - "+dose+"</td>"
                		+ "</tr>"
                		+ "<tr>"
                		+ "<td>Beneficiary Name : </td>"
                		+ "<td>"+name+"</td>"
                		+ "</tr>"
                		+ "<tr>"
                		+ "<td>Age : </td>"
                		+ "<td>"+age+" Years</td>"
                		+ "</tr>"
                		+ "<tr>"
                		+ "<td>Gender : </td>"
                		+ "<td>"+gender+"</td>"
                		+ "</tr>"
                		+ "<tr>"
                		+ "<td>Adhar Number : </td>"
                		+ "<td>"+adharNo+"</td>"
                		+ "</tr>"
                		+ "<tr>"
                		+ "<td>Vaccination Center : </td>"
                		+ "<td>"+city+"</td>"
                		+ "</tr>"
                		+ "<tr>"
                		+ "<td>Vaccination Date : </td>"
                		+ "<td>"+date+"</td>"
                		+ "</tr>"
                		+ "</table>");
                renderer.layout();              
                /* Write the converted PDF output to the output stream */
                renderer.createPDF(out);                
        }
        catch (Exception e) {
                e.printStackTrace(); /* Throw exceptions to log files */
        }
        finally {
                out.close();/* Close the output stream */
        }
    }

    public static ByteArrayOutputStream getPdfFile() {

        Document document = new Document();
        ByteArrayOutputStream bout = new ByteArrayOutputStream();

        try {

            PdfPTable table = new PdfPTable(3);
            table.setWidthPercentage(60);
            table.setWidths(new int[]{1, 3, 3});
            table.setSummary("Provisional Certificate for Covid 19 Vaccination");

            Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);

            PdfPCell hcell;
            hcell = new PdfPCell(new Phrase("Name:", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);

            hcell = new PdfPCell(new Phrase("Name", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);

            hcell = new PdfPCell(new Phrase("Population", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);
            
            hcell = new PdfPCell(new Phrase("Address", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);

            PdfWriter.getInstance(document, bout);
            document.open();
            document.add(table);
            
            document.close();
            
        } catch (DocumentException ex) {
        
            Logger.getLogger(GeneratePdfServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        return bout; 
    }
}