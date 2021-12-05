
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DBConnection.DBConnection;

@WebServlet("/servlet")
public class Servlet extends HttpServlet {
	int i;
	PreparedStatement pst;
	String query_pst;
	ResultSet rs;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			i++;
			PrintWriter out = response.getWriter();
			response.setContentType("text/html");
			String districtname = request.getParameter("combo");
			String covidW = request.getParameter("covid");
			String covidAffected = request.getParameter("covid-affected");
			String age = request.getParameter("age");
			HttpSession session = request.getSession();
			
			//http://localhost:8080/Covid19/Servlet?combo=Nagpur&covid=all&covid-affected=all&age=all&doseValue=100
			int doseAllocation = Integer
					.parseInt(request.getParameter("doseValue") == "" ? "0" : request.getParameter("doseValue"));
			
			session.setAttribute("combo", districtname);
			session.setAttribute("covid", covidW);
			session.setAttribute("covid-affected", covidAffected);
			session.setAttribute("age", age);
			session.setAttribute("doseValue", doseAllocation);
			Connection con = DBConnection.getDBConnection();

			if (covidW.equalsIgnoreCase("all") && covidAffected.equalsIgnoreCase("all")
					&& age.equalsIgnoreCase("all")) {
				pst = con.prepareStatement(
						"select aadhar_no,name,Gender ,phone_number,dob,covid_affect,city,pincode,frontline,dose1 , dose2  from userregistered where district=? order by aadhar_no  LIMIT ?");
				pst.setString(1, districtname);
				pst.setInt(2, doseAllocation);
				query_pst = pst.toString();

			} else if (covidW.equalsIgnoreCase("all") && covidAffected.equalsIgnoreCase("all")
					&& age.equalsIgnoreCase("18-45")) {
				pst = con.prepareStatement(
						"select aadhar_no,name,Gender ,phone_number,dob,covid_affect,city,pincode,frontline,dose1 , dose2  from userregistered where district=? and age=? order by aadhar_no limit ?");
				pst.setString(1, districtname);
				pst.setString(2, age);
				pst.setInt(3, doseAllocation);
				query_pst = pst.toString();

			} else if (covidW.equalsIgnoreCase("all") && covidAffected.equalsIgnoreCase("all")
					&& age.equalsIgnoreCase("46-65")) {
				pst = con.prepareStatement(
						"select aadhar_no,name,Gender ,phone_number,dob,covid_affect,city,pincode,frontline,dose1 , dose2  from userregistered where district=?  and age=? order by aadhar_no limit ?");
				pst.setString(1, districtname);
				pst.setString(2, age);
				pst.setInt(3, doseAllocation);
				query_pst = pst.toString();
			} else {
				pst = con.prepareStatement(
						"select aadhar_no,name,Gender ,phone_number,dob,covid_affect,city,pincode,frontline,dose1 , dose2  from userregistered where district=? and frontline=? and covid_affect=? and age=? order by aadhar_no limit ?");
				pst.setString(1, districtname);
				pst.setString(2, covidW);
				pst.setString(3, covidAffected);
				pst.setString(4, age);
				pst.setInt(5, doseAllocation);
				query_pst = pst.toString();
			}

			rs = pst.executeQuery();
			out.println("<title>Result</title>");
			out.println("<script type=\"text/javascript\">");
			out.println("function getVaccinated(aadhar_num){ "
					+ 	"alert('the session did time out, please reconnect '+aadhar_num  );"
					+ "}"
					+ "</script>");
			out.println("<body>");
			out.println("<style>"); // start style

			out.println("body {"); // note leading brace
			out.println("color:#ccc;");
			out.println("background-color:  #203647;");
			out.println("border: 1px solid black;");
			out.println("}");
			out.println(".center");
			out.println("{");
			out.println("text-align:center;");
			out.println("}");
			out.println("div");
			out.println("{");
			out.println("text-align:center;");
			out.println("}");
			out.println("table, th,td");
			out.println("{");
			// out.println("display:inline-block;");
			out.println("text-align:center;");

			out.println("margin-left: auto;");
			out.println("margin-right: auto;");
			out.println("border : 3px solid #DDAF94;");
			out.println("}");

