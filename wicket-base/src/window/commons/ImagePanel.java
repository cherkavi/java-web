package window.commons;

import java.io.File;

import org.apache.wicket.Resource;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.util.resource.FileResourceStream;
import org.apache.wicket.util.resource.IResourceStream;

/** панель, которая содержит только изображение */
public class ImagePanel extends Panel{
	private final static long serialVersionUID=1L;
	
	public ImagePanel(String id, final File file){
		super(id);
		this.add(new Image("image",new Resource(){
			private final static long serialVersionUID=1L;
			@Override
			public IResourceStream getResourceStream() {
				return new FileResourceStream(file);
			}
		}));
	}
}
