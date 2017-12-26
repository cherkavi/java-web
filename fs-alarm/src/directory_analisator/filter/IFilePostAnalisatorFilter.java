package directory_analisator.filter;

import java.io.File;

/** фильтр-анализатор файлов ( пост-анализатор )*/
public interface IFilePostAnalisatorFilter {
	/** обработать полученный список файлов  */
	public File[] filterFile(File[] files);
}
