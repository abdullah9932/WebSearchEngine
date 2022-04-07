<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ page import="Controller.SearchTextFiles"%>
<%@ page import="ExternalClasses.StdOut"%>
<%@ page import="java.util.HashMap" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<form action="#">
<p>Enter Your Query : 
<input type="text" name="Search"/>
</p>
<input type="submit" value="submit"/>
</form>

<%
String n = request.getParameter("Search");
if(!(n == null || (n.equals(""))))
{
	String query = n;
	String value, key;
	double start, end;
	HashMap<String, Integer> result = new HashMap();
	SearchTextFiles search = new SearchTextFiles();
	start = System.currentTimeMillis();
	result = search.searchString(query);
	end = System.currentTimeMillis();
	boolean checkResultIsNotEmpty = false;
	boolean haveResults = false;
%>

	<p>Search Result of Query " <%=query %>" is : </p>
	<p>Time Taken to search <strong><%=end-start %> ms</strong></p>
	<%
	int i = 0;
	for (HashMap.Entry<String, Integer> entry : result.entrySet()) 
	{
		if(i < 10)
		{
			if(entry.getValue()!= 0)
			{
				value = entry.getValue().toString();
				key = entry.getKey();
				%>
				<table>
				<tr>
				<td><p><strong>URL:</strong><%=key %></p> </td>
				</tr>
				<tr>
				<td><h4>Occurences:<h4><%=value %> </td>
				</tr>
				</table>
				<% 
				checkResultIsNotEmpty = false;
				haveResults = true;
				i++;
			}
				
			else
			{
				checkResultIsNotEmpty = true;
				break;
			}
		}
	}	
				%>
				
	<%
	if (checkResultIsNotEmpty == true && haveResults == false)
	{
	%>
		<h1>Run Edit Distance</h1>
		<%
	}
}
%>
</body>
</html>