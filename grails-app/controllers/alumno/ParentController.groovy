package alumno

import org.springframework.dao.DataIntegrityViolationException
import grails.plugins.springsecurity.Secured

class ParentController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    @Secured(['ROLE_ADMIN'])
    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [parentInstanceList: Parent.list(params), parentInstanceTotal: Parent.count()]
    }

    def create() {
        [parentInstance: new Parent(params)]
    }

    def save() {
        def parentInstance = new Parent(params)
        if (!parentInstance.save(flush: true)) {
            render(view: "create", model: [parentInstance: parentInstance])
            return
        }

		flash.message = message(code: 'default.created.message', args: [message(code: 'parent.label', default: 'Parent'), parentInstance.id])
        redirect(action: "show", id: parentInstance.id)
    }

    def show() {
        def parentInstance = Parent.get(params.id)
        if (!parentInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'parent.label', default: 'Parent'), params.id])
            redirect(action: "list")
            return
        }

        [parentInstance: parentInstance]
    }

    def edit() {
        def parentInstance = Parent.get(params.id)
        if (!parentInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'parent.label', default: 'Parent'), params.id])
            redirect(action: "list")
            return
        }

        [parentInstance: parentInstance]
    }

    def update() {
        def parentInstance = Parent.get(params.id)
        if (!parentInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'parent.label', default: 'Parent'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (parentInstance.version > version) {
                parentInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'parent.label', default: 'Parent')] as Object[],
                          "Another user has updated this Parent while you were editing")
                render(view: "edit", model: [parentInstance: parentInstance])
                return
            }
        }

        parentInstance.properties = params

        if (!parentInstance.save(flush: true)) {
            render(view: "edit", model: [parentInstance: parentInstance])
            return
        }

		flash.message = message(code: 'default.updated.message', args: [message(code: 'parent.label', default: 'Parent'), parentInstance.id])
        redirect(action: "show", id: parentInstance.id)
    }

    def delete() {
        def parentInstance = Parent.get(params.id)
        if (!parentInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'parent.label', default: 'Parent'), params.id])
            redirect(action: "list")
            return
        }

        try {
            parentInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'parent.label', default: 'Parent'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'parent.label', default: 'Parent'), params.id])
            redirect(action: "show", id: params.id)
        }
    }
}
