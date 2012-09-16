<!doctype html>
<%@ page import="org.springframework.util.ClassUtils" %>
<%@ page import="grails.plugin.searchable.internal.lucene.LuceneUtils" %>
<%@ page import="grails.plugin.searchable.internal.util.StringQueryUtils" %>
<html>
	<head>
		<meta name="layout" content="main"/>
        <r:require modules="bootstrap"/>
		<title>Welcome to Grails</title>
		<style type="text/css" media="screen">
			#status {
				background-color: #eee;
				border: .2em solid #fff;
				margin: 2em 2em 1em;
				padding: 1em;
				width: 12em;
				float: left;
				-moz-box-shadow: 0px 0px 1.25em #ccc;
				-webkit-box-shadow: 0px 0px 1.25em #ccc;
				box-shadow: 0px 0px 1.25em #ccc;
				-moz-border-radius: 0.6em;
				-webkit-border-radius: 0.6em;
				border-radius: 0.6em;
			}

			.ie6 #status {
				display: inline; /* float double margin fix http://www.positioniseverything.net/explorer/doubled-margin.html */
			}

			#status ul {
				font-size: 0.9em;
				list-style-type: none;
				margin-bottom: 0.6em;
				padding: 0;
			}
            
			#status li {
				line-height: 1.3;
			}

			#status h1 {
				text-transform: uppercase;
				font-size: 1.1em;
				margin: 0 0 0.3em;
			}

			#page-body {
				margin: 2em 1em 1.25em 18em;
			}

			h2 {
				margin-top: 1em;
				margin-bottom: 0.3em;
				font-size: 1em;
			}

			p {
				line-height: 1.5;
				margin: 0.25em 0;
			}

			#controller-list ul {
				list-style-position: inside;
			}

			#controller-list li {
				line-height: 1.3;
				list-style-position: inside;
				margin: 0.25em 0;
			}

			@media screen and (max-width: 480px) {
				#status {
					display: none;
				}

				#page-body {
					margin: 0 1em 1em;
				}

				#page-body h1 {
					margin-top: 0;
				}
			}
		</style>
	</head>
	<body>
		<a href="#page-body" class="skip"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div id="status" role="complementary">
			<h1>Application Status</h1>
			<ul>
				<li>App version: <g:meta name="app.version"/></li>
				<li>Grails version: <g:meta name="app.grails.version"/></li>
				<li>Groovy version: ${org.codehaus.groovy.runtime.InvokerHelper.getVersion()}</li>
				<li>JVM version: ${System.getProperty('java.version')}</li>
				<li>Reloading active: ${grails.util.Environment.reloadingAgentEnabled}</li>
				<li>Controllers: ${grailsApplication.controllerClasses.size()}</li>
				<li>Domains: ${grailsApplication.domainClasses.size()}</li>
				<li>Services: ${grailsApplication.serviceClasses.size()}</li>
				<li>Tag Libraries: ${grailsApplication.tagLibClasses.size()}</li>
			</ul>
			<h1>Installed Plugins</h1>
			<ul>
				<g:each var="plugin" in="${applicationContext.getBean('pluginManager').allPlugins}">
					<li>${plugin.name} - ${plugin.version}</li>
				</g:each>
			</ul>
		</div>
		<div id="page-body" role="main">
			<h1>Alumno</h1>
			<p></p>
            <g:link controller="Registration" action="importOnlineData"><input type="button" value="Import Online Data" class="button"/></g:link>
            <g:link controller="Registration" action="importHSData"><input type="button" value="Import High School Data" class="button"/></g:link>
            <g:link controller="Registration" action="createPDF"><input type="button" value="Create Directory" class="button"/></g:link>
            <g:form url='[controller: "searchable", action: "index"]' id="searchableForm" name="searchableForm" method="get">
                <g:textField name="q" value="${params.q}" size="50"/> <input type="submit" value="Search" />
            </g:form>
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
	</body>
</html>
