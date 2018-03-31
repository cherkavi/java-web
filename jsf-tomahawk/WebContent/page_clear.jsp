<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>

<html>
	<head>
		<title>Clear</title>
	</head>
	<body>
		<f:view>
			<h:form>
				<f:verbatim>
					<h3> Clear: </h3>
				</f:verbatim>
				<h:commandButton value="back" action="#{checkINN.goBack}" />
			</h:form>
		</f:view>
	</body>
</html>