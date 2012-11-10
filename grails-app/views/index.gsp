<!doctype html>
<%@ page import="org.springframework.util.ClassUtils" %>
<%@ page import="grails.plugin.searchable.internal.lucene.LuceneUtils" %>
<%@ page import="grails.plugin.searchable.internal.util.StringQueryUtils" %>
<html>
	<head>
        <meta name="layout" content="main"/>
        <r:require modules="bootstrap"/>
        <title>Home</title>
	</head>
	<body>

<section id="main" class="span9">

    <div class="hero-unit">
        <h1>Alumno</h1>

        <p>Wildcats directory application</p>
    </div>

    <div class="row">
        <div class="span6 offset2">
            <g:link controller="Registration" action="importOnlineData"><input type="button" value="Import Online Data" class="btn"/></g:link>
            <g:link controller="Registration" action="importHSData"><input type="button" value="Import High School Data" class="btn"/></g:link>
            <g:link controller="Registration" action="createPDF"><input type="button" value="Create Directory" class="btn"/></g:link>
            <g:link controller="Registration" action="concatenatePDF"><input type="button" value="Concatenate PDFs" class="btn"/></g:link>
            <g:link controller="Registration" action="createDistributionSpreadsheet"><input type="button" value="Create Distribution Spreadsheet" class="btn"/></g:link>
        </div>
    </div>
    <div class="row"><br></div>
    <div class="row">
        <div class="span6 offset2">
        <g:form url='[controller: "searchable", action: "index"]' id="searchableForm" name="searchableForm" method="get">
            <g:textField name="q" value="${params.q}" size="50"/> <input type="submit" value="Search" />
        </g:form>
        </div>
    </div>
    <g:if test="${haveResults}">
            <div class="results">
                <g:each var="result" in="${searchResult.results}" status="index">
                    <div class="result">
                        <g:set var="className" value="${ClassUtils.getShortName(result.getClass())}" />
                        <g:set var="link" value="${createLink(controller: className[0].toLowerCase() + className[1..-1], action: 'show', id: result.id)}" />
                        <div class="name"><a href="${link}">${className} #${result.id}</a></div>
                        <g:set var="desc" value="${result.toString()}" />
                        <g:if test="${desc.size() > 120}"><g:set var="desc" value="${desc[0..120] + '...'}" /></g:if>
                        <div class="desc">${desc.encodeAsHTML()}</div>
                        <div class="displayLink">${link}</div>
                    </div>
                </g:each>
            </div>

            <div>
                <div class="paging">
                    <g:if test="${haveResults}">
                        Page:
                        <g:set var="totalPages" value="${Math.ceil(searchResult.total / searchResult.max)}" />
                        <g:if test="${totalPages == 1}"><span class="currentStep">1</span></g:if>
                        <g:else><g:paginate controller="searchable" action="index" params="[q: params.q]" total="${searchResult.total}" prev="&lt; previous" next="next &gt;"/></g:else>
                    </g:if>
                </div>
            </div>
        </g:if>
        <div id="controller-list" role="navigation">
            <h2>Available Controllers:</h2>
            <ul>
                <g:each var="c" in="${grailsApplication.controllerClasses.sort { it.fullName } }">
                    <li class="controller"><g:link controller="${c.logicalPropertyName}">${c.fullName}</g:link></li>
                </g:each>
            </ul>
        </div>

    </div>

</section>

</body>
</html>