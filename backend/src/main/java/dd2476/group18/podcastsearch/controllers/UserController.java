package dd2476.group18.podcastsearch.controllers;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dd2476.group18.podcastsearch.models.User;
import dd2476.group18.podcastsearch.repositories.UserRepository;
import dd2476.group18.podcastsearch.views.View;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private final UserRepository userRepository;

    @PutMapping("/{id}")
    @CrossOrigin(origins = "http://localhost:3000")
    @JsonView(View.Detail.class)
    public User updateUser(@PathVariable(name = "id") Integer id) {
        Optional<User> userQS = userRepository.findById(id);
        if (!userQS.isPresent()) {
            return null;
        }
        User user = userQS.get();
        return user;
    }
}
