package xindus.backend.assignment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import xindus.backend.assignment.entity.Users;
import xindus.backend.assignment.repository.UserRepository;

import javax.annotation.PostConstruct;
import java.util.Date;

@SpringBootApplication
public class XindusAssignmentApplication {
@Autowired
private UserRepository userRepository;
@Autowired
	private  BCryptPasswordEncoder bCryptPasswordEncoder;
	public static void main(String[] args) {
		SpringApplication.run(XindusAssignmentApplication.class, args);
	}
	   // @PostConstruct
	public void init(){
		Users users=new Users(0,"Ashish","ashishkumar3110199@gmail.com","SUPER_ADMIN", bCryptPasswordEncoder.encode("shiv"),
				new Date());
		userRepository.save(users);
		System.out.println(users);
	}

}
