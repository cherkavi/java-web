package manager.display;

import manager.transport.SubCommand;

/** Данный интерфейс отвечает за обработку команд, которые касаются пользовательского интерфейса <br>
 * первая стадия - отображение полученных данных
 */
public interface Display {
	/** метод, который отвечает за обработку команды sub_command для отображения пользователю */
	public void doSubCommand(SubCommand sub_command);
}
