package org.belajar.api.services.event;

import org.belajar.api.entity.event.EventImage;
import org.jboss.resteasy.reactive.multipart.FileUpload;

import java.io.IOException;

public interface EventImageService {

    EventImage sendUpload(FileUpload data) throws IOException;
    EventImage update(Long id, FileUpload data) throws IOException;
}
