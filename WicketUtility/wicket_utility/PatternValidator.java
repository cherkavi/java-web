package wicket_utility;

import java.io.Serializable;

import org.apache.wicket.validation.IErrorMessageSource;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidationError;
import org.apache.wicket.validation.IValidator;

/** ���������, ������� ��������� ��������� �������� �� ������������ ����������� ���������, 
 * � � ������ ������� ���������� ������ �� ���������� �����, �������� ����������� ������, 
 * � �������� ����� ����������� ������ ���������
 * */
public class PatternValidator implements IValidator<String>,Serializable{
	private static final long serialVersionUID = 1L;
	private String regPattern;
	private ValidationError validationError;
	/** ���������, ������� ��������� ��������� �������� �� ������������ ����������� ���������, 
	 * � � ������ ������� ���������� ������ �� ���������� �����, �������� ����������� ������, 
	 * � �������� ����� ����������� ������ ���������
	 * @param regPattern - ������ ����������� ���������
	 * @param resourceStringName - ��� �������� � ��������� ����� 
	 */
	public PatternValidator(String regPattern, String resourceStringName){
		this.regPattern=regPattern;
		this.validationError=new ValidationError(resourceStringName);
	}
	
	/** ����� ������������ ��� ��������������� ���������� ����������� ��������,
	 * �������� ����� ���������� �������� ����� �������������� "��������" �� ������, ����, ����� ����...
	 * */
	public String prepareValue(String value){
		return value;
	}
	
	/** �����, ������� ��������� �� ������ ��������� */
	public void notifyError(IValidatable<String> validatable){
		validatable.error(this.validationError);
	}
	
	@Override
	public void validate(IValidatable<String> validatable) {
		if(this.prepareValue(validatable.getValue()).matches(this.regPattern)){
			// ��������� �������, � ����� ������ ������� ����������
		}else{
			// ��������� �� ������� - ������� ������
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


