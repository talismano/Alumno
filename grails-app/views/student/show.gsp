
<%@ page import="alumno.Student" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'student.label', default: 'Student')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-student" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-student" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list student">
			
				<g:if test="${studentInstance?.email}">
				<li class="fieldcontain">
					<span id="email-label" class="property-label"><g:message code="student.email.label" default="Email" /></span>
					
						<span class="property-value" aria-labelledby="email-label"><g:fieldValue bean="${studentInstance}" field="email"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${studentInstance?.firstName}">
				<li class="fieldcontain">
					<span id="firstName-label" class="property-label"><g:message code="student.firstName.label" default="First Name" /></span>
					
						<span class="property-value" aria-labelledby="firstName-label"><g:fieldValue bean="${studentInstance}" field="firstName"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${studentInstance?.grade}">
				<li class="fieldcontain">
					<span id="grade-label" class="property-label"><g:message code="student.grade.label" default="Grade" /></span>
					
						<span class="property-value" aria-labelledby="grade-label"><g:fieldValue bean="${studentInstance}" field="grade"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${studentInstance?.lastName}">
				<li class="fieldcontain">
					<span id="lastName-label" class="property-label"><g:message code="student.lastName.label" default="Last Name" /></span>
					
						<span class="property-value" aria-labelledby="lastName-label"><g:fieldValue bean="${studentInstance}" field="lastName"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${studentInstance?.phoneNumber}">
				<li class="fieldcontain">
					<span id="phoneNumber-label" class="property-label"><g:message code="student.phoneNumber.label" default="Phone Number" /></span>
					
						<span class="property-value" aria-labelledby="phoneNumber-label"><g:fieldValue bean="${studentInstance}" field="phoneNumber"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${studentInstance?.registration}">
				<li class="fieldcontain">
					<span id="registration-label" class="property-label"><g:message code="student.registration.label" default="Registration" /></span>
					
						<span class="property-value" aria-labelledby="registration-label"><g:link controller="registration" action="show" id="${studentInstance?.registration?.id}">${studentInstance?.registration?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${studentInstance?.id}" />
					<g:link class="edit" action="edit" id="${studentInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
