package project.utility;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import project.model.User;
import project.service.UserService;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

@Slf4j
@Component
public class UtilComponents {
    public byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[4];
        random.nextBytes(bytes);
        return bytes;
    }

    public byte[] Hash(String password, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        return factory.generateSecret(spec).getEncoded();
    }

    public User currentUser(UserService userService) {
        return userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
    }
}
