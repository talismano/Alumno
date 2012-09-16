
<%@ page import="alumno.Registration" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'registration.label', default: 'Registration')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-registration" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-registration" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list registration">
			
				<g:if test="${registrationInstance?.dateCreated}">
				<li class="fieldcontain">
					<span id="dateCreated-label" class="property-label"><g:message code="registration.dateCreated.label" default="Date Created" /></span>
					
						<span class="property-value" aria-labelledby="dateCreated-label"><g:formatDate date="${registrationInstance?.dateCreated}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${registrationInstance?.households}">
				<li class="fieldcontain">
					<span id="households-label" class="property-label"><g:message code="registration.households.label" default="Household 1" /></span>
						<g:each status="i" in="${registrationInstance.households}" var="h">
                            <g:if test="${i > 0}">
                                </li><li class="fieldcontain"><span id="households-label" class="property-label"><g:message code="registration.households.label" default="Household 2" /></span>
                            </g:if>

						<span class="property-value" aria-labelledby="households-label"><g:link controller="household" action="show" id="${h.id}">${h?.encodeAsHTML()}</g:link></span>
                            <g:if test="${h?.parents}">
                                <li class="fieldcontain">
                                    <span id="parents-label" class="property-label"><g:message code="h.parents.label" default="Parents" /></span>
                                    <g:each in="${h.parents}" var="p">
                                        <span class="property-value" aria-labelledby="parents-label"><g:link controller="parent" action="show" id="${p.id}">${p?.encodeAsHTML()}</g:link></span>
                                    </g:each>

                                </li>
                            </g:if>
                        </g:each>
					
				</li>
				</g:if>
			
				<g:if test="${registrationInstance?.ipAddress}">
				<li class="fieldcontain">
					<span id="ipAddress-label" class="property-label"><g:message code="registration.ipAddress.label" default="Ip Address" /></span>
					
						<span class="property-value" aria-labelledby="ipAddress-label"><g:fieldValue bean="${registrationInstance}" field="ipAddress"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${registrationInstance?.students}">
				<li class="fieldcontain">
					<span id="students-label" class="property-label"><g:message code="registration.students.label" default="Students" /></span>
					
						<g:each in="${registrationInstance.students}" var="s">
                            <span class="property-value" aria-labelledby="students-label"><g:link controller="student" action="show" id="${s.id}">${s?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			</ol>
            <g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${registrationInstance?.id}" />
					<g:link class="edit" action="edit" id="${registrationInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
