package by.ezer.controller;

import by.ezer.service.AuthorityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/Authority")
public class AuthorityController {
    private final AuthorityService authorityService;
}
