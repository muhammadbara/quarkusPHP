package org.belajar.api.services.ojk.Implementation;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.belajar.api.entity.event.EventImage;
import org.belajar.api.entity.ojk.OjkFile;
import org.belajar.api.repository.ojk.OjkFileRepository;
import org.belajar.api.services.ojk.OjkFileService;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.resteasy.reactive.multipart.FileUpload;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class OjkFileServiceImpl implements OjkFileService {

    @Inject
    OjkFileRepository ojkFileRepository;
    @ConfigProperty(name = "upload.directory.file")
    String UPLOAD_DIR;


    @Transactional
    @Override
    public OjkFile sendUpload(FileUpload data) throws Exception {

        String fileName =UUID.randomUUID()+data.fileName();

        OjkFile ojkFile = new OjkFile();
        ojkFile.setFileName(fileName);
        ojkFile.setFileSize(data.size());
        ojkFile.setFileType(data.contentType());


        ojkFileRepository.persist(ojkFile);

        Files.copy(data.filePath(), Paths.get(UPLOAD_DIR + fileName));

        return ojkFile;
    }

    @Override
    public OjkFile update(Long id, FileUpload data) throws Exception {
        OjkFile ojkFile = ojkFileRepository.findByOjkId(id).orElseThrow(()->new RuntimeException("Event image with ID " + id + " not found"));

        Files.deleteIfExists(Paths.get(UPLOAD_DIR + ojkFile.getFileName()));

        ojkFile.setFileName(data.fileName());
        ojkFile.setFileType(data.contentType());
        ojkFile.setFileSize(data.size());

        ojkFileRepository.persist(ojkFile);

        String fileName = ojkFile.getFileName();

        Files.copy(data.filePath(), Paths.get(UPLOAD_DIR + fileName));
        return ojkFile;
    }
}
