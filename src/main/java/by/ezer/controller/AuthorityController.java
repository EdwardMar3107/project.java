package by.ezer.controller;

import by.ezer.dto.AuthorityCreateDTO;
import by.ezer.dto.AuthorityDTO;
import by.ezer.dto.ProductCreateDTO;
import by.ezer.dto.ProductDTO;
import by.ezer.service.AuthorityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/Authority")
public class AuthorityController {
    private final AuthorityService authorityService;

    @PostMapping
    public ResponseEntity<AuthorityDTO> save(@RequestBody AuthorityCreateDTO request) {
        return ResponseEntity.ok(authorityService.createAuthority(request));
    }
}
