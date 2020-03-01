package xml_robot.engine;

/** допустимые типы параметров */
public enum EngineParameterType {
	/** целочисленные */
	INTEGER, 
	/** дробные */
	FLOAT, 
	/** дата  */
	DATE, 
	/** дата и время */
	TIMESTAMP, 
	/** текст */
	VARCHAR,
	/** заранее определенные перечислимые типы */
	ENUM;
}
