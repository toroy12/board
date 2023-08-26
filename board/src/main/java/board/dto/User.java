package board.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
public class User {
	
	private String name;
	private String email;
	private String picture;
	private Role role;
	
	@Builder
    public User(String name, String email, String picture, Role role) {
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.role = role;
    }
	
	 public User update(String name, String picture) {
	        this.name = name;
	        this.picture = picture;
	        
	        return this;
	    }
	
	public String getRoleKey() {
        return this.role.getKey();
    }

}
