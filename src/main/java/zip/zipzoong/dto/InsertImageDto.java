package zip.zipzoong.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@AllArgsConstructor
public class InsertImageDto {
    private List<MultipartFile> files;
}
