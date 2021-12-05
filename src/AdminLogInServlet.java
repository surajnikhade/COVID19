import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import DBConnection.DBConnection;

public class AdminLogInServlet extends HttpServlet {
	PrintWriter pw = null;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html");
		// HttpSession session = request.getSession("seatnumber");
		pw = response.getWriter();
		String loginno = request.getParameter("loginid");
		String password = request.getParameter("Password");
		HttpSession session = request.getSession();
		session.setAttribute("loginid", loginno);
		if (isSeatExist(loginno)) {
			if (isValidUser(loginno, password)) {
				pw.write("<title> Login Successfully</title>");
				pw.println("<h1> </h1>");
				pw.write("<style>");
				pw.write(".class1{text-align: center;}");
				pw.write(".container{text-align: center; width: 410px; display: inline-block; box-shadow: 12px 12px 16px 5px #00000069; padding: 23px;}");
				pw.write("a{font-size: 24px; color: white;}");
				pw.write(".button1{background-color: #32c671; width: 90px; padding: 15px; border-radius: 5px; display: inline-block;}");
				pw.write("</style>");
				pw.write("<div class='class1'> ");
				pw.write("<div class='container'> ");
				pw.write("<div class='img1'>");
				pw.write("<img src='./successful.png'>");
				pw.write("</div>");
				pw.write("<h2>LoggedIn successfully</h2>");
				pw.write("<div class='button1'>");
				pw.write("<a href=\"viewResult.jsp\"> Continue</a>");

				pw.write("</div>");
				pw.write("</div>");
				pw.write("</div>");
			} else {
				pw.write("<title> Login Failed</title>");
				pw.write("<style>");
				pw.write(".class1{text-align: center;}");
				pw.write("img{width: 200px; min-width: 200px; height: 200px;}");
				pw.write(".container{text-align: center; width: 410px; display: inline-block; box-shadow: 12px 12px 16px 5px #00000069; padding: 23px;}");
				pw.write("a{font-size: 24px; color: white;}");
				pw.write(".button1{background-color: #d72828; width: 90px; padding: 15px; border-radius: 5px; display: inline-block;}");
				pw.write("</style>");
				pw.write("<div class='class1'>");
				pw.write("<div class='container'>");
				pw.write("<div class='img1'>");
				pw.write("<img src='./fail.png'>");
				pw.write("</div>");
				pw.write("<h2>Login Failed</h2>");
				pw.write("<p>Please enter valid Login id or Password</p>");
				pw.write("<div class='button1'>");
				pw.write("<a href=\"adminLogIn.html\">Back</a>");
				pw.write("</div>");
				pw.write("</div>");
				pw.write("</div>");
			}

		} else {
			pw.write("<title> Login Failed</title>");
			pw.write("<style>");
			pw.write(".class1{text-align: center;}");
			pw.write("img{width: 200px; min-width: 200px; height: 200px;}");
			pw.write(".container{text-align: center; width: 410px; display: inline-block; box-shadow: 12px 12px 16px 5px #00000069; padding: 23px;}");
			pw.write("a{font-size: 24px; color: white;}");
			pw.write(".button1{background-color: #d72828; width: 90px; padding: 15px; border-radius: 5px; display: inline-block;}");
			pw.write("</style>");
			pw.write("<div class='class1'>");
			pw.write("<div class='container'>");
			pw.write("<div class='img1'>");
			pw.write("<img src='./fail.png'>");
			pw.write("</div>");
			pw.write("<h2>Login Failed</h2>");
			pw.write("<p>Please enter valid Login id or Password</p>");
			pw.write("<div class='button1'>");
			pw.write("<a href=\"adminLogIn.html\">Back</a>");
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

		try {
			if (null != con) {
				stmt = con.createStatement();
				String sql = "SELECT * FROM adminIds where user_name = '" + demoseatnumber + "'";
				rs = stmt.executeQuery(sql);

				if (rs.next()) {
					isExist = true;
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

	// This method is use to check username and password in DB
	private boolean isValidUser(String admin_number, String password_number) {
		Connection con = DBConnection.getDBConnection();
		Statement stmt = null;
		ResultSet rs = null;
		boolean isValidUser = false;
		String demoadmin_number = "";
		String p_number = "";

		try {
			if (null != con) {
				stmt = con.createStatement();
				String sql = "SELECT  user_name, password FROM adminids where user_name = '" + admin_number + "'";
				rs = stmt.executeQuery(sql);

				if (rs.next()) {
					demoadmin_number = rs.getString("user_name");//
					p_number = rs.getString("password");
					if (admin_number.equals(demoadmin_number) && password_number.equals(p_number)) {
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
