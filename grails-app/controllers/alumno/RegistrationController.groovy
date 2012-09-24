package alumno

import org.springframework.dao.DataIntegrityViolationException
import grails.plugins.springsecurity.Secured
import org.springframework.web.multipart.MultipartFile
import javax.servlet.http.HttpServletRequest

class RegistrationController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

//    @Secured(['ROLE_ADMIN'])
    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [registrationInstanceList: Registration.list(params), registrationInstanceTotal: Registration.count()]
    }

    def create() {
        [registrationInstance: new Registration(params)]
    }

    def importHSData() {
        def importData = new ImportDataService()
        importData.importHighSchoolData()
     //    flash.message = message("Data imported")
        redirect(action: "list")
    }

    def importOnlineData() {
        def importData = new ImportDataService()
        importData.importOnlineData()
        //    flash.message = message("Data imported")
        redirect(action: "list")
    }

    def createPDF() {
        def createDirectory = new CreateDirectoryService()
        createDirectory.createPDF()
        //    flash.message = message("Data imported")
        redirect(action: "list")
    }

    def save() {
        def registrationInstance = new Registration(params)
        registrationInstance.ipAddress = request.getRemoteAddr()
        if (!registrationInstance.save(flush: true)) {
            render(view: "create", model: [registrationInstance: registrationInstance])
            return
        }

		flash.message = message(code: 'default.created.message', args: [message(code: 'registration.label', default: 'Registration'), registrationInstance.id])
        redirect(action: "show", id: registrationInstance.id)
    }

    def show() {
        def registrationInstance = Registration.get(params.id)
        if (!registrationInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'registration.label', default: 'Registration'), params.id])
            redirect(action: "list")
            return
        }

        [registrationInstance: registrationInstance]
    }

    def edit() {
        def registrationInstance = Registration.get(params.id)
        if (!registrationInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'registration.label', default: 'Registration'), params.id])
            redirect(action: "list")
            return
        }

        [registrationInstance: registrationInstance]
    }

    def update() {
        def registrationInstance = Registration.get(params.id)
        if (!registrationInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'registration.label', default: 'Registration'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (registrationInstance.version > version) {
                registrationInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'registration.label', default: 'Registration')] as Object[],
                          "Another user has updated this Registration while you were editing")
                render(view: "edit", model: [registrationInstance: registrationInstance])
                return
            }
        }

        registrationInstance.properties = params

        if (!registrationInstance.save(flush: true)) {
            render(view: "edit", model: [registrationInstance: registrationInstance])
            return
        }

		flash.message = message(code: 'default.updated.message', args: [message(code: 'registration.label', default: 'Registration'), registrationInstance.id])
        redirect(action: "show", id: registrationInstance.id)
    }

 //   @Secured(['ROLE_ADMIN'])
    def delete() {
        def registrationInstance = Registration.get(params.id)
        if (!registrationInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'registration.label', default: 'Registration'), params.id])
            redirect(action: "list")
            return
        }

        try {
            registrationInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'registration.label', default: 'Registration'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'registration.label', default: 'Registration'), params.id])
            redirect(action: "show", id: params.id)
        }
    }
}
