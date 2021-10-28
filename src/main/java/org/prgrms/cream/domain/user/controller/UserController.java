package org.prgrms.cream.domain.user.controller;

import javax.validation.Valid;
import org.prgrms.cream.domain.user.dto.UserSignUpRequest;
import org.prgrms.cream.domain.user.service.UserService;
import org.prgrms.cream.global.response.ApiResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping
	public ApiResponse<Long> createUser(@Valid @RequestBody UserSignUpRequest userSignUpRequest) {
		return ApiResponse.of(userService.saveUser(userSignUpRequest));
	}
}
