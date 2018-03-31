package test.jax;

import javax.jws.WebService;

@WebService(endpointInterface = "test.jax.IWebInterface")
public class WebImplementation implements IWebInterface{
	@Override
	public int getLength(String value) {
		if(value==null){
			return 0;
		}
		return value.length();
	}
}
