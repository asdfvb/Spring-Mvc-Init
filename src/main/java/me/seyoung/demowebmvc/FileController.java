package me.seyoung.demowebmvc;

import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;

@Controller
public class FileController {

    @Autowired
    private ResourceLoader resourceLoader;

    @GetMapping("/file")
    public String fileUploadForm(Model model) {
        return"files/index";
    }

    //file Upload Service Logic
    @PostMapping("/file")
    public String fileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes attributes) {
        //file save

        String message = file.getOriginalFilename() + " is uploaded";
        attributes.addFlashAttribute("message", message);
        return "redirect:/file";
    }

    //ResponseEntity : 응답본문
    @GetMapping("/file/{filename}")
    public ResponseEntity<Resource> fileDownload(@PathVariable String filename) throws IOException {
        //classpath: 는 resources가 root임.
        Resource resource = resourceLoader.getResource ("classpath:"+filename);

        File file = resource.getFile();
        Tika tika = new Tika();
        String mediaType = tika.detect(file);

        return ResponseEntity.ok()
                //file download할때 경로 지정할수 있는 팝업이 뜨도록 하는 설정
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+ resource.getFilename() + "\"")
                //tika(파일의 확장자를 알려주는 API)를 이용하여 파일 타입을 지정해준다.
                .header(HttpHeaders.CONTENT_TYPE, mediaType)
                //File의 크기를 지정해준다.
                .header(HttpHeaders.CONTENT_LENGTH, String.valueOf(file.length()))
                .body(resource);

    }
}
