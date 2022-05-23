package com.service.dida.User;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class UserController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserService userService;

}
