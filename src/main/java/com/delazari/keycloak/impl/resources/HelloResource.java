package com.delazari.keycloak.impl.resources;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HelloResource {

    @GetMapping(value = "/hello")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public String hello() {
        return "Hello World";
    }

    @GetMapping(value = "/hi")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public String hi() {
        return "Hi World";
    }

}
