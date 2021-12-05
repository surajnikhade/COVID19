
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DBConnection.DBConnection;

public class GetVaccinatedServlet extends HttpServlet {
	PrintWriter pw = null;
	

	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		pw = response.getWriter();
		PreparedStatement pst = null;
		ResultSet rs = null;

		
		//http://localhost:8080/Covid19/Servlet?combo=Nagpur&covid=all&covid-affected=all&age=all&doseValue=100
		HttpSession session = request.getSession();
		String districtname = (String)session.getAttribute("combo");
		String covidW = (String)session.getAttribute("covid");
		String covidAffected = (String)session.getAttribute("covid-affected");
		String age = (String)session.getAttribute("age");
		int doseValue =  (int) session.getAttribute("doseValue");
		
		  String aadharNo = request.getParameter("aadharNo"); 
		  String dose = request.getParameter("dose");
		  System.out.println("inside servlet GetVaccinatedServlet : "+aadharNo +" dose : "+dose);
		  
		  Connection con = DBConnection.getDBConnection();
		  try {
			  if(dose.equalsIgnoreCase("1")) {
				  pst = con.prepareStatement("update userregistered set dose1 = ?,dose1_vaccination_date=CURDATE() where aadhar_no = "+aadharNo);
			  }else if(dose.equalsIgnoreCase("2")) {
				  pst = con.prepareStatement("update userregistered set dose2 = ?,dose2_vaccination_date=CURDATE() where aadhar_no = "+aadharNo);
			  }
		  	
			pst.setString(1, "Y");
			int rec = pst.executeUpdate();
			if(rec>0) {
				//pw.println("<h2>Vaccinated Successfully...</h2>");
				//response.sendRedirect("Servlet");  
				String redirectURL= "Servlet?combo="+districtname+"&covid="+covidW+"&covid-affected="+covidAffected+"&age="+age+"&doseValue="+doseValue+"&vaccinated="+rec;
				System.out.println("redirectURL : "+redirectURL);
				//session.setAttribute("vaccinated", rec);
				//request.setAttribute("vaccinated", rec);
				/*if(dose.equalsIgnoreCase("1")) {
					pw.println("<script>alert('Dose 1 Vaccinated');</script>");
				}else if(dose.equalsIgnoreCase("2")) {
					pw.println("<script>alert('Dose 2 Vaccinated');</script>");
				}*/
				response.sendRedirect(redirectURL);  
			}
		  }catch(Exception e) {
			  System.out.println("Getting Error while updating dose Flag");
			  e.printStackTrace();
		  }
	}

	
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
}
