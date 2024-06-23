package zip.zipzoong.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import zip.zipzoong.domain.entity.Board;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
public class BoardImgForm {

    private List<MultipartFile> files;
    private Board board;
}
