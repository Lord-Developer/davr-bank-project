package uz.davr.service.impl;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.davr.database.Database;
import uz.davr.dto.response.RestAPIResponse;
import uz.davr.model.ERole;
import uz.davr.model.User;
import uz.davr.service.UserService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final Database database;
    private final String INSERT_QUERY = "INSERT INTO user_tb (name, username, password, role) VALUES(?, ?, ?,?)";
    private final String GET_BY_USERNAME_QUERY = "SELECT * FROM user_tb WHERE username = ?";



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = getById(username);
        if (user == null) {
            log.error("user {} not found int the database", username);
            throw new UsernameNotFoundException("user " + username + " not found in the database");
        } else {
            log.info("user {} found int the database", username);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole()));

        return new org.springframework.security.core.userdetails
                .User(user.getUsername(), user.getPassword(), authorities);
    }


    public RestAPIResponse saveUser(User user) {
        try {
            PreparedStatement preparedStatement = database.connect().prepareStatement(INSERT_QUERY);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getUsername());
            preparedStatement.setString(3, passwordEncoder.encode(user.getPassword()));
            ERole eRole = ERole.valueOfRole(user.getRole());
            if (eRole != null)
                preparedStatement.setString(4, eRole.name());
            else
                preparedStatement.setString(4, ERole.ROLE_USER.name());

            preparedStatement.execute();
            return new RestAPIResponse("Successfully added!",true, HttpStatus.CREATED);

        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return new RestAPIResponse("Failed!", false, HttpStatus.BAD_REQUEST);
    }

    public User getById(String username) {
        try {
            PreparedStatement preparedStatement = database.connect().prepareStatement(GET_BY_USERNAME_QUERY);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            User user = new User();
            if (resultSet.next()){
                user.setId(resultSet.getInt(1));
                user.setName(resultSet.getString(2));
                user.setUsername(resultSet.getString(3));
                user.setPassword(resultSet.getString(4));
                user.setRole(resultSet.getString(5));
                return user;
            }

        } catch (SQLException e) {
            log.error(e.getMessage());
        }

        return null;

    }
}