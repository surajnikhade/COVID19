import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DBConnection.DBConnection;

public class UserRegisterationServlet extends HttpServlet {
	PrintWriter pw = null;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html");
		pw = response.getWriter();
		PreparedStatement pst;
		String query_pst;
		ResultSet rs;
		Connection con = DBConnection.getDBConnection();
		String aadharno = request.getParameter("aadharnumber");
		String mobileno = request.getParameter("mobilenumber");
		HttpSession session = request.getSession();
		session.setAttribute("aadharnumber", aadharno);
		String dose1 = null;
		String dose2 = null;
		Date dose1Date = null;
		Date dose2Date = null;
		String name = null;
		String age = null;
		String gender = null;
		String adharNo = null;
		String city = null;
		if (isSeatExist(aadharno)) {
			if (isValidUser(aadharno, mobileno)) {
				
				try{
					pst = con.prepareStatement(
							"select name,aadhar_no,gender,age,city,dose1 , dose2,dose1_vaccination_date,dose2_vaccination_date  from userregistered where aadhar_no=?");
					pst.setString(1, aadharno);
					rs = pst.executeQuery();
					if(rs.next()) {
						dose1 = rs.getString("dose1");
						dose2 = rs.getString("dose2");
						name = rs.getString("name");
						age = rs.getString("age");
						gender = rs.getString("gender");
						adharNo = rs.getString("aadhar_no");
						city = rs.getString("city");
						dose1Date = rs.getDate("dose1_vaccination_date");
						dose2Date = rs.getDate("dose2_vaccination_date");
						System.out.println("Dose1 : "+dose1 + " Dose 2 : "+dose2+" Date 1 : "+dose1Date+" Date 2 : "+dose2Date); 
					}
				}catch(Exception e) {
					
				}
				
				pw.println("<h1> </h1>");
				pw.write("<style>");
				pw.write(".class1{text-align: center;}");
				pw.write(
						".container{text-align: center; width: 600px; display: inline-block; box-shadow: 12px 12px 16px 5px #00000069; padding: 23px;}");
				pw.write("a{font-size: 24px; color: white;}");
				pw.write(
						".button1{background-color: #32c671; width: 90px; padding: 15px; border-radius: 5px; display: inline-block;}");
				pw.write("</style>");
				pw.write("<div class='class1'> ");
				pw.write("<div class='container'> ");
				pw.write("<div class='img1'>");
				pw.write("<img src='./successful.png'>");
				pw.write("</div>");
				pw.write("<div style=\"text-align: left;\"><h2>Registration completed successfully !<br><br> Aadhar Number : "+ aadharno +"</h2></div>");
				pw.write("<div style=\"text-align: left;\"><h2>Aadhar Holder Name : "+ name +"</h2></div>");
				if(null != dose1 && dose1.equalsIgnoreCase("Y")) {
					pw.println("<div style=\"text-align: left;\"><h2> Dose 1  taken on "+ dose1Date+"</h2>"
							+ "<a href='downloadCertificate?dose=Dose 1&name="+name+"&age="+age+"&gender="+gender+"&date="+dose1Date+"&adharNo="+adharNo+"&city="+city+"'"
							+"style=\"background-color: #42a53f;\r\n" + 
									"	border: 1px solid black;\r\n" + 
									"	border-radius: 10px;\r\n" + 
									"	color: white;\r\n" + 
									"	padding: 6px;\r\n" + 
									"	text-align: left;\r\n" + 
									"	text-decoration: none;\r\n" + 
									"	display: inline-block;\r\n" + 
									"	font-size: 15px;\r\n" + 
									"	margin: 4px 2px;\r\n" + 
									"	cursor: pointer;font-weight: bold;\">Download Dose 1 Certificate  </a>"+ "</div> ");
				}else {
					pw.println("<div style=\"text-align: left;\"><h2> Dose 1 not Yet taken...</h2></div>");
				}
				if(null != dose2 && dose2.equalsIgnoreCase("Y")) {
					pw.println("<div style=\"text-align: left;\"><h2> Dose 2  taken on "+ dose2Date+"</h2>"
							+ "<a href='downloadCertificate?dose=Dose 2&name="+name+"&age="+age+"&gender="+gender+"&date="+dose2Date+"&adharNo="+adharNo+"&city="+city+"'"
							+"style=\"background-color: #42a53f;\r\n" + 
							"	border: 1px solid black;\r\n" + 
							"	border-radius: 10px;\r\n" + 
							"	color: white;\r\n" + 
							"	padding: 6px;\r\n" + 
							"	text-align: left;\r\n" + 
							"	text-decoration: none;\r\n" + 
							"	display: inline-block;\r\n" + 
							"	font-size: 15px;\r\n" + 
							"	margin: 4px 2px;\r\n" + 
							"	cursor: pointer;font-weight: bold;\">Download Dose 2 Certificate  </a>"+ "</div> ");
				}else {
					pw.println("<div style=\"text-align: left;\"><h2> Dose 2 not Yet taken...</h2></div>");
				}
				pw.write("<div class='button1'>");
				pw.write("<a href=\"index.html\"> Continue</a>");

				pw.write("</div>");
				pw.write("</div>");
				pw.write("</div>");

			} else {
				pw.write("<style>");
				pw.write(".class1{text-align: center;}");
				pw.write("img{width: 200px; min-width: 200px; height: 200px;}");
				pw.write(
						".container{text-align: center; width: 410px; display: inline-block; box-shadow: 12px 12px 16px 5px #00000069; padding: 23px;}");
				pw.write("a{font-size: 24px; color: white;}");
				pw.write(
						".button1{background-color: #d72828; width: 90px; padding: 15px; border-radius: 5px; display: inline-block;}");
				pw.write("</style>");
				pw.write("<div class='class1'>");
				pw.write("<div class='container'>");
				pw.write("<div class='img1'>");
				pw.write("<img src='./fail.png'>");
				pw.write("</div>");
				pw.write("<h2>Registration Failed</h2>");
				pw.write("<p>Please enter valid Aadhar number or Mobile Number</p>");
				pw.write("<div class='button1'>");
				pw.write("<a href=\"index.html\">Back</a>");
				pw.write("</div>");
				pw.write("</div>");
				pw.write("</div>");
			}

		} else {
			pw.write("<style>");
			pw.write(".class1{text-align: center;}");
			pw.write("img{width: 200px; min-width: 200px; height: 200px;}");
			pw.write(
					".container{text-align: center; width: 410px; display: inline-block; box-shadow: 12px 12px 16px 5px #00000069; padding: 23px;}");
			pw.write("a{font-size: 24px; color: white;}");
			pw.write(
					".button1{background-color: #d72828; width: 90px; padding: 15px; border-radius: 5px; display: inline-block;}");
			pw.write("</style>");
			pw.write("<div class='class1'>");
			pw.write("<div class='container'>");
			pw.write("<div class='img1'>");
			pw.write("<img src='./fail.png'>");
			pw.write("</div>");
			pw.write("<h2>Registration Failed</h2>");
			pw.write("<p>Please enter valid Aadhar number or Mobile Number</p>");
			pw.write("<div class='button1'>");
			pw.write("<a href=\"index.html\">Back</a>");
			pw.write("</div>");
			pw.write("</div>");
			pw.write("</div>");
		}

	}

// this method is use to check wheither username exists in DB or not
	private boolean isSeatExist(String demoseatnumber) {
		Connection con = DBConnection.getDBConnection();
		Statement stmt = null;
		ResultSet rs = null;
		boolean isExist = false;
		UserInfo userInfo = new UserInfo();

		try {
			if (null != con) {
				stmt = con.createStatement();
				String sql = "SELECT * FROM userInfo where aadhar_no = '" + demoseatnumber + "'";
				rs = stmt.executeQuery(sql);

				if (rs.next()) {
					isExist = true;
					userInfo.setAadharNo(rs.getString("aadhar_no"));
					userInfo.setName(rs.getString("name"));
					userInfo.setGender(rs.getString("Gender"));
					userInfo.setPhoneNo(rs.getString("phone_number"));
					userInfo.setDob(rs.getDate("dob"));
					userInfo.setAge(rs.getString("age"));
					userInfo.setCovidAffect(rs.getString("covid_affect"));
					userInfo.setState(rs.getString("state"));
					userInfo.setDistrict(rs.getString("district"));
					userInfo.setCity(rs.getString("city"));
					userInfo.setPincode(rs.getString("pincode"));
					userInfo.setFrontline(rs.getString("frontline"));

					insertMethod(userInfo);
				} else {
					isExist = false;
				}
			} else {
				pw.println("<h1> Error while Connecting DB </h1>");
				pw.write("<br><a href=\"index.html\"> Back</a>");
			}
		} catch (Exception e) {
			e.printStackTrace();
			pw.println("<h1> Error while checking Seat Number </h1>");
			pw.write("<br><a href=\"index.html\"> Back</a>");
		} finally {
			try {
				stmt.close();
				rs.close();
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return isExist;
	}

	private void insertMethod(UserInfo userInfo) {
		Connection con = DBConnection.getDBConnection();
// Statement stmt = null;
// ResultSet rs = null;

		try {
// stmt = con.createStatement();
			java.util.Date date=new java.util.Date();
			java.sql.Date currenDate=new java.sql.Date(date.getTime());
			String sqlIn = "insert into userregistered(aadhar_no, name, Gender,phone_number , dob,age, covid_affect, state, district, city, pincode, frontline,registration_date) values (? , ? , ?  ,  ? , ?,? , ? , ? , ?, ? , ? , ? ,?)";
			PreparedStatement ps = con.prepareStatement(sqlIn);
			ps.setString(1, userInfo.getAadharNo());
			ps.setString(2, userInfo.getName());
			ps.setString(3, userInfo.getGender());
			ps.setString(4, userInfo.getPhoneNo());
			ps.setDate(5, userInfo.getDob());
			ps.setString(6, userInfo.getAge());
			ps.setString(7, userInfo.getCovidAffect());
			ps.setString(8, userInfo.getState());
			ps.setString(9, userInfo.getDistrict());
			ps.setString(10, userInfo.getCity());
			ps.setString(11, userInfo.getPincode());
			ps.setString(12, userInfo.getFrontline());
			ps.setDate(13, currenDate);
			ps.executeUpdate();
			System.out.print("INSERTED SUCCESSFULLY");

		} catch (Exception e) {
			System.out.print("ERROR WHILE INSERTING DATA IN DB" + e);
		}
	}

// This method is use to check username and password in DB
	private boolean isValidUser(String aadhar_number, String mobile_number) {
		Connection con = DBConnection.getDBConnection();
		Statement stmt = null;
		ResultSet rs = null;
		boolean isValidUser = false;
		String demoaadhar_number = "";
		String m_number = "";

		try {
			if (null != con) {
				stmt = con.createStatement();
				String sql = "SELECT  aadhar_no, phone_number FROM userInfo where aadhar_no = '" + aadhar_number + "'";
				rs = stmt.executeQuery(sql);

				if (rs.next()) {
					demoaadhar_number = rs.getString("aadhar_no");//
					m_number = rs.getString("phone_number");
					if (aadhar_number.equals(demoaadhar_number) && mobile_number.equals(m_number)) {
						isValidUser = true;
					} else {
						isValidUser = false;
					}
				} else {
					isValidUser = false;
				}
			} else {
				pw.println("<h1> Error while Connecting DB </h1>");
				pw.write("<br><a href=\"index.html\"> Back</a>");
			}
		} catch (Exception e) {
			e.printStackTrace();
			pw.println("<h1> Error while Authenticating User </h1>");
			pw.write("<br><a href=\"index.html\"> Back</a>");
		} finally {
			try {
				stmt.close();
				rs.close();
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
				pw.println("<h1> Here </h1>");
			}
		}
		return isValidUser;
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		doPost(request, response);
	}
}