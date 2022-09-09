package com.kepler.service;

import com.kepler.entity.FileEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IFileService {

    FileEntity save(FileEntity fileEntity, MultipartFile multipartFile);

    String loadFilePath(Long fileId);

    void delete(FileEntity fileEntity);

    void delete(Iterable<FileEntity> files);

}
