package com.heavenscode.rac.web.rest;

import static com.heavenscode.rac.security.SecurityUtils.AUTHORITIES_KEY;
import static com.heavenscode.rac.security.SecurityUtils.JWT_ALGORITHM;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.heavenscode.rac.domain.Employee;
import com.heavenscode.rac.service.EmployeeQueryService;
import com.heavenscode.rac.service.criteria.EmployeeCriteria;
import com.heavenscode.rac.web.rest.vm.LoginVM;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.service.filter.StringFilter;

/**
 * Controller to authenticate users.
 */
@RestController
@RequestMapping("/api")
public class AuthenticateController {

    private final Logger log = LoggerFactory.getLogger(AuthenticateController.class);

    private final JwtEncoder jwtEncoder;

    @Value("${jhipster.security.authentication.jwt.token-validity-in-seconds:0}")
    private long tokenValidityInSeconds;

    @Value("${jhipster.security.authentication.jwt.token-validity-in-seconds-for-remember-me:0}")
    private long tokenValidityInSecondsForRememberMe;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    // @Autowired
    // EmployeeCriteria employeeCriteria;

    @Autowired
    EmployeeQueryService employeeQueryService;

    public AuthenticateController(JwtEncoder jwtEncoder, AuthenticationManagerBuilder authenticationManagerBuilder) {
        this.jwtEncoder = jwtEncoder;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }

    // @PostMapping("/authenticate")
    // public ResponseEntity<JWTToken> authorize(@Valid @RequestBody LoginVM loginVM) {
    //     UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
    //             loginVM.getUsername(),
    //             loginVM.getPassword());

    //     Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
    //     SecurityContextHolder.getContext().setAuthentication(authentication);
    //     String jwt = this.createToken(authentication, loginVM.isRememberMe());
    //     HttpHeaders httpHeaders = new HttpHeaders();
    //     httpHeaders.setBearerAuth(jwt);
    //     return new ResponseEntity<>(new JWTToken(jwt), httpHeaders, HttpStatus.OK);
    // }

    @PostMapping("/authenticate")
    public ResponseEntity<JWTToken> authorize(@Valid @RequestBody LoginVM loginVM) {
        StringFilter stringFilter = new StringFilter();
        stringFilter.setEquals(loginVM.getUsername());
        EmployeeCriteria employeeCriteria = new EmployeeCriteria();
        employeeCriteria.setUsername(stringFilter);
        var emps = employeeQueryService.findByCriteria(employeeCriteria);
        var emp = new Employee();
        var isAdmin = "";
        if (emps.size() > 0) {
            emp = emps.get(0);
            if (emp.getCode().toUpperCase().equals("ADMIN")) {
                isAdmin = ",ROLE_ADMIN";
            } else {
                isAdmin = emp.getRoleName() + ",ROLE_USER";
            }
        }
        var pass = emp.getPassword();
        var decodedPass = new String(Base64.getDecoder().decode(pass));
        if (decodedPass.equals(loginVM.getPassword())) {
            String jwt = this.createToken2(loginVM.getUsername(), isAdmin, loginVM.isRememberMe());
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setBearerAuth(jwt);
            return new ResponseEntity<>(new JWTToken(jwt), httpHeaders, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatusCode.valueOf(401));
    }

    /**
     * {@code GET /authenticate} : check if the user is authenticated, and return
     * its login.
     *
     * @param request the HTTP request.
     * @return the login if the user is authenticated.
     */
    @GetMapping("/authenticate")
    public String isAuthenticated(HttpServletRequest request) {
        log.debug("REST request to check if the current user is authenticated");
        return request.getRemoteUser();
    }

    public String createToken(Authentication authentication, boolean rememberMe) {
        String authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(" "));

        Instant now = Instant.now();
        Instant validity;
        if (rememberMe) {
            validity = now.plus(this.tokenValidityInSecondsForRememberMe, ChronoUnit.SECONDS);
        } else {
            validity = now.plus(this.tokenValidityInSeconds, ChronoUnit.SECONDS);
        }

        // @formatter:off
        JwtClaimsSet claims = JwtClaimsSet.builder()
            .issuedAt(now)
            .expiresAt(validity)
            .subject(authentication.getName())
            .claim(AUTHORITIES_KEY, authorities)
            .build();

        JwsHeader jwsHeader = JwsHeader.with(JWT_ALGORITHM).build();
        return this.jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).getTokenValue();
    }

    public String createToken2(String username,String authorities, boolean rememberMe) {
   

        Instant now = Instant.now();
        Instant validity;
        if (rememberMe) {
            validity = now.plus(this.tokenValidityInSecondsForRememberMe, ChronoUnit.SECONDS);
        } else {
            validity = now.plus(this.tokenValidityInSeconds, ChronoUnit.SECONDS);
        }

        // @formatter:off
        JwtClaimsSet claims = JwtClaimsSet.builder()
            .issuedAt(now)
            .expiresAt(validity)
            .subject(username)
            .claim(AUTHORITIES_KEY, authorities)
            .build();

        JwsHeader jwsHeader = JwsHeader.with(JWT_ALGORITHM).build();
        return this.jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).getTokenValue();
    }

    /**
     * Object to return as body in JWT Authentication.
     */
    static class JWTToken {

        private String idToken;

        JWTToken(String idToken) {
            this.idToken = idToken;
        }

        @JsonProperty("id_token")
        String getIdToken() {
            return idToken;
        }

        void setIdToken(String idToken) {
            this.idToken = idToken;
        }
    }
}
