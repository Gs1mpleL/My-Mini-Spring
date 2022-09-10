package com.wanfeng.myminispring.core.io;

import lombok.Data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@Data
public class FileSystemResource implements Resource{
    private final String filePath;

    @Override
    public InputStream getInputStream() throws IOException {
        try {
            Path path = new File(this.filePath).toPath();
            return Files.newInputStream(path);
        }catch (NoSuchFieldError e){
            throw new FileNotFoundException(e.getMessage());
        }
    }
}
