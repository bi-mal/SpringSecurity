package com.bimal.springsecurityjwt;

import com.bimal.springsecurityjwt.models.AuthenticationRequest;
import com.bimal.springsecurityjwt.models.AuthenticationResponse;
import com.bimal.springsecurityjwt.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloResource {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    MyUserDetailService userDetailService;

    @Autowired
    JwtUtil jwtTokenUtil;

    @GetMapping("/hello")
    public String hello() {
        return "hello world";
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception  {
        try {
            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUserName(), authenticationRequest.getPassword()));
        }
        catch (BadCredentialsException e){
            throw new Exception("Invalid username or password",e);
        }
        final UserDetails userDetails= userDetailService.loadUserByUsername(authenticationRequest.getUserName());
        final String jwt=jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }
}
