package com.kepler.security;

import com.kepler.entity.RoleEntity;
import com.kepler.entity.UserEntity;
import com.kepler.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.kepler.constant.SystemConstant.User.STATUS_NOT_ACTIVE;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {

        UserEntity user = userRepository.findOneByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Username not found");
        }

        boolean isAccountLocked = user.getStatus().equals(STATUS_NOT_ACTIVE);

        MyUserDetail myUserDetail = MyUserDetail.fromUserDetails(
                User.withUsername(username)
                        .password(user.getPassword())
                        .roles(user.getRoles().stream().map(RoleEntity::getCode).toArray(String[]::new))
                        .accountLocked(isAccountLocked)
                        .build()
        );
        myUserDetail.setId(user.getId());
        myUserDetail.setFullName(user.getFullName());

        return myUserDetail;
    }

}
