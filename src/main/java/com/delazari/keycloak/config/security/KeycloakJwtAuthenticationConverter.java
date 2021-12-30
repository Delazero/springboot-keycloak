package com.delazari.keycloak.config.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class KeycloakJwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    private final JwtGrantedAuthoritiesConverter defaultGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
    private final ObjectMapper objectMapper;

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        Collection<GrantedAuthority> authorities = Stream
                .concat(defaultGrantedAuthoritiesConverter.convert(jwt).stream(), extractAuthorities(jwt).stream())
                .collect(Collectors.toSet());
        return new JwtAuthenticationToken(jwt, authorities);
    }

    private Collection<GrantedAuthority> extractAuthorities(Jwt jwt) {
        Set<String> rolesWithPrefix = new HashSet<>();
        rolesWithPrefix.addAll(getRealmRoles(jwt));
        return AuthorityUtils.createAuthorityList(rolesWithPrefix.toArray(new String[0]));
    }

    private Set<String> getRealmRoles(Jwt jwt) {
        Set<String> rolesWithPrefix = new HashSet<>();
        JsonNode json = objectMapper.convertValue(jwt.getClaim("authorities"), JsonNode.class);
        json.elements().forEachRemaining(
                e -> rolesWithPrefix.add(createRole(e.asText())));
        return rolesWithPrefix;
    }

    private String createRole(String... values) {
        StringBuilder role = new StringBuilder("ROLE");
        for (String value : values) {
            role.append("_").append(value.toUpperCase());
        }
        return role.toString();
    }

}
