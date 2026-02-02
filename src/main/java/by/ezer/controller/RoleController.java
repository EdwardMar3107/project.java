package by.ezer.controller;

import by.ezer.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/roles")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService service;

}
