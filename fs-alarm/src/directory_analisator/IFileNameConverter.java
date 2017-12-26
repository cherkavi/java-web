package directory_analisator;

import java.io.File;

/** преобразовать  */
public interface IFileNameConverter {
	/** преобразовать название файла в текстовое представление  */
	public String convertFileName(File file);
}
