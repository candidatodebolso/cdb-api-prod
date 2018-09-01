package br.com.candidatodebolso.api.endpoints.admin.upload;

import br.com.candidatodebolso.api.exceptions.StorageException;
import br.com.candidatodebolso.api.exceptions.FileNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Service
public class StorageServiceImpl implements StorageService {

    private final Path directory;

    @Autowired
    public StorageServiceImpl() {
        this.directory = Paths.get("data");
    }

    @Override
    public void init() {
        try {
            if (!Files.exists(directory) || !Files.isDirectory(directory))
                Files.createDirectory(directory);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void storage(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new StorageException("Falha em salvar o arquivo vazio " + file.getOriginalFilename());
            }
            Files.copy(file.getInputStream(), this.directory.resolve(file.getOriginalFilename()));
        } catch (IOException e) {
            throw new StorageException("Falha em salvar o arquivo " + file.getOriginalFilename());
        }
    }

    @Override
    public Stream<Path> loadAll() {
        return null;
    }

    @Override
    public Path load(String filename) {
        return directory.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new FileNotFoundException("Não foi possivel ler o arquivo " + filename);
            }
        } catch (MalformedURLException e) {
            throw new FileNotFoundException("Não foi possivel ler o arquivo " + filename, e);
        }
    }

    @Override
    public void removeAll() {

    }
}
