package br.ufrn.ePET.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.ufrn.ePET.service.FileStorageService;

@RestController
public class FileStorageController {
	
	@Autowired
	private FileStorageService fileStorageService;
	
	@PostMapping("/uploadfile")
	@ApiOperation(value = "Método que Informa o diretório de upload.")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
	    public String uploadFile(@ApiParam(value = "diretório de upload de arquivos") @RequestParam("file") MultipartFile file) {
	     try {
	    	 String filename = fileStorageService.storeFile(file);
	    	 //System.out.println(filename);
	    	 return filename;
	     } catch(Exception e) {
	    	 return null;
	     }
	 }
	
	@GetMapping("/downloadfile/{filename:.+}")
	@ApiOperation(value = "Método que faz o download de um arquivo.")
	//@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
		public ResponseEntity<Resource> downloadFile(@ApiParam(value = "Link do arquivo") @PathVariable String filename, HttpServletRequest request){
			Resource resource = fileStorageService.loadFileAsResource(filename);
			
			String contentType = null;
	        try {
	            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
	        } catch (IOException ex) {
	            throw new RuntimeException("Não foi possível determinar o tipo do arquivo");
	        }

	        // Fallback to the default content type if type could not be determined
	        if(contentType == null) {
	            contentType = "application/octet-stream";
	        }

	        return ResponseEntity.ok()
	                .contentType(MediaType.parseMediaType(contentType))
	                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
	                .body(resource);
	    }
}
