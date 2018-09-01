package br.com.candidatodebolso.api.endpoints.admin.upload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api")
public class FileUploadEndpoint {

    private final StorageService storageService;

    @Autowired
    public FileUploadEndpoint(StorageService storageService) {
        this.storageService = storageService;
    }

    @PostMapping("admin/upload")
    public ResponseEntity<?> upload(@RequestBody MultipartFile file) {
        storageService.storage(file);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON_UTF8).body("{\"filename\":\"" + file.getOriginalFilename() + "\"}");
    }

    @GetMapping("protected/files/{filename:.+}")
    public ResponseEntity<?> loadFile(@PathVariable String filename) {
        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(file);
    }
}
