
<%@ page import="alumno.Household" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'household.label', default: 'Household')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-household" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-household" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list household">
			
				<g:if test="${householdInstance?.address}">
				<li class="fieldcontain">
					<span id="address-label" class="property-label"><g:message code="household.address.label" default="Address" /></span>
					
						<span class="property-value" aria-labelledby="address-label"><g:fieldValue bean="${householdInstance}" field="address"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${householdInstance?.city}">
				<li class="fieldcontain">
					<span id="city-label" class="property-label"><g:message code="household.city.label" default="City" /></span>
					
						<span class="property-value" aria-labelledby="city-label"><g:fieldValue bean="${householdInstance}" field="city"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${householdInstance?.state}">
				<li class="fieldcontain">
					<span id="state-label" class="property-label"><g:message code="household.state.label" default="State" /></span>
					
						<span class="property-value" aria-labelledby="state-label"><g:fieldValue bean="${householdInstance}" field="state"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${householdInstance?.zip}">
				<li class="fieldcontain">
					<span id="zip-label" class="property-label"><g:message code="household.zip.label" default="Zip" /></span>
					
						<span class="property-value" aria-labelledby="zip-label"><g:fieldValue bean="${householdInstance}" field="zip"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${householdInstance?.phoneNumber}">
				<li class="fieldcontain">
					<span id="phoneNumber-label" class="property-label"><g:message code="household.phoneNumber.label" default="Phone Number" /></span>
					
						<span class="property-value" aria-labelledby="phoneNumber-label"><g:fieldValue bean="${householdInstance}" field="phoneNumber"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${householdInstance?.parents}">
				<li class="fieldcontain">
					<span id="parents-label" class="property-label"><g:message code="household.parents.label" default="Parents" /></span>
					
						<g:each in="${householdInstance.parents}" var="p">
						<span class="property-value" aria-labelledby="parents-label"><g:link controller="parent" action="show" id="${p.id}">${p?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
				<g:if test="${householdInstance?.registration}">
				<li class="fieldcontain">
					<span id="registration-label" class="property-label"><g:message code="household.registration.label" default="Registration" /></span>
					
						<span class="property-value" aria-labelledby="registration-label"><g:link controller="registration" action="show" id="${householdInstance?.registration?.id}">${householdInstance?.registration?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${householdInstance?.safehome}">
				<li class="fieldcontain">
					<span id="safehome-label" class="property-label"><g:message code="household.safehome.label" default="Safehome" /></span>
					
						<span class="property-value" aria-labelledby="safehome-label"><g:formatBoolean boolean="${householdInstance?.safehome}" /></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${householdInstance?.id}" />
					<g:link class="edit" action="edit" id="${householdInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
