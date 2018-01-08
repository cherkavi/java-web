package BonCard.applet;

/** интерфейс дл€ общени€ аплета с HTML кодом */
public interface JavaScriptExchange {
	/** метод дл€ получени€ отработки действи€ */
	public void method_simple();
	/** метод дл€ получени€ объектов типа String */
	public void method_string(String value);
	/** метод, который возвращает строку */
	public String echo(String value);
}
