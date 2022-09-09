package com.kepler.service.impl;

import com.kepler.converter.UserConverter;
import com.kepler.dto.PageableDTO;
import com.kepler.dto.user.*;
import com.kepler.entity.RoleEntity;
import com.kepler.entity.UserEntity;
import com.kepler.enums.UserStatusEnum;
import com.kepler.exception.UnProcessableEntityException;
import com.kepler.repository.RoleRepository;
import com.kepler.repository.UserRepository;
import com.kepler.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.kepler.constant.SystemConstant.User.*;
import static org.apache.commons.lang.StringUtils.isNotBlank;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserConverter userConverter;
    private final PasswordEncoder passwordEncoder;

    @Override
    public PageableDTO<UserSearchResponse> getUsers(UserSearchRequest searchRequest) {

        String searchValue = searchRequest.getSearchValue();
        Integer searchStatus = searchRequest.getStatus();
        if (searchStatus == null) {
            searchStatus = STATUS_ACTIVE;
        }
        Pageable pageable = PageRequest.of(searchRequest.getPage() - 1, searchRequest.getLimit(), Sort.by( "createdDate").descending());

        Page<UserEntity> users;
        if (isNotBlank(searchValue)) {
            users = userRepository.findByUsernameContainingIgnoreCaseAndStatusOrFullNameContainsIgnoreCaseAndStatus(searchValue, searchStatus, searchValue, searchStatus, pageable);
        } else {
            users = userRepository.findByStatus(searchStatus, pageable);
        }

        return userConverter.convertToPageableResponseDTO(users);
    }

    @Override
    public int getTotalItems(String searchValue) {
        int totalItem;
        if (isNotBlank(searchValue)) {
            totalItem = (int) userRepository.countByUsernameContainingIgnoreCaseOrFullNameContainingIgnoreCaseAndStatusNot(searchValue, searchValue, 0);
        } else {
            totalItem = (int) userRepository.countByStatusNot(0);
        }
        return totalItem;
    }

    @Override
    public UserDTO findOneByUserName(String username) {
        UserEntity userEntity = userRepository.findOneByUsername(username);
        if (userEntity == null) {
            return null;
        }
        return userConverter.convertToDto(userEntity);
    }

    @Override
    public UserDTO findUserById(long id) {
        UserEntity entity = userRepository.findById(id).orElse(null);
        if (entity == null) {
            return null;
        }
        return userConverter.convertToDto(entity);
    }

    @Override
    @Transactional
    public UserDTO createUser(UserDTO newUser) {
        if (userRepository.existsByUsername(newUser.getUsername())) {
            throw new UnProcessableEntityException("Tên đăng nhập đã tồn tại");
        }
        UserEntity userEntity = userConverter.convertToEntity(newUser);

        RoleEntity role = roleRepository.findOneByCode(newUser.getRoleCode());
        userEntity.setRoles(Collections.singleton(role));

        userEntity.setPassword(passwordEncoder.encode(PASSWORD_DEFAULT));

        return userConverter.convertToDto(userRepository.save(userEntity));
    }

    @Override
    @Transactional
    public UserDTO updateUser(UserDTO updateUser) {
        UserEntity oldUser = userRepository.findById(updateUser.getId())
                                           .orElseThrow(() -> new UnProcessableEntityException("Id người dùng không tồn tại"));
        userConverter.convertToExistingEntity(updateUser, oldUser);

        RoleEntity role = roleRepository.findOneByCode(updateUser.getRoleCode());
        oldUser.setRoles(Collections.singleton(role));

        return updateUser;
    }

    @Override
    @Transactional
    public void updatePassword(long id, PasswordDTO passwordDTO) {
        UserEntity user = userRepository.findById(id)
                                        .orElseThrow(() -> new UnProcessableEntityException("Id người dùng không tồn tại"));

        if (! passwordEncoder.matches(passwordDTO.getOldPassword(), user.getPassword())) {
            throw new UnProcessableEntityException("Mật khẩu hiện tại không đúng");
        }
        if (passwordDTO.getOldPassword().equals(passwordDTO.getNewPassword())) {
            throw new UnProcessableEntityException("Mật khẩu mới phải khác mật khẩu hiện tại");
        }
        if (! passwordDTO.getNewPassword().equals(passwordDTO.getConfirmPassword())) {
            throw new UnProcessableEntityException("Mật khẩu mới chưa khớp");
        }

        user.setPassword(passwordEncoder.encode(passwordDTO.getNewPassword()));
        userRepository.save(user);
    }

    @Override
    @Transactional
    public UserDTO resetPassword(long id) {
        UserEntity userEntity = userRepository.findById(id)
                                              .orElseThrow(() -> new UnProcessableEntityException("Id người dùng không tồn tại"));
        userEntity.setPassword(passwordEncoder.encode(PASSWORD_DEFAULT));
        return userConverter.convertToDto(userRepository.save(userEntity));
    }

    @Override
    @Transactional
    public UserDTO updateUserProfile(UserProfileDTO updateUser) {
        String username = updateUser.getUsername();
        UserEntity oldUser = userRepository.findOneByUsername(username);
        if (oldUser == null) {
            throw new UnProcessableEntityException("Lỗi khi cập nhật thông tin");
        }
        userConverter.convertToExistingEntity(updateUser, oldUser);
        return userConverter.convertToDto(oldUser);
    }

    @Override
    @Transactional
    public void deleteUsers(long[] ids) {
        List<Long> managersId = userRepository.getAllManagersId();
        for (long id: ids) {
            if(managersId.contains(id)) {
                throw new UnProcessableEntityException("Không thể xóa admin");
            }
        }
        for (Long id : ids) {
            UserEntity userEntity = userRepository.findById(id)
                    .orElseThrow(() -> new UnProcessableEntityException("Id người dùng không tồn tại"));
            userEntity.setStatus(STATUS_NOT_ACTIVE);
            userEntity.getBuildings()
                      .forEach(building -> building.getStaffs().remove(userEntity));
            userEntity.getCustomers()
                      .forEach(customer -> customer.getStaffs().remove(userEntity));
        }
    }

    @Override
    public Map<Long, String> getStaffsMap() {
        List<UserEntity> userEntities = userRepository.findByStatusAndRoles_Code(1, STAFF);
        Map<Long, String> result = new HashMap<>();
        userEntities.forEach(userEntity -> result.put(userEntity.getId(), userEntity.getFullName()));
        return result;
    }

    @Override
    public boolean checkUserHasManagedBuilding(Long userId, Long buildingId) {
        return userRepository.getBuildingsIdByUserId(userId).contains(buildingId);
    }

    @Override
    public boolean checkUserHasManagedCustomer(Long userId, Long customerId) {
        return userRepository.getCustomersIdByUserId(userId).contains(customerId);
    }

    @Override
    public Map<Integer, String> getUserStatus() {
        LinkedHashMap<Integer, String> statusMap = new LinkedHashMap<>();
        for (UserStatusEnum item : UserStatusEnum.values()) {
            statusMap.put(item.getStatusCode(), item.getStatusName());
        }
        return statusMap;
    }
}
