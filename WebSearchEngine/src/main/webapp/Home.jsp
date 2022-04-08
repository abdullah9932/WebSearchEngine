<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ page import="Search.SearchTextFiles"%>
<%@ page import="SpellChecker.Spell_1"%>
<%@ page import="Controller.WebEngine"%>
<%@ page import="ExternalClasses.StdOut"%>
<%@ page import="java.util.HashMap" %>
<!DOCTYPE html>
<html>
<style>
.searchbar{
	height:45px;
	border: none;
	width: 500px;
	font-size:16px;
	outline:none;
}
.bar{
  margin:0 auto;
  width:575px;
  border-radius:30px;
  border:1px solid #dcdcdc;
}

.bar:hover{
  box-shadow: 1px 1px 8px 1px #dcdcdc;
}
.bar:focus-within{
  box-shadow: 1px 1px 8px 1px #dcdcdc;
  outline:none;
}

.button{
  background-color: #f5f5f5;
  border:none;
  color:#707070;
  font-size:15px;
  padding: 10px 20px;
  margin:5px;
  border-radius:4px;
  outline:none;
}

.button:hover{
  border: 1px solid #c8c8c8;
  padding: 9px 19px;
  color:#808080;
}
.button:focus{
  border:1px solid #4885ed;
  padding: 9px 19px;
}
</style>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<center>
<h1>ForageX</h1>
<form action="#">
<div class="bar">
<input type="text" class= "searchbar" name="Search" placeholder="Search"/>
</div>

<input class="button" type="submit" value="Search"/>
</form>
</center>
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

	<p>Search Result of Query "<%=query %>" is : </p>
	<p>Time Taken to search <strong><%=end-start %> ms</strong></p>
	
	<%
	int i = 0;
	for (HashMap.Entry<String, Integer> entry : result.entrySet()) 
	{
		if(i < 10)
		{
			if(entry.getValue()!= 0 && entry.getKey() != null)
			{
				value = entry.getValue().toString();
				key = entry.getKey();
	%>
	<table>
	<tr>
	<td><%=i+1%>)  <a href=<%=key %>><%=key %></a> </td>
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
		
			String dictionaryFileName = WebEngine.mainPath + ("/src/Files/dictionary.txt");
	
	            Spell_1 corrector = new Spell_1();
	            
	            corrector.dict_file(dictionaryFileName);
	
	            String suggestion = corrector.suggestSimilarWord(query);
	
	            if (suggestion == null) {
	
	                suggestion = "No similar word found";
	
	            }
	%>
	
	
Auto Corrected word to:<%=suggestion %>

	<%
	result = search.searchString(suggestion);
	    			
	    int j = 0;
	    for (HashMap.Entry<String, Integer> entry : result.entrySet()) 
	    {
	    	if(j < 10)
	    	{
	    		if(entry.getValue()!= 0)
	    		{
	    			value = entry.getValue().toString();
	    			key = entry.getKey();
	    			
	  %>
	    						<table>
	    						<tr>
	    						<td><%=j+1%>)  <a href=<%=key %>><%=key %></a> </td>
	    						</tr>
	    						</table>
	    									<% 
	    									j++;
	    					}
	    						
	    					else
	    					{
	    						break;
	    					}
	    				}
	    			}	
	
	            }	
	%>
	<%
	}

	%>
</body>
</html>