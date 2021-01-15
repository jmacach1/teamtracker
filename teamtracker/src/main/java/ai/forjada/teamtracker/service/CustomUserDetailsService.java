package ai.forjada.teamtracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ai.forjada.teamtracker.domain.Employee;

import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	
    @Autowired
    private EmployeeService employeeService;

    @Override
    public UserDetails loadUserByUsername(String email) 
    		throws UsernameNotFoundException {
        Employee employee = employeeService.findByEmail(email);
        return new User(email, employee.getPassword(), new ArrayList<>());
    }
}