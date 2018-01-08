package validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author Администратор
 */
public class InnValidator implements Validator{

    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
    	System.out.println("Validator");
        if(value==null){
        	System.out.println("Validator error: input value");
            throw new ValidatorException(new FacesMessage("input value"));
        }
        String controlValue=(String)value;
        controlValue=controlValue.trim();
        if(controlValue.length()!=10){
        	System.out.println("Validator error: input correct value");
            throw new ValidatorException(new FacesMessage("input correct value"));
        }
    }

}
