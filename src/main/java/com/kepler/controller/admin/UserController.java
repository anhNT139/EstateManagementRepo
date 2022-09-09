package com.kepler.controller.admin;

import com.kepler.dto.PageableDTO;
import com.kepler.dto.user.UserDTO;
import com.kepler.dto.user.UserSearchRequest;
import com.kepler.dto.user.UserSearchResponse;
import com.kepler.security.IAuthenticationFacade;
import com.kepler.service.IUserService;
import com.kepler.service.impl.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

import static com.kepler.constant.SystemConstant.MODEL;
import static com.kepler.constant.SystemConstant.User.MANAGER;

@Controller(value = "usersControllerOfAdmin")
@RequiredArgsConstructor
@RequestMapping(value = "/admin")
public class UserController {

	private final IUserService userService;
	private final RoleService roleService;
	private final IAuthenticationFacade authenticationFacade;


	@GetMapping(value = "/user-list")
	public ModelAndView getUsers(@ModelAttribute() @Valid UserSearchRequest searchRequest) {
		ModelAndView mav = new ModelAndView("admin/user/list");
		PageableDTO<UserSearchResponse> pageableUsers = userService.getUsers(searchRequest);
		mav.addObject("pageableUsers", pageableUsers);
		mav.addObject("statusMap", userService.getUserStatus());
		mav.addObject("searchRequest", searchRequest);
		return mav;
	}

	@GetMapping(value = "/user-edit")
	public ModelAndView addUser() {
		ModelAndView mav = new ModelAndView("admin/user/edit");
		mav.addObject("rolesMap", roleService.getRoles());
		mav.addObject("statusMap", userService.getUserStatus());
		mav.addObject("title", "Thêm mới người dùng");
		mav.addObject(MODEL, new UserDTO());
		return mav;
	}

	@GetMapping(value = "/user-edit-{id}")
	public ModelAndView updateUser(@PathVariable("id") Long id) {
		ModelAndView mav = new ModelAndView("admin/user/edit");
		UserDTO user = userService.findUserById(id);
		if (user == null || (user.getRoleCode().equals(MANAGER))) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		mav.addObject("rolesMap", roleService.getRoles());
		mav.addObject("statusMap", userService.getUserStatus());
		mav.addObject("title", "Chỉnh sửa người dùng");
		mav.addObject(MODEL, user);
		return mav;
	}

	@GetMapping(value = "/profile-{username}")
	@PreAuthorize("authentication.name.equals(#username)")
	public ModelAndView updateProfile(@PathVariable("username") String username) {
		ModelAndView mav = new ModelAndView("admin/user/profile");
		UserDTO user = userService.findOneByUserName(username);
		if (user == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "entity not found");
		}
		mav.addObject(MODEL, user);
		return mav;
	}

	@GetMapping(value = "/profile-password")
	public ModelAndView updatePassword() {
		ModelAndView mav = new ModelAndView("admin/user/password");
		UserDTO model = userService.findOneByUserName(authenticationFacade.getPrincipal().getUsername());
		mav.addObject(MODEL, model);
		return mav;
	}

}
