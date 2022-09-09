package com.kepler.api.admin;

import com.kepler.dto.staff.StaffDTO;
import com.kepler.dto.user.PasswordDTO;
import com.kepler.dto.user.UserDTO;
import com.kepler.dto.user.UserProfileDTO;
import com.kepler.service.IBuildingService;
import com.kepler.service.ICustomerService;
import com.kepler.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserAPI {
    private final IUserService userService;
    private final IBuildingService buildingService;
    private final ICustomerService customerService;

    @GetMapping()
    public UserDTO getUserByUsername(@RequestParam("username") String username) {
        return userService.findOneByUserName(username);
    }

    @GetMapping("/staffs")
    public List<StaffDTO> getStaffs(@RequestParam(name = "buildingId", required = false) Long buildingId,
                                            @RequestParam(name = "customerId", required = false) Long customerId) {
        List<StaffDTO> staffDTOs = new ArrayList<>();
        if (buildingId != null) {
            staffDTOs.addAll(buildingService.getStaffs(buildingId));
        } else if(customerId != null) {
            staffDTOs.addAll(customerService.getStaffs(customerId));
        }
        return staffDTOs;
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUsers(@RequestBody @Valid UserDTO newUser) {
        return ResponseEntity.ok(userService.createUser(newUser));
    }

    @PutMapping()
    public ResponseEntity<UserDTO> updateUsers(@RequestBody @Valid UserDTO userDTO) {
        return ResponseEntity.ok(userService.updateUser(userDTO));
    }

    @PutMapping("/change-password/{id}")
    @PreAuthorize("authentication.principal.id.equals(#id)")
    public ResponseEntity<String> changePassword(@PathVariable("id") long id, @RequestBody @Valid PasswordDTO passwordDTO) {
        userService.updatePassword(id, passwordDTO);
        return ResponseEntity.ok("Thay đổi mật khẩu thành công");
    }

    @PutMapping("/password/{id}/reset")
    public ResponseEntity<UserDTO> resetPassword(@PathVariable("id") long id) {
        return ResponseEntity.ok(userService.resetPassword(id));
    }

    @PutMapping("/profile")
    @PreAuthorize("authentication.name.equals(#userProfileDTO.username)")
    public ResponseEntity<UserDTO> updateUserProfile(@RequestBody @Valid UserProfileDTO userProfileDTO) {
        return ResponseEntity.ok(userService.updateUserProfile(userProfileDTO));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteUsers(@RequestBody long[] ids) {
        if (ids.length > 0) {
            userService.deleteUsers(ids);
        }
        return ResponseEntity.noContent().build();
    }


}
