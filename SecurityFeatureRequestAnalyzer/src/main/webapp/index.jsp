<%@page import="org.se.rub.github.monitor.BackgroundJobManager"%>

<html>
<body>
<h2><%= "GitHub Feature Request Monitor" %></h2>
	<form method = "post">
         Query frequency (in seconds): <input type = "number" name = "frequency">
         <br />
         GitHub access token: <input type = "text" name = "github_token" />
         <br />
         <input type = "submit" value = "Submit" onclick="<%
         	String[] frequency = request.getParameterMap().get("frequency");
         	if(frequency != null) {
             	System.out.println(frequency.length);
         		BackgroundJobManager.INSTANCE.setFrequency(Integer.valueOf(frequency[0]));
         	}
         %>"/>
      </form>
</body>
</html>
