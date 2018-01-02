package gui.EnterPoint;

/**
 * класс, методы которого отвечают за позиционирование JFrame <br>
 * (на рабочем столе монитора) 
 * и JInternalFrame <br>
 * (либо же на рабочем столе фрейма) <br>
 * @author cherkashinv
 *
 */
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
/**
 * класс для установки позиция для JFrame и JInternalFrame (on JDesktopPane)
 * @author Technik
 */
public class Position {
    
    /**
     * Установка JFrame на весь экран главного монитора
     */
    public static void set_frame_to_center(JFrame frame){
        set_frame_to_center(frame,0,0);
    }
    /**
     * установка JFrame в центр со смещением слева и справа
     */
    public static void set_frame_to_center(JFrame frame,int offset_left,int offset_top){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setBounds(offset_left, offset_top, 
                        screenSize.width - offset_left*2, 
                        screenSize.height-offset_top*2);
    };
    /**
     * установка JFrame с заданными размерами в центре главного монитора
     */
    public static void set_frame_by_dimension(JFrame frame,Dimension dimension){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setBounds((int)((screenSize.getWidth()-dimension.getWidth())/2),
                        (int)((screenSize.getHeight()-dimension.getHeight())/2),
                        (int)dimension.getWidth(),
                        (int)dimension.getHeight());
    }
    /**
     * установка JFrame с заданными размерами в центре главного монитора
     */
    public static void set_frame_by_dimension(JFrame frame,int width,int height){
        set_frame_by_dimension(frame,new Dimension(width,height));
    }
    /**
     * утсановка JInternalFrame в центре рабочего стола со смещением слева и справа
     */
    public static void set_frame_to_center(JInternalFrame internalframe,
                                           JDesktopPane desktop,
                                           int offset_left,
                                           int offset_top){
        Dimension screenSize = desktop.getSize();
        internalframe.setBounds((int)(screenSize.getWidth()/2-offset_left/2-internalframe.getWidth()/2),
                                (int)(screenSize.getHeight()/2-offset_top/2-internalframe.getHeight()/2),
                                (int)internalframe.getWidth(),
                                (int)internalframe.getHeight());
    }
    /**
     * установка JInternalFrame на весь рабочий стол
     */
    public static void set_frame_to_center(JInternalFrame internalframe,
                                           JDesktopPane desktop){
        set_frame_to_center(internalframe,desktop,0,0);
    }                                         
    /**
     * установка JInternalFrame в центре рабочего стола с заданными размерами
     */
    public static void set_frame_by_dimension(JInternalFrame internalframe,
                                              JDesktopPane desktop,
                                              int width,
                                              int height){
        internalframe.setBounds((int)(((desktop.getWidth()-internalframe.getWidth())/2)-width/2),
                                (int)(((desktop.getHeight()-internalframe.getHeight())/2)-height/2),
                                width,
                                height);
    }
    /**
     * установка JInternalFrame в центре рабочего стола без изменения размеров
     */
    public static void set_frame_by_dimension(JInternalFrame internalframe,
                                              JDesktopPane desktop){
        internalframe.setBounds((int)(((desktop.getWidth()-internalframe.getWidth())/2)-internalframe.getWidth()/2),
                                (int)(((desktop.getHeight()-internalframe.getHeight())/2)-internalframe.getHeight()/2),
                                internalframe.getWidth(),
                                internalframe.getHeight());
    }
    
}



















