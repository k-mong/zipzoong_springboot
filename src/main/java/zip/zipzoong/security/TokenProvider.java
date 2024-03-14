package zip.zipzoong.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import zip.zipzoong.dto.MemberLoginDto;

import java.util.Date;

@Component
public class TokenProvider {

    @Value("${jwt.secret}")
    private String secretKey;

    private static final long EXPRIATION_DATE = 1000 * 60 * 60;
    private static final long REFRESH_EXPRIATION_DATE = 1000 * 60 * 60 * 24 * 3; // 3일

    public String generationToken(String userId) {
        Claims claims = Jwts.claims().setSubject(userId);
        Date now = new Date();
        Date ExpireDate = new Date(now.getTime()+EXPRIATION_DATE);  // 1시간 뒤의 시간

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(ExpireDate)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public Claims parseClaims(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }

    public String getUserId(String token) {
        return parseClaims(token).getSubject();
    }

    public boolean checkValidToken(String token) {
        // 유효하면 true, 아니면 false
        return !parseClaims(token).getExpiration().before(new Date());
    }

    public String generateRefreshToken(MemberLoginDto memberLoginDto) {
        Claims claims = Jwts.claims().setSubject(memberLoginDto.getEmail());
        Date now = new Date();
        Date ExpireDate = new Date(now.getTime()+REFRESH_EXPRIATION_DATE);  // 3일 뒤의 시간

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(ExpireDate)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String checkToken(String refreshToken) {
        if(checkValidToken(refreshToken)) { // refreshToken 이 유효하면 true 아니면 false
            String userId = getUserId(refreshToken);
            return generationToken(userId);
        }
        return null;
    }
}
