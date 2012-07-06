<%@ page import="alumno.Parent" %>



<div class="fieldcontain ${hasErrors(bean: parentInstance, field: 'email', 'error')} ">
	<label for="email">
		<g:message code="parent.email.label" default="Email" />
		
	</label>
	<g:textField name="email" value="${parentInstance?.email}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: parentInstance, field: 'firstName', 'error')} ">
	<label for="firstName">
		<g:message code="parent.firstName.label" default="First Name" />
		
	</label>
	<g:textField name="firstName" value="${parentInstance?.firstName}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: parentInstance, field: 'grade', 'error')} required">
	<label for="grade">
		<g:message code="parent.grade.label" default="Grade" />
		<span class="required-indicator">*</span>
	</label>
	<g:field type="number" name="grade" required="" value="${fieldValue(bean: parentInstance, field: 'grade')}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: parentInstance, field: 'household', 'error')} required">
	<label for="household">
		<g:message code="parent.household.label" default="Household" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="household" name="household.id" from="${alumno.Household.list()}" optionKey="id" required="" value="${parentInstance?.household?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: parentInstance, field: 'lastName', 'error')} ">
	<label for="lastName">
		<g:message code="parent.lastName.label" default="Last Name" />
		
	</label>
	<g:textField name="lastName" value="${parentInstance?.lastName}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: parentInstance, field: 'phoneNumber', 'error')} ">
	<label for="phoneNumber">
		<g:message code="parent.phoneNumber.label" default="Phone Number" />
		
	</label>
	<g:textField name="phoneNumber" value="${parentInstance?.phoneNumber}"/>
</div>

