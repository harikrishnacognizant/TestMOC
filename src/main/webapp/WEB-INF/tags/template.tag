<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ attribute name="breadcrumb" required="true" rtexprvalue="true"%>
<%@ attribute name="hidesearch" required="false" rtexprvalue="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
	<title>MD Process Engine</title>
	<link href="${pageContext.request.contextPath}/resources/layout.css" rel="stylesheet" type="text/css" />
	<script src="http://ajax.googleapis.com/ajax/libs/dojo/1.6/dojo/dojo.xd.js"   djConfig="parseOnLoad: true,locale: 'en'"></script>
	 <script src="${pageContext.request.contextPath}/resources/MDMJavaScript.js" type="text/javascript"></script>
	  <link rel="stylesheet" type="text/css" href="http://ajax.googleapis.com/ajax/libs/dojo/1.6/dijit/themes/tundra/tundra.css"/>			
</head>
<body>
	<div id="header"><div id="app_title">MDM On Cloud</div></div>
	<div id="navigation">${breadcrumb}</div>
	<div id="content">	
		<jsp:doBody/>	
	</div>
	<div id="footer"></div>
</body>
</html>
