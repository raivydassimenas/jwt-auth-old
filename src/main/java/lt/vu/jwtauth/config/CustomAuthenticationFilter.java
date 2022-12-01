package lt.vu.jwtauth.config;

import org.springframework.context.annotation.ComponentScan;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@ComponentScan(basePackages = "lt.vu.jwtauth.service")
public class CustomAuthenticationFilter implements AuthenticationProvider {

    @Resource
    UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) {
        String username = authentication.getName();

        if (StringUtils.isEmpty(username)) {
            throw new BadCredentialsException(("Invalid login details"));
        }

        UserDetails user;
        try {
            user = userDetailsService.loadUserByUsername(username);
        } catch (UsernameNotFoundException exception) {
            throw new BadCredentialsException("Invalid login details");
        }

        String password = authentication.getCredentials().toString();

        UsernamePasswordAuthenticationToken token =  new UsernamePasswordAuthenticationToken(username, password, user.getAuthorities());
        token.setDetails(authentication.getDetails());
        return token;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

//    @Override
//    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
//        String username = request.getParameter("username");
//        String password = request.getParameter("password");
//
//        log.info("Username is {}", username);
//        log.info("Password is {}", password);
//
//        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
//
//        return authenticationManager.authenticate(authenticationToken);
//    }
//
//    @Override
//    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
//        User user = (User) authResult.getPrincipal();
//        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
//        String access_token = JWT.create()
//                .withSubject(user.getUsername())
//                .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
//                .withIssuer(request.getRequestURI())
//                .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
//                .sign(algorithm);
//
//        String refresh_token = JWT.create()
//                .withSubject(user.getUsername())
//                .withExpiresAt(new Date(System.currentTimeMillis() + 30 * 10 * 1000))
//                .withIssuer(request.getRequestURI())
//                .sign(algorithm);
//
//        Map<String, String> tokens = new HashMap<>();
//        tokens.put("access_token", access_token);
//        tokens.put("refresh_token", refresh_token);
//
//        response.setContentType(APPLICATION_JSON_VALUE);
//
//        new ObjectMapper().writeValue(response.getOutputStream(), tokens);
//    }
}
