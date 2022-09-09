package com.kepler.service;

import com.kepler.dto.*;
import com.kepler.dto.user.*;

import java.util.Map;

public interface IUserService {
    PageableDTO<UserSearchResponse> getUsers(UserSearchRequest searchRequest);
    int getTotalItems(String searchValue);
    UserDTO findOneByUserName(String userName);
    UserDTO findUserById(long id);
    UserDTO createUser(UserDTO userDTO);
    UserDTO updateUser(UserDTO userDTO);
    void updatePassword(long id, PasswordDTO userDTO);
    UserDTO resetPassword(long id);
    UserDTO updateUserProfile(UserProfileDTO userProfileDTO);
    void deleteUsers(long[] ids);
    Map<Long, String> getStaffsMap();
    boolean checkUserHasManagedBuilding(Long userId, Long buildingId);
    boolean checkUserHasManagedCustomer(Long userId, Long customerId);
    Map<Integer, String> getUserStatus();
}
