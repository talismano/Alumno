<%@ page import="alumno.Household" %>



<div class="fieldcontain ${hasErrors(bean: householdInstance, field: 'address', 'error')} ">
	<label for="address">
		<g:message code="household.address.label" default="Address" />
		
	</label>
	<g:textField name="address" value="${householdInstance?.address}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: householdInstance, field: 'city', 'error')} ">
	<label for="city">
		<g:message code="household.city.label" default="City" />
		
	</label>
	<g:textField name="city" value="${householdInstance?.city}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: householdInstance, field: 'parents', 'error')} ">
	<label for="parents">
		<g:message code="household.parents.label" default="Parents" />
		
	</label>
	
<ul class="one-to-many">
<g:each in="${householdInstance?.parents?}" var="p">
    <li><g:link controller="parent" action="show" id="${p.id}">${p?.encodeAsHTML()}</g:link></li>
</g:each>
<li class="add">
<g:link controller="parent" action="create" params="['household.id': householdInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'parent.label', default: 'Parent')])}</g:link>
</li>
</ul>

</div>

<div class="fieldcontain ${hasErrors(bean: householdInstance, field: 'phoneNumber', 'error')} ">
	<label for="phoneNumber">
		<g:message code="household.phoneNumber.label" default="Phone Number" />
		
	</label>
	<g:textField name="phoneNumber" value="${householdInstance?.phoneNumber}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: householdInstance, field: 'registration', 'error')} required">
	<label for="registration">
		<g:message code="household.registration.label" default="Registration" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="registration" name="registration.id" from="${alumno.Registration.list()}" optionKey="id" required="" value="${householdInstance?.registration?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: householdInstance, field: 'safehome', 'error')} ">
	<label for="safehome">
		<g:message code="household.safehome.label" default="Safehome" />
		
	</label>
	<g:checkBox name="safehome" value="${householdInstance?.safehome}" />
</div>

<div class="fieldcontain ${hasErrors(bean: householdInstance, field: 'state', 'error')} ">
	<label for="state">
		<g:message code="household.state.label" default="State" />
		
	</label>
	<g:textField name="state" value="${householdInstance?.state}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: householdInstance, field: 'zip', 'error')} required">
	<label for="zip">
		<g:message code="household.zip.label" default="Zip" />
		<span class="required-indicator">*</span>
	</label>
	<g:field type="number" name="zip" required="" value="${fieldValue(bean: householdInstance, field: 'zip')}"/>
</div>

