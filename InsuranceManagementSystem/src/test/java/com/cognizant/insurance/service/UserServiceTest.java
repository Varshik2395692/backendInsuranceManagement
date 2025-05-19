//package com.cognizant.insurance.security;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//import java.util.Optional;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.modelmapper.ModelMapper;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//
//import com.cognizant.insurance.dto.LoginRequestDTO;
//import com.cognizant.insurance.dto.LoginResponseDTO;
//import com.cognizant.insurance.dto.RegisterRequestDTO;
//import com.cognizant.insurance.dto.RegisterResponseDTO;
//import com.cognizant.insurance.entity.Agent;
//import com.cognizant.insurance.entity.Customer;
//import com.cognizant.insurance.entity.Users;
//import com.cognizant.insurance.exception.AllException.UserNotExist;
//import com.cognizant.insurance.repository.AgentRepository;
//import com.cognizant.insurance.repository.CustomerRepository;
//import com.cognizant.insurance.repository.UserRepo;
//import com.cognizant.insurance.security.JWTService;
//import com.cognizant.insurance.security.UserService;
//
//@ExtendWith(MockitoExtension.class)
//public class UserServiceTest {
//
//    @Mock
//    private ModelMapper modelMapper;
//
//    @Mock
//    private AgentRepository agentRepository;
//
//    @Mock
//    private UserRepo userRepository;
//
//    @Mock
//    private CustomerRepository customerRepository;
//
//    @Mock
//    private JWTService jwtService;
//
//    @Mock
//    private AuthenticationManager authenticationManager;
//
//    @InjectMocks
//    private UserService userService;
//
//    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
//
//    private Users user;
//    private RegisterRequestDTO registerRequestDTO;
//    private RegisterResponseDTO registerResponseDTO;
//    private LoginRequestDTO loginRequestDTO;
//    private LoginResponseDTO loginResponseDTO;
//
//    @BeforeEach
//    public void setUp() {
//        user = new Users();
//        user.setUserId(1);
//        user.setName("John Doe");
//        user.setEmail("john.doe@example.com");
//        user.setPassword("password");
//        user.setRole(ROLE_AGENT);
//
//        registerRequestDTO = new RegisterRequestDTO();
//        registerRequestDTO.setName("John Doe");
//        registerRequestDTO.setEmail("john.doe@example.com");
//        registerRequestDTO.setPassword("password");
//        registerRequestDTO.setRole("ROLE_AGENT");
//
//        registerResponseDTO = new RegisterResponseDTO();
//        registerResponseDTO.setName("John Doe");
//        registerResponseDTO.setEmail("john.doe@example.com");
//        registerResponseDTO.setRole("ROLE_AGENT");
//
//        loginRequestDTO = new LoginRequestDTO();
//        loginRequestDTO.setEmail("john.doe@example.com");
//        loginRequestDTO.setPassword("password");
//
//        loginResponseDTO = new LoginResponseDTO();
//        loginResponseDTO.setName("John Doe");
//        loginResponseDTO.setEmail("john.doe@example.com");
//        loginResponseDTO.setRole("ROLE_AGENT");
//    }
//
//    @Test
//    public void testRegisterAgent() {
//        when(modelMapper.map(registerRequestDTO, Users.class)).thenReturn(user);
//        when(agentRepository.save(any(Agent.class))).thenReturn(new Agent());
//        when(modelMapper.map(any(Agent.class), RegisterResponseDTO.class)).thenReturn(registerResponseDTO);
//
//        RegisterResponseDTO result = userService.register(registerRequestDTO);
//
//        assertNotNull(result);
//        assertEquals(registerResponseDTO.getEmail(), result.getEmail());
//        verify(agentRepository, times(1)).save(any(Agent.class));
//    }
//
//    @Test
//    public void testRegisterCustomer() {
//        user.setRole(Users.Role.ROLE_CUSTOMER);
//        registerRequestDTO.setRole("ROLE_CUSTOMER");
//        when(modelMapper.map(registerRequestDTO, Users.class)).thenReturn(user);
//        when(customerRepository.save(any(Customer.class))).thenReturn(new Customer());
//        when(modelMapper.map(any(Customer.class), RegisterResponseDTO.class)).thenReturn(registerResponseDTO);
//
//        RegisterResponseDTO result = userService.register(registerRequestDTO);
//
//        assertNotNull(result);
//        assertEquals(registerResponseDTO.getEmail(), result.getEmail());
//        verify(customerRepository, times(1)).save(any(Customer.class));
//    }
//
//    @Test
//    public void testRegisterInvalidRole() {
//        user.setRole(null);
//        registerRequestDTO.setRole("ROLE_INVALID");
//        when(modelMapper.map(registerRequestDTO, Users.class)).thenReturn(user);
//
//        assertThrows(UserNotExist.class, () -> {
//            userService.register(registerRequestDTO);
//        });
//    }
//
//    @Test
//    public void testLogin() {
//        when(userRepository.findByEmail(loginRequestDTO.getEmail())).thenReturn(Optional.of(user));
//        when(modelMapper.map(user, LoginResponseDTO.class)).thenReturn(loginResponseDTO);
//        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
//                .thenReturn(mock(Authentication.class));
//        when(jwtService.generateToken(user)).thenReturn("token");
//
//        LoginResponseDTO result = userService.login(loginRequestDTO);
//
//        assertNotNull(result);
//        assertEquals(loginResponseDTO.getEmail(), result.getEmail());
//        assertEquals("token", result.getToken());
//    }
//
//    @Test
//    public void testLoginUserNotExist() {
//        when(userRepository.findByEmail(loginRequestDTO.getEmail())).thenReturn(Optional.empty());
//
//        assertThrows(UserNotExist.class, () -> {
//            userService.login(loginRequestDTO);
//        });
//    }
//}
