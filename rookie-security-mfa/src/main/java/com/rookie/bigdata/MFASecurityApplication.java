package com.rookie.bigdata;

import com.rookie.bigdata.security.CustomUser;
import com.rookie.bigdata.security.MapCustomUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.security.crypto.encrypt.BytesEncryptor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author rookie
 * @Description Hello Security application.
 * @Date 2024/6/26 21:10
 * @Version 1.0
 */
@SpringBootApplication
public class MFASecurityApplication extends SpringBootServletInitializer implements CommandLineRunner {

    public static void main(String[] args) {

        SpringApplication.run(MFASecurityApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(MFASecurityApplication.class);
    }

    @Autowired
    private ApplicationContext appContext;

    @Override
    public void run(String... args) throws Exception {
        String[] beans = appContext.getBeanDefinitionNames();
        Arrays.sort(beans);
        for (String bean : beans) {
            System.out.println(bean + " of Type :: " + appContext.getBean(bean).getClass());
        }
    }

    @Bean
    MapCustomUserRepository userRepository(BytesEncryptor encryptor) {
        // the hashed password was calculated using the following code
        // the hash should be done up front, so malicious users cannot discover the
        // password
        // PasswordEncoder encoder =
        // PasswordEncoderFactories.createDelegatingPasswordEncoder();
        // String encodedPassword = encoder.encode("password");

        // the raw password is "password"
        String encodedPassword = "{bcrypt}$2a$10$h/AJueu7Xt9yh3qYuAXtk.WZJ544Uc2kdOKlHu2qQzCh/A3rq46qm";

        // to sync your phone with the Google Authenticator secret, hand enter the value
        // in base32Key
        // String base32Key = "QDWSM3OYBPGTEVSPB5FKVDM3CSNCWHVK";
        // Base32 base32 = new Base32();
        // byte[] b = base32.decode(base32Key);
        // String secret = Hex.encodeHexString(b);

        String hexSecret = "80ed266dd80bcd32564f0f4aaa8d9b149a2b1eaa";
        String encrypted = new String(Hex.encode(encryptor.encrypt(hexSecret.getBytes())));

        // the raw security answer is "smith"
        String encodedSecurityAnswer = "{bcrypt}$2a$10$JIXMjAszy3RUu8y5T0zH0enGJCGumI8YE.K7w3wsM5xXDfeVIsJhq";

        CustomUser customUser = new CustomUser(1L, "user@example.com", encodedPassword, encrypted,
                encodedSecurityAnswer);
        Map<String, CustomUser> emailToCustomUser = new HashMap<>();
        emailToCustomUser.put(customUser.getEmail(), customUser);
        return new MapCustomUserRepository(emailToCustomUser);
    }


}
