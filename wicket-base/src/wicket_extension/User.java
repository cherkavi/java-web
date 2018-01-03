package wicket_extension;

import java.io.Serializable;

/** ���������� ��� ������������ ��� ������������� ��� � ������ */
public class User implements Serializable{
	private final static long serialVersionUID=1L;
	
	private Integer kod;
	/** �����, ������� ����� �������������� ������� ������������, ������� �������� � ����������� */
	public User(){
	}
	
	public User(Integer kod){
		this.kod=kod;
	}

	/**
	 * @return the kod
	 */
	public Integer getKod() {
		return kod;
	}

	/**
	 * @param kod the kod to set
	 */
	public void setKod(Integer kod) {
		this.kod = kod;
	}
	
	
}
