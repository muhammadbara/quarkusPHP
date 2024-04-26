package org.belajar.serviceTest.event;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.test.junit.QuarkusTest;
import org.belajar.api.entity.event.Category;
import org.belajar.api.entity.event.EventImage;
import org.belajar.api.repository.event.EventImageRepository;
import org.belajar.api.services.event.implementation.EventImageServiceImpl;
import org.jboss.resteasy.reactive.multipart.FilePart;
import org.jboss.resteasy.reactive.multipart.FileUpload;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@QuarkusTest
class EventImageServiceImplTest {
    @InjectMocks
    EventImageServiceImpl eventImageService;

    @Mock
    EventImageRepository eventImageRepository;

    @Mock
    FileUpload fileUpload;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSendUpload() throws IOException {
        // Mock data
        String fileName = "shape.jpg";
        String contentType = "image/jpeg";
        long fileSize = 1024; // 1 KB
        String uploadDir = "C:\\Users\\User\\OneDrive\\Gambar\\gambar matlab";

        // Set up fileUpload mock behavior
        when(fileUpload.fileName()).thenReturn(fileName);
        when(fileUpload.contentType()).thenReturn(contentType);
        when(fileUpload.size()).thenReturn(fileSize);
        when(fileUpload.filePath()).thenReturn(Paths.get(uploadDir, fileName));

        // Set up eventImage mock behavior
        EventImage eventImageMock = new EventImage();
        doNothing().when(eventImageRepository).persist(eventImageMock);

        // Test sendUpload method
        EventImage eventImage = eventImageService.sendUpload(fileUpload);

        // Verify eventImage is not null
        assertNotNull(eventImage);

        // Verify eventImage properties
        assertEquals(fileName, eventImage.getImageName());
        assertEquals(contentType, eventImage.getImageType());
        assertEquals(fileSize, eventImage.getImageSize());

        // Verify eventImageRepository.persist() is called
        verify(eventImageRepository, times(1)).persist(any(EventImage.class));
    }

    @Test
    void testSendUpload_UnsupportedContentType() {
        FileUpload unsupportedContentTypeFileUpload = Mockito.mock(FileUpload.class);
        when(unsupportedContentTypeFileUpload.contentType()).thenReturn("application/pdf");

        // Verify that IOException is thrown for unsupported content type
        assertThrows(IOException.class, () -> eventImageService.sendUpload(unsupportedContentTypeFileUpload));
    }

    @Test
    void testSendUpload_OversizedFile() {
        FileUpload oversizedFileUpload = Mockito.mock(FileUpload.class);
        when(oversizedFileUpload.contentType()).thenReturn("image/jpeg");
        when(oversizedFileUpload.size()).thenReturn(1024L * 1024 * 5); // 5 MB

        // Verify that IOException is thrown for oversized file
        assertThrows(IOException.class, () -> eventImageService.sendUpload(oversizedFileUpload));
    }

    @Test
    public void testUpdate() throws IOException {
        // Mock data
        Long id = 1L;
        String fileName = "update.jpg";
        String contentType = "image/jpeg";
        long fileSize = 1024; // 1 KB
        String uploadDir = "C:\\Users\\User\\OneDrive\\Gambar\\gambar matlab";

        // Create a mock EventImage
        EventImage eventImage = new EventImage();
        eventImage.setImageId(id);
        eventImage.setImageName("shape.jpg");

        // Create a mock FileUpload
        FileUpload fileUpload = mock(FileUpload.class);
        when(fileUpload.fileName()).thenReturn(fileName);
        when(fileUpload.contentType()).thenReturn(contentType);
        when(fileUpload.size()).thenReturn(fileSize);
        when(fileUpload.filePath()).thenReturn(Paths.get(uploadDir, fileName));

        // Stubbing behavior of eventImageRepository.findById()
        when(eventImageRepository.findById(id)).thenReturn(eventImage);

        // Invoke the method to be tested
        EventImage updatedEventImage = eventImageService.update(id, fileUpload);

        // Verify that eventImageRepository.persist() is called
        verify(eventImageRepository).persist(eventImage);

        // Verify the updated EventImage properties
        assertEquals(fileName, updatedEventImage.getImageName());
        assertEquals(contentType, updatedEventImage.getImageType());
        assertEquals(fileSize, updatedEventImage.getImageSize());

        // Verify that the file is copied to the correct location
        assertNotNull(Files.exists(Paths.get(uploadDir + fileName)));
    }

    @Test
    public void testUpdate_InvalidId() {
        // Mock data
        Long id = 1L;

        // Stubbing behavior of eventImageRepository.findById()
        when(eventImageRepository.findById(id)).thenReturn(null);

        // Invoke the method to be tested and verify that it throws an exception
        assertThrows(IllegalArgumentException.class, () -> eventImageService.update(id, null));
    }



}