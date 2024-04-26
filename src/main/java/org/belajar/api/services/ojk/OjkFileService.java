package org.belajar.api.services.ojk;

import org.belajar.api.entity.ojk.OjkFile;
import org.jboss.resteasy.reactive.multipart.FileUpload;

public interface OjkFileService {
    OjkFile sendUpload(FileUpload data) throws Exception;
    OjkFile update(Long id, FileUpload data) throws Exception;
}
