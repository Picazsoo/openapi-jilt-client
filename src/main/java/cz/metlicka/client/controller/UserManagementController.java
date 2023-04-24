package cz.metlicka.client.controller;

import cz.metlicka.client.api.UserApiClient;
import cz.metlicka.client.model.UserCreateRequest;
import cz.metlicka.client.utils.RandomUserGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("user-management")
@Slf4j
public class UserManagementController {

    private final UserApiClient userApiClient;

    @GetMapping("/createRandom")
    public ResponseEntity<URI> createUser() {
        return ResponseEntity.ok(createUserAndGetLocation());
    }

    @GetMapping("/createRandom/{count}")
    public ResponseEntity<List<URI>> createUsers(@PathVariable(name = "count") int count) {
        List<URI> locationUris = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            locationUris.add(createUserAndGetLocation());
        }
        return ResponseEntity.ok(locationUris);
    }

    private URI createUserAndGetLocation() {
        UserCreateRequest user = RandomUserGenerator.prepareRandomUser();
        log.info("Will try persist new random user:\n{}", user);
        ResponseEntity<Void> createUserResponse = userApiClient.createUser(user);
        URI createUserLocation = createUserResponse.getHeaders().getLocation();
        log.info("Response status:{}. Resource available at:{}", createUserResponse.getStatusCode(), createUserLocation);
        return createUserLocation;
    }
}

