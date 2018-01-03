package exchange.objects.shared;

import java.util.ArrayList;

public enum Command {
	COMMAND_START(0),
	COMMAND_STOP(1),
	COMMAND_RESET(2),
	COMMAND_EXIT(3);
	
	public static Command getByName(String name){
		if(name!=null){
			if(name.equalsIgnoreCase(COMMAND_START.toString())){
				return COMMAND_START;
			}else if(name.equalsIgnoreCase(COMMAND_STOP.toString())){
				return COMMAND_STOP;
			}else if(name.equalsIgnoreCase(COMMAND_RESET.toString())){
				return COMMAND_RESET;
			}else if(name.equalsIgnoreCase(COMMAND_EXIT.toString())){
				return COMMAND_EXIT;
			}else{
				return null;
			}
		}else{
			return null;
		}
	}
	
	private int kod;
	private ArrayList<String> arguments=new ArrayList<String>();
	
	private Command(int kod){
		this.kod=kod;
	}

	/** �������� ��� */
	public int getKod(){
		return this.kod;
	}
	
	/** �������� ��������  */
	public void addArgument(String value){
		this.arguments.add(value);
	}
	
	/** �������� ��� ��������� */
	public void clearArguments(){
		this.arguments.clear();
	}
	
	/** �������� ���-�� ���������� */
	public int getArgumentsCount(){
		return this.arguments.size();
	}

	/** �������� �������� �� ��� ������  */
	public String getArgumentByNumber(int index){
		try{
			return this.arguments.get(index);
		}catch(Exception ex){
			return null;
		}
	}
	
}
