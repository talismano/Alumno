
<%@ page import="alumno.Parent" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'parent.label', default: 'Parent')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-parent" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-parent" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list parent">
			
				<g:if test="${parentInstance?.email}">
				<li class="fieldcontain">
					<span id="email-label" class="property-label"><g:message code="parent.email.label" default="Email" /></span>
					
						<span class="property-value" aria-labelledby="email-label"><g:fieldValue bean="${parentInstance}" field="email"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${parentInstance?.firstName}">
				<li class="fieldcontain">
					<span id="firstName-label" class="property-label"><g:message code="parent.firstName.label" default="First Name" /></span>
					
						<span class="property-value" aria-labelledby="firstName-label"><g:fieldValue bean="${parentInstance}" field="firstName"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${parentInstance?.grade}">
				<li class="fieldcontain">
					<span id="grade-label" class="property-label"><g:message code="parent.grade.label" default="Grade" /></span>
					
						<span class="property-value" aria-labelledby="grade-label"><g:fieldValue bean="${parentInstance}" field="grade"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${parentInstance?.household}">
				<li class="fieldcontain">
					<span id="household-label" class="property-label"><g:message code="parent.household.label" default="Household" /></span>
					
						<span class="property-value" aria-labelledby="household-label"><g:link controller="household" action="show" id="${parentInstance?.household?.id}">${parentInstance?.household?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${parentInstance?.lastName}">
				<li class="fieldcontain">
					<span id="lastName-label" class="property-label"><g:message code="parent.lastName.label" default="Last Name" /></span>
					
						<span class="property-value" aria-labelledby="lastName-label"><g:fieldValue bean="${parentInstance}" field="lastName"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${parentInstance?.phoneNumber}">
				<li class="fieldcontain">
					<span id="phoneNumber-label" class="property-label"><g:message code="parent.phoneNumber.label" default="Phone Number" /></span>
					
						<span class="property-value" aria-labelledby="phoneNumber-label"><g:fieldValue bean="${parentInstance}" field="phoneNumber"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${parentInstance?.id}" />
					<g:link class="edit" action="edit" id="${parentInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
