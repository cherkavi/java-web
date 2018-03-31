<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>

<html>
<head>
	<title>check INN</title>
	<script type="text/javascript">
		function unvisible(componentName){
			document.getElementById(componentName).style.visibility="hidden";
		}
	</script>
        <style type="text/css">
            .button_red{
            	background-color: red;
            }
            .button_green{
            	background-color: green;
            }
        </style>
</head>
<body>
	<f:view>
		<h:form id="form_main">
	        <h:panelGrid>
	            <h:panelGroup>
	            	<h:panelGroup id="panel_group" binding="#{simpleBean.component}"></h:panelGroup>
	            	<f:verbatim>
	            		<hr />
	            	</f:verbatim>
	            	<f:verbatim>
						<b>input value</b>
					</f:verbatim>
	                <h:inputText value="#{checkINN.fieldValue}" id="input_value" validator="validators.InnValidator" required="false" onkeydown="unvisible('form_main:result')" />
	                <h:message for="input_value" />
	                
	                <t:saveState value="#{checkINN.showResult}" />
	                <h:panelGroup rendered="#{checkINN.showResult}" id="result">
		                <h:panelGroup rendered="#{checkINN.checkError ne null}">
		                    <h:outputText value="Error value: #{checkINN.checkError}"  />
		                </h:panelGroup>
		
						<t:saveState value="#{checkINN.inBlackList}" />
		                <h:panelGroup rendered="#{checkINN.checkError eq null}" >
		                    <h:commandButton value="black list" rendered="#{checkINN.inBlackList}" actionListener="#{checkINN.actionBlack}" action="#{checkINN.goToBlack}" styleClass="button_red" />
		                    <h:commandButton value="clear" rendered="#{!checkINN.inBlackList}" actionListener="#{checkINN.actionClear}" action="#{checkINN.goToClear}" styleClass="button_green" />
		                </h:panelGroup>
	                </h:panelGroup>
	                
	            </h:panelGroup>
	
	            <h:panelGroup>
	                <h:commandButton value="check" action="#{checkINN.checkINN}" />
	            </h:panelGroup>
	
	        </h:panelGrid>
		</h:form>
	</f:view>
</body>
</html>