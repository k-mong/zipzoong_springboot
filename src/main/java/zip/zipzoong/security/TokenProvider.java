package zip.zipzoong.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import zip.zipzoong.dto.MemberLoginDto;

@Component
public class TokenProvider {

    @Value("${jwt.secret}")
    private String secretKey;

    private static final long EXPRIATION_DATE = 1000 * 60 * 30;

    public String generationToken(MemberLoginDto memberLoginDto) {
        Claims claims = Jwts.claims().setSubject(memberLoginDto.getEmail());
        return null;
    }
}
