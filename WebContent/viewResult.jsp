<%@page import="java.io.PrintWriter"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@page import="DBConnection.DBConnection"%>

<!DOCTYPE html>
<html>
<head>
<title></title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
	integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh"
	crossorigin="anonymous">
<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
	integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"
	crossorigin="anonymous"></script>
<script
	src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
	integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
	crossorigin="anonymous"></script>
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
	integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
	crossorigin="anonymous"></script>
	
<style>
body{
background-image: url('coviii19.jpg');
  background-position: ;
  background-repeat: no-repeat;
  background-size: cover;
height: 50%;


color:#fff;
font-size:25px;
background-color:#000000;
}
.form-group{
text-align:center;
}
td{
padding:10px;
}

</style>	
</head>
<body>
	<div class="container">
		<div class="form-group">
			<h2>District Regional Information </h2>
		</div>
		<br>
		<div class="form-group">
			<form method="get" action="Servlet">
				<div class="form-group">
					<h3></h3>

					<%
						PreparedStatement pst;
						ResultSet rs;
						try {
							Connection con = DBConnection.getDBConnection();
							pst = con.prepareStatement("select distinct district from userregistered");
							rs = pst.executeQuery();

							if (rs.next()) {
								out.println("<table width='100%' border='2'><tr>");
								out.println("<td><strong>Choose District Name<strong>");
								//out.println("<td>");
								out.println("<select name='combo'>");

								do {
									String districtname = rs.getString("district");
									out.println("<option value='" + districtname + "'>" + districtname + "</option>");

								} while (rs.next());
								out.println("</select>");
								rs.close();

								out.println("</td>");

							} else {
								out.println("<tr>");
								out.println("<td colspan=2 align=right>");
								out.println("Sorry table is Empty");
								out.println("</td>");
							}
							out.println("<td width='10px'>Covid Worrior <select  name='covid'> ");
							out.println(" <option value='all'>All</option>");
							out.println(" <option value='0'>0</option>");
							out.println(" <option value='1'>1</option>");
							out.println("</select> </td> </tr>");
							
							out.println("<tr><td width='10px'>Covid Affect <select  name='covid-affected'> ");
							out.println(" <option value='all'>All</option>");
							out.println(" <option value='0'>0</option>");
							out.println(" <option value='1'>1</option>");
							out.println("</select> </td> ");
							
							out.println("<td width='10px'>Age <select  name='age'> ");
							out.println(" <option value='all'>All</option>");
							out.println(" <option value='Below 18'> Below 18</option>");
							out.println(" <option value='18-45'>18-45</option>");
							out.println(" <option value='46-65'>46-65</option>");
							out.println(" <option value='Above 65'> Above 65</option>");
							out.println("</select> </td> </tr>");
							
							out.println("<tr><td width='10px'>");
							out.println(" <label for='doseValue'> Dose Allocation	</label>");
							out.println("<input type='number' id='doseValue' name='doseValue' min='1'  style='width: 75px;'>");
							
							out.println("</td></tr> ");
							
							
							out.println("<tr><br><br><td colspan=2 margin-left=400px  align=center><input type=submit  value=Retrieve></td></tr>");
							out.println("</form>");
						} catch (Exception e) {
							e.printStackTrace(); // Or System.out.println(e);
						}
					%>
				</div>
			</form>
		</div>
	</div>
</body>
</html>