package com.heavenscode.rac.web.rest;

import com.heavenscode.rac.domain.Employee;
import com.heavenscode.rac.domain.User;
import com.heavenscode.rac.repository.UserRepository;
import com.heavenscode.rac.security.SecurityUtils;
import com.heavenscode.rac.service.EmployeeQueryService;
import com.heavenscode.rac.service.MailService;
import com.heavenscode.rac.service.UserService;
import com.heavenscode.rac.service.criteria.EmployeeCriteria;
import com.heavenscode.rac.service.dto.AdminUserDTO;
import com.heavenscode.rac.service.dto.PasswordChangeDTO;
import com.heavenscode.rac.web.rest.errors.*;
import com.heavenscode.rac.web.rest.vm.KeyAndPasswordVM;
import com.heavenscode.rac.web.rest.vm.ManagedUserVM;
import jakarta.validation.Valid;
import java.util.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.service.filter.StringFilter;

/**
 * REST controller for managing the current user's account.
 */
@RestController
@RequestMapping("/api")
public class AccountResource {

    private static class AccountResourceException extends RuntimeException {

        private AccountResourceException(String message) {
            super(message);
        }
    }

    private final Logger log = LoggerFactory.getLogger(AccountResource.class);

    private final UserRepository userRepository;

    private final UserService userService;

    private final MailService mailService;

    public AccountResource(UserRepository userRepository, UserService userService, MailService mailService) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.mailService = mailService;
    }

    @Autowired
    EmployeeQueryService employeeQueryService;

    /**
     * {@code POST  /register} : register the user.
     *
     * @param managedUserVM the managed user View Model.
     * @throws InvalidPasswordException  {@code 400 (Bad Request)} if the password
     *                                   is incorrect.
     * @throws EmailAlreadyUsedException {@code 400 (Bad Request)} if the email is
     *                                   already used.
     * @throws LoginAlreadyUsedException {@code 400 (Bad Request)} if the login is
     *                                   already used.
     */
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerAccount(@Valid @RequestBody ManagedUserVM managedUserVM) {
        if (isPasswordLengthInvalid(managedUserVM.getPassword())) {
            throw new InvalidPasswordException();
        }
        User user = userService.registerUser(managedUserVM, managedUserVM.getPassword());
        mailService.sendActivationEmail(user);
    }

    /**
     * {@code GET  /activate} : activate the registered user.
     *
     * @param key the activation key.
     * @throws RuntimeException {@code 500 (Internal Server Error)} if the user
     *                          couldn't be activated.
     */
    @GetMapping("/activate")
    public void activateAccount(@RequestParam(value = "key") String key) {
        Optional<User> user = userService.activateRegistration(key);
        if (!user.isPresent()) {
            throw new AccountResourceException("No user was found for this activation key");
        }
    }

    /**
     * {@code GET  /account} : get the current user.
     *
     * @return the current user.
     * @throws RuntimeException {@code 500 (Internal Server Error)} if the user
     *                          couldn't be returned.
     */
    @GetMapping("/account")
    public AdminUserDTO getAccount() {
        System.out.println("getAccount " + SecurityUtils.getCurrentUserLogin().get());
        StringFilter stringFilter = new StringFilter();
        stringFilter.setEquals(SecurityUtils.getCurrentUserLogin().get());
        EmployeeCriteria employeeCriteria = new EmployeeCriteria();
        employeeCriteria.setUsername(stringFilter);
        var emps = employeeQueryService.findByCriteria(employeeCriteria);
        var emp = new Employee();
        var roles = "";
        if (emps.size() > 0) {
            emp = emps.get(0);
            if (emp.getCode().toUpperCase().equals("ADMIN")) {
                emp.setRoleName(emp.getRoleName() + ",ROLE_ADMIN");
            } else {
                emp.setRoleName(emp.getRoleName() + ",ROLE_USER");
            }
        }
        System.out.println();
        return employeeQueryService
            .findByCriteria(employeeCriteria)
            .stream()
            .findFirst()
            .map(a -> {
                if (a.getCode().toUpperCase().equals("ADMIN")) {
                    a.setRoleName(a.getRoleName() + ",ROLE_ADMIN");
                } else {
                    a.setRoleName(a.getRoleName() + ",ROLE_USER");
                }
                var aa = new AdminUserDTO();
                aa.setLogin(a.getUsername());
                aa.setFirstName(a.getFullName());
                aa.setLogin(a.getUsername());
                aa.setEmail(a.getEmail());
                aa.setImageUrl(a.getImagePath());
                Set<String> authorities = Set.of(a.getRoleName().split(","));
                aa.setAuthorities(authorities);
                return aa;
            })
            .orElseThrow(() -> new AccountResourceException("User could not be found"));
        // return userService
        // .getUserWithAuthorities()
        // .map(AdminUserDTO::new)
        // .orElseThrow(() -> new AccountResourceException("User could not be found"));
    }

    /**
     * {@code POST  /account} : update the current user information.
     *
     * @param userDTO the current user information.
     * @throws EmailAlreadyUsedException {@code 400 (Bad Request)} if the email is
     *                                   already used.
     * @throws RuntimeException          {@code 500 (Internal Server Error)} if the
     *                                   user login wasn't found.
     */
    @PostMapping("/account")
    public void saveAccount(@Valid @RequestBody AdminUserDTO userDTO) {
        String userLogin = SecurityUtils.getCurrentUserLogin()
            .orElseThrow(() -> new AccountResourceException("Current user login not found"));
        Optional<User> existingUser = userRepository.findOneByEmailIgnoreCase(userDTO.getEmail());
        if (existingUser.isPresent() && (!existingUser.orElseThrow().getLogin().equalsIgnoreCase(userLogin))) {
            throw new EmailAlreadyUsedException();
        }
        Optional<User> user = userRepository.findOneByLogin(userLogin);
        if (!user.isPresent()) {
            throw new AccountResourceException("User could not be found");
        }
        userService.updateUser(
            userDTO.getFirstName(),
            userDTO.getLastName(),
            userDTO.getEmail(),
            userDTO.getLangKey(),
            userDTO.getImageUrl()
        );
    }

    /**
     * {@code POST  /account/change-password} : changes the current user's password.
     *
     * @param passwordChangeDto current and new password.
     * @throws InvalidPasswordException {@code 400 (Bad Request)} if the new
     *                                  password is incorrect.
     */
    @PostMapping(path = "/account/change-password")
    public void changePassword(@RequestBody PasswordChangeDTO passwordChangeDto) {
        if (isPasswordLengthInvalid(passwordChangeDto.getNewPassword())) {
            throw new InvalidPasswordException();
        }
        userService.changePassword(passwordChangeDto.getCurrentPassword(), passwordChangeDto.getNewPassword());
    }

    /**
     * {@code POST   /account/reset-password/init} : Send an email to reset the
     * password of the user.
     *
     * @param mail the mail of the user.
     */
    @PostMapping(path = "/account/reset-password/init")
    public void requestPasswordReset(@RequestBody String mail) {
        Optional<User> user = userService.requestPasswordReset(mail);
        if (user.isPresent()) {
            mailService.sendPasswordResetMail(user.orElseThrow());
        } else {
            // Pretend the request has been successful to prevent checking which emails
            // really exist
            // but log that an invalid attempt has been made
            log.warn("Password reset requested for non existing mail");
        }
    }

    /**
     * {@code POST   /account/reset-password/finish} : Finish to reset the password
     * of the user.
     *
     * @param keyAndPassword the generated key and the new password.
     * @throws InvalidPasswordException {@code 400 (Bad Request)} if the password is
     *                                  incorrect.
     * @throws RuntimeException         {@code 500 (Internal Server Error)} if the
     *                                  password could not be reset.
     */
    @PostMapping(path = "/account/reset-password/finish")
    public void finishPasswordReset(@RequestBody KeyAndPasswordVM keyAndPassword) {
        if (isPasswordLengthInvalid(keyAndPassword.getNewPassword())) {
            throw new InvalidPasswordException();
        }
        Optional<User> user = userService.completePasswordReset(keyAndPassword.getNewPassword(), keyAndPassword.getKey());

        if (!user.isPresent()) {
            throw new AccountResourceException("No user was found for this reset key");
        }
    }

    private static boolean isPasswordLengthInvalid(String password) {
        return (
            StringUtils.isEmpty(password) ||
            password.length() < ManagedUserVM.PASSWORD_MIN_LENGTH ||
            password.length() > ManagedUserVM.PASSWORD_MAX_LENGTH
        );
    }
}
