package org.belajar.api.services.event.implementation;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import org.belajar.api.entity.event.EventImage;
import org.belajar.api.repository.event.EventImageRepository;
import org.belajar.api.services.event.EventImageService;


import org.eclipse.microprofile.config.inject.ConfigProperty;

import org.jboss.resteasy.reactive.multipart.FileUpload;
import java.io.IOException;
import java.nio.file.Files;

import java.nio.file.Paths;
import java.util.*;



@ApplicationScoped
public class EventImageServiceImpl implements EventImageService {
    @Inject
    EventImageRepository eventImageRepository;

    @ConfigProperty(name = "upload.directory.image")
    String UPLOAD_DIR;

    @Transactional
    @Override
    public EventImage sendUpload(FileUpload data) throws IOException {
        List<String> mimetype = Arrays.asList("image/jpg", "image/jpeg", "image/gif", "image/png");

        if (!mimetype.contains(data.contentType())) {
            throw new IOException("File not suported");
        }

        if (data.size() > 1024 * 1024 * 4) {
            throw new IOException("File much large");
        }

        String fileName = UUID.randomUUID() + data.fileName();

        EventImage eventImage = new EventImage();
        eventImage.setImageName(data.fileName());
        eventImage.setImageType(data.contentType());
        eventImage.setImageSize(data.size());


        eventImageRepository.persist(eventImage);

        Files.copy(data.filePath(), Paths.get(UPLOAD_DIR + fileName));

        return eventImage;
    }

    @Override
    public EventImage update(Long id, FileUpload data) throws IOException {
        EventImage eventImage = eventImageRepository.findById(id);
        if (eventImage == null) {
            throw new IllegalArgumentException("Event image with ID " + id + " not found");
        }

        String fileName = eventImage.getImageName();


        Files.deleteIfExists(Paths.get(UPLOAD_DIR + fileName));

        eventImage.setImageName(data.fileName());
        eventImage.setImageType(data.contentType());
        eventImage.setImageSize(data.size());

        eventImageRepository.persist(eventImage);



        Files.copy(data.filePath(), Paths.get(UPLOAD_DIR +fileName));
        return eventImage;
    }

}
