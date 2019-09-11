package br.ufrn.ePET.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
	     try {
	    	 fileStorageService.storeFile(file);
	    	 return new ResponseEntity<>(HttpStatus.OK);
	     } catch(Exception e) {
	    	 return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	     }
	 }
}
