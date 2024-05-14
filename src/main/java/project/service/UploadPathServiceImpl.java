package project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
@RequiredArgsConstructor
public class UploadPathServiceImpl implements UploadPathService {
    @Value("${fileDirectory}")
    String fileDirectory;

    @Override
    public File getFilePath(String modifiedFileName, String path) {
        boolean exists = new File(fileDirectory + path + "/").exists();
        if (!exists) {
            boolean saved = new File(fileDirectory + path + "/").mkdirs();
            System.out.println("new directory saved : " + saved);
        }
        String modifiedFilePath = fileDirectory + path + "/" + File.separator + modifiedFileName;
        return new File(modifiedFilePath);
    }
}

