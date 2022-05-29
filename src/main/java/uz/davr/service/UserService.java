package uz.davr.service;

import uz.davr.dto.response.RestAPIResponse;
import uz.davr.model.User;

public interface UserService {

    User getById(String username);
    RestAPIResponse saveUser(User user);
}