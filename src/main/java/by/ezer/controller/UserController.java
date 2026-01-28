package by.ezer.controller;

import by.ezer.dto.UserCreateDTO;
import by.ezer.dto.UserDTO;
import by.ezer.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/users")
public class UserController {

    private final UserService userService;

//    @GetMapping("/{id}")
//    public ResponseEntity<UserDTO> findById(@PathVariable Long id) {
//        return userService.findById(id)
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
//    }

//    @GetMapping
//    public ResponseEntity<PagedResult<UserDTO>> findAllPaged (@RequestParam(value = "page", defaultValue = "0") int page,
//                                                              @RequestParam(value = "size", defaultValue = "5") int size) {
//        return ResponseEntity.ok(userService.findAllPaged(page, size));
//    }

    @PostMapping
    public ResponseEntity<UserDTO> save(@RequestBody UserCreateDTO request) {
        return ResponseEntity.ok(userService.createUser(request));
    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<UserDTO> update(@PathVariable Long id, @RequestBody UserDTO userDTO) {
//        userService.updateUser(id, userDTO);
//        return ResponseEntity.ok().build();
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<UserDTO> delete(@PathVariable Long id) {
//        userService.deleteUser(id);
//        return ResponseEntity.noContent().build();
//    }
}
