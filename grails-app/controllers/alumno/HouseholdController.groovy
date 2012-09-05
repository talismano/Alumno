package alumno

import org.springframework.dao.DataIntegrityViolationException
import groovy.json.JsonSlurper
import grails.plugins.rest.client.RestBuilder
import org.codehaus.groovy.grails.web.json.JSONObject
import grails.plugins.springsecurity.Secured


class HouseholdController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    @Secured(['ROLE_ADMIN'])
    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [householdInstanceList: Household.list(params), householdInstanceTotal: Household.count()]
    }

    def create() {
        [householdInstance: new Household(params)]
    }

    def save() {
        def householdInstance = new Household(params)
        String zipcodeLookupString = "http://ws.geonames.org/postalCodeSearchJSON?country=US&postalcode=" + householdInstance.zip
        // {"postalCodes":[{"adminName2":"Santa Clara","adminCode2":"085","adminCode1":"CA","postalCode":"95032","countryCode":"US","lng":-121.9554,"placeName":"Los Gatos","lat":37.2417,"adminName1":"California"}]}
        def rest = new RestBuilder(connectTimeout:1000, readTimeout:20000)
        def resp = rest.get(zipcodeLookupString)
        resp.json instanceof JSONObject

        householdInstance.city = resp.json.postalCodes[0].placeName
        householdInstance.state = resp.json.postalCodes[0].adminCode1

        if (!householdInstance.save(flush: true)) {
            render(view: "create", model: [householdInstance: householdInstance])
            return
        }

		flash.message = message(code: 'default.created.message', args: [message(code: 'household.label', default: 'Household'), householdInstance.id])
        redirect(action: "show", id: householdInstance.id)
    }

    def show() {
        def householdInstance = Household.get(params.id)
        if (!householdInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'household.label', default: 'Household'), params.id])
            redirect(action: "list")
            return
        }

        [householdInstance: householdInstance]
    }

    def edit() {
        def householdInstance = Household.get(params.id)
        if (!householdInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'household.label', default: 'Household'), params.id])
            redirect(action: "list")
            return
        }

        [householdInstance: householdInstance]
    }

    def update() {
        def householdInstance = Household.get(params.id)
        if (!householdInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'household.label', default: 'Household'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (householdInstance.version > version) {
                householdInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'household.label', default: 'Household')] as Object[],
                          "Another user has updated this Household while you were editing")
                render(view: "edit", model: [householdInstance: householdInstance])
                return
            }
        }

        householdInstance.properties = params

        if (!householdInstance.save(flush: true)) {
            render(view: "edit", model: [householdInstance: householdInstance])
            return
        }

		flash.message = message(code: 'default.updated.message', args: [message(code: 'household.label', default: 'Household'), householdInstance.id])
        redirect(action: "show", id: householdInstance.id)
    }

    def delete() {
        def householdInstance = Household.get(params.id)
        if (!householdInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'household.label', default: 'Household'), params.id])
            redirect(action: "list")
            return
        }

        try {
            householdInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'household.label', default: 'Household'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'household.label', default: 'Household'), params.id])
            redirect(action: "show", id: params.id)
        }
    }
}