			out.println("</style>");

			out.println("<center><h1>Result of Search Page</h1></center>");
//			System.out.println("request value : "+request.getParameter("vaccinated"));
			//int vaccinated = request.getParameter("vaccinated") !=null ?Integer.parseInt(request.getParameter("vaccinated")):0;
			/*if(vaccinated>0) {
				out.println("<div style=\"font-size: 25px;\r\n" + 
						"    color: green;\r\n" + 
						"    font-weight: bold;\" >Vaccinated Successful </div>");
					out.println("<script>alert('Vaccinated Successfully');</script>");
			}*/
			out.println("<table  width=90% border= 1   >");
//			out.println("<tr><td ");
//			out.println("</td></tr>");
			out.println("<tr>");
//             out.println("<th>Ref_id</th>");
			out.println("<th>Aadhar Number</th>");
			out.println("<th>Name</th>");
			out.println("<th>Gender</th>");
			out.println("<th>Date Of Birth</th>");
//             out.println("<th>dob</th>");
			out.println("<th>Contact Number</th>");
			out.println("<th>Covid Affect</th>");
			out.println("<th>City</th>");
			out.println("<th>Pincode</th>");
			out.println("<th>Covid Warrior</th>");
			out.println("<th>Dose 1</th>");
			out.println("<th>Dose 2</th>");
			out.println("</tr>");
			out.println("</body>");
			while (rs.next()) {
				out.println("<tr>");
				out.println("<td>" + rs.getString("aadhar_no") + "</td> ");
				out.println("<td>" + rs.getString("name") + "</td> ");
				out.println("<td>" + rs.getString("Gender") + "</td> ");
				out.println("<td>" + rs.getString("dob") + "</td> ");
				out.println("<td>" + rs.getString("phone_number") + "</td> ");
				out.println("<td>" + rs.getString("covid_affect") + "</td> ");
				out.println("<td>" + rs.getString("city") + "</td> ");
				out.println("<td>" + rs.getString("pincode") + "</td> ");
				out.println("<td>" + rs.getString("frontline") + "</td> ");
				if(rs.getString("dose1") == null ||rs.getString("dose1")=="" ) {
					out.println("<td>" + "<a href='getVaccinated?aadharNo="+rs.getString("aadhar_no")+"&dose=1' style=\"background-color: #42a53f;;\r\n" + 
							"	border: 1px solid #f9f9f9;;\r\n" +  
							"	border-radius: 10px;\r\n" + 
							"	color: white;\r\n" + 
							"	padding: 6px;\r\n" + 
							"	text-align: left;\r\n" + 
							"	text-decoration: none;\r\n" + 
							"	display: inline-block;\r\n" + 
							"	font-size: 15px;\r\n" + 
							"	margin: 4px 2px;\r\n" + 
							"	cursor: pointer;font-weight: bold;\">Dose-1 </a>"+ "</td> ");
				}else {
					out.println("<td>" + rs.getString("dose1") + "</td> "); 
				}
				if(rs.getString("dose2") == null ||rs.getString("dose2")=="" ) {
					out.println("<td>" + "<a href='getVaccinated?aadharNo="+rs.getString("aadhar_no")+"&dose=2' style=\"background-color: #42a53f;;\r\n" + 
							"	border: 1px solid #f9f9f9;;\r\n" + 
							"	border-radius: 10px;\r\n" + 
							"	color: white;\r\n" + 
							"	padding: 6px;\r\n" + 
							"	text-align: left;\r\n" + 
							"	text-decoration: none;\r\n" + 
							"	display: inline-block;\r\n" + 
							"	font-size: 15px;\r\n" + 
							"	margin: 4px 2px;\r\n" + 
							"	cursor: pointer;font-weight: bold;\">Dose-2  </a>"+ "</td> ");
				}else {
						out.println("<td>" + rs.getString("dose2") + "</td> "); 
				}out.println("</tr>");
			}
			out.println("</table>");
			out.println("<br>");
			
			

		} catch (Exception e) {
			throw new ServletException("error", e);
		}
	}

	public void destory() {
		i = 0;
	}

}