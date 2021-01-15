package ai.forjada.teamtracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ai.forjada.teamtracker.security.AuthRequest;
import ai.forjada.teamtracker.service.ApiMessage;
import ai.forjada.teamtracker.util.JwtUtil;

@RestController
public class LoginController {
	
    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private AuthenticationManager authenticationManager;
	
    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/authenticate")
    public ResponseEntity<?> generateToken(
    		@RequestBody AuthRequest authRequest
    		) throws Exception {
    	
    	String msg = "";
    	String err = "";
    	
        try {
            authenticationManager
            	.authenticate(
                    new UsernamePasswordAuthenticationToken(
                    		authRequest.getUserName(), 
                    		authRequest.getPassword()
                    	)
            		);
        } catch (Exception ex) {
			msg = authRequest.getUserName() + " was unable to log in";
			err = "LOGIN_FAILED";
			return new ResponseEntity<>(new ApiMessage(err, msg), HttpStatus.NOT_FOUND);
        }
        
        msg = jwtUtil.generateToken(authRequest.getUserName());
        return ResponseEntity.ok(new ApiMessage(err,msg));
    }

}
