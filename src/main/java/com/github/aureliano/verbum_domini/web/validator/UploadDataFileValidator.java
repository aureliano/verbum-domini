package com.github.aureliano.verbum_domini.web.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.apache.myfaces.custom.fileupload.UploadedFile;

public class UploadDataFileValidator implements Validator {

	private static final Long FILE_MAX_SIZE = 460800L;
	
	@Override
	public void validate(FacesContext fc, UIComponent component, Object object)
			throws ValidatorException {
		
		if (object == null) {
			String text = "Uploaded file cannot be null.";
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, text, text);
			fc.addMessage(null, message);
			
			throw new ValidatorException(message);
		}
		
		UploadedFile file = (UploadedFile) object;
		if (!("application/json".equalsIgnoreCase(file.getContentType()) ||
				"text/json".equalsIgnoreCase(file.getContentType()))) {
			String text = "Accept only JSON file.";
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, text, text);
			fc.addMessage(null, message);
			
			throw new ValidatorException(message);
		}
		
		if (FILE_MAX_SIZE < file.getSize()) {
			String text = "File must not be greater than " + (FILE_MAX_SIZE / 1024) + "KiB.";
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, text, text);
			fc.addMessage(null, message);
			
			throw new ValidatorException(message);
		}
	}
}