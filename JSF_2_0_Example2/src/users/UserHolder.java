/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package users;

import java.io.Serializable;
import javax.faces.bean.*;

@SessionScoped
@ManagedBean(name="user_holder", eager=true)
public class UserHolder implements Serializable{
	private final static long serialVersionUID=1L;
	
    private User user;

    public UserHolder(){
    }

    public UserHolder(User user){
        this.user=user;
    }

    /** получить пользователя по данной сессии  */
    public User getUser() {
        return user;
    }

    /** установить пользователя по данной сессии */
    public void setUser(User user) {
        this.user = user;
    }
}
