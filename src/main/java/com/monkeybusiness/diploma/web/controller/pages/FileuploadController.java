package com.monkeybusiness.diploma.web.controller.pages;

import com.monkeybusiness.diploma.core.document.Document;
import com.monkeybusiness.diploma.core.document.DocumentStatus;
import com.monkeybusiness.diploma.core.service.DocumentService;
import com.monkeybusiness.diploma.web.controller.dto.AbstractDto;
import com.monkeybusiness.diploma.web.controller.dto.DocumentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@CrossOrigin
@RestController
public class FileuploadController {
//  @Value("${upload.path}")
  private String uploadPath = "/resources/files";

  @Autowired
  private ServletContext context;

  @Autowired
  private DocumentService documentService;

  @RequestMapping(value = "/fileupload", headers = ("content-type=multipart/*"), method = RequestMethod.POST)
  public DocumentDto upload(@RequestParam("file") MultipartFile inputFile, HttpSession session) {
    DocumentDto documentDto = new DocumentDto();
    if (!inputFile.isEmpty()) {
      try {
        String originalFilename = StringUtils.cleanPath(inputFile.getOriginalFilename());
        Path destination = Paths.get(context.getRealPath(uploadPath)).toAbsolutePath().normalize();
        try {
          Files.createDirectories(destination);
        } catch (Exception ex) {
          documentDto.setSuccessful(false);
          addRoleAndId(documentDto, session);
          return documentDto;
        }
        Path targetLocation = destination.resolve(originalFilename);
        Files.copy(inputFile.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        Document document = new Document();
        document.setPath(targetLocation.toString());
        document.setStatus(DocumentStatus.UPLOADED.name());
        document.setUserId((Long) session.getAttribute("userId"));
        documentService.save(document);
        documentDto.setSuccessful(true);
        documentDto.setId(document.getId());
        addRoleAndId(documentDto, session);
        return documentDto;
      } catch (Exception e) {
        documentDto.setSuccessful(false);
        addRoleAndId(documentDto, session);
        return documentDto;
      }
    } else {
      documentDto.setSuccessful(false);
      addRoleAndId(documentDto, session);
      return documentDto;
    }
  }

  @RequestMapping(value = "/download/{id}", method = RequestMethod.GET)
  public ResponseEntity<Resource> upload(@PathVariable Long id) throws IOException {
    Document document = documentService.getDocument(id);
    Path path = Paths.get(document.getPath());
    ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

    return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_OCTET_STREAM)
            .body(resource);
  }

  private void addRoleAndId(AbstractDto dto, HttpSession session) {
    dto.setUser_role((String) session.getAttribute("user_role"));
    dto.setUser_id((Long) session.getAttribute("user_id"));
  }
}
