package directory_analisator.filter;

import java.io.File;

/** ������-���������� ������ ( ����-���������� )*/
public interface IFilePostAnalisatorFilter {
	/** ���������� ���������� ������ ������  */
	public File[] filterFile(File[] files);
}
