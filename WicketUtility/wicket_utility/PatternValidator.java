package wicket_utility;

import java.io.Serializable;

import org.apache.wicket.validation.IErrorMessageSource;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidationError;
import org.apache.wicket.validation.IValidator;

/** Валидатор, который проверяет введенную величину на соответствие регулярному выражению, 
 * и в случае неудачи возвращает строку из ресурсного файла, которому принадлежит объект, 
 * к которому будет присоединен данный валидатор
 * */
public class PatternValidator implements IValidator<String>,Serializable{
	private static final long serialVersionUID = 1L;
	private String regPattern;
	private ValidationError validationError;
	/** Валидатор, который проверяет введенную величину на соответствие регулярному выражению, 
	 * и в случае неудачи возвращает строку из ресурсного файла, которому принадлежит объект, 
	 * к которому будет присоединен данный валидатор
	 * @param regPattern - шаблон регулярного выражения
	 * @param resourceStringName - имя свойства в ресурсном файле 
	 */
	public PatternValidator(String regPattern, String resourceStringName){
		this.regPattern=regPattern;
		this.validationError=new ValidationError(resourceStringName);
	}
	
	/** метод предназначен для предварительной подготовки проверяемой величины,
	 * например номер мобильного телефона нужно предварительно "очистить" от скобок, тире, знака плюс...
	 * */
	public String prepareValue(String value){
		return value;
	}
	
	/** метод, который оповещает об ошибке валидации */
	public void notifyError(IValidatable<String> validatable){
		validatable.error(this.validationError);
	}
	
	@Override
	public void validate(IValidatable<String> validatable) {
		if(this.prepareValue(validatable.getValue()).matches(this.regPattern)){
			// выражение валидно, с точки зрения данного валидатора
		}else{
			// выражение не валидно - вывести ошибку
			this.notifyError(validatable);
		}
	}

	private class ValidationError implements IValidationError, Serializable{
		private final static long serialVersionUID=1L;
		private String resourceString;
		private ValidationError(String resourceString){
			this.resourceString=resourceString;
		}
		@Override
		public String getErrorMessage(IErrorMessageSource messageSource) {
			try{
				return messageSource.getMessage(this.resourceString);
			}catch(Exception ex){
				System.err.println("get message Error");
				ex.printStackTrace();
				return "error";
			}
			
		}
		
	}
}


