package com.kepler.service.impl;

import com.kepler.entity.FileEntity;
import com.kepler.exception.FileHandlingException;
import com.kepler.repository.FileRepository;
import com.kepler.service.IFileService;
import com.kepler.utils.FileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileService implements IFileService {

    private final FileRepository fileRepository;

    @Transactional
    @Override
    public FileEntity save(FileEntity fileEntity, MultipartFile uploadFile) {

        String originalFilename = uploadFile.getOriginalFilename();
        if (originalFilename == null) {
            throw new FileHandlingException("Lỗi khi upload file");
        }

        if (fileEntity == null) { // case create

            fileEntity = new FileEntity();
            fileEntity.setOriginalName(originalFilename);
            fileEntity.setFilePath(FileUtils.generatePath());

            String fileExtension = originalFilename.substring(originalFilename.indexOf('.'));
            String randomFileName = UUID.randomUUID() + fileExtension;
            fileEntity.setGeneratedName(randomFileName);

            fileRepository.save(fileEntity);

        } else { // case update
            FileUtils.delete(fileEntity.getFilePath(), fileEntity.getGeneratedName()); // delete old file
        }

        // save file to directory
        FileUtils.copyToDirectory(uploadFile, fileEntity.getFilePath(), fileEntity.getGeneratedName());

        return fileEntity;
    }

    @Override
    public String loadFilePath(Long fileId) {
        FileEntity fileEntity = fileRepository.findById(fileId)
                                              .orElseThrow(() -> new FileHandlingException("File không tồn tại"));
        return fileEntity.getFilePath();
    }

    @Override
    public void delete(FileEntity file) {
        fileRepository.delete(file);
        fileRepository.flush();
        FileUtils.delete(file.getFilePath(), file.getGeneratedName());

    }

    @Override
    public void delete(Iterable<FileEntity> fileEntities) {
        fileRepository.deleteInBatch(fileEntities);
        fileRepository.flush();
        for (FileEntity fileEntity : fileEntities) {
            FileUtils.delete(fileEntity.getFilePath(), fileEntity.getGeneratedName());
        }
    }
}
