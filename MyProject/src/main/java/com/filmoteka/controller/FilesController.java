package com.filmoteka.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FilesController {
	private static final String IO_ERROR_MESSAGE = "An error occured while uploading your file. Please try again!";
	
	private static final String POSTERS_FILEPATH = "C:\\images";
	private static final String USER_IMAGES_FILEPATH = "C:\\usersImages";
	private static final String TRAILERS_FILEPATH = "C:\\trailers";
	private static final String NO_IMAGE = "no-photo.jpg";
	
	
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public String showPictureUpload() {
		return "pictureTest";
	}
	
	@RequestMapping(value = "/posterupload", method = RequestMethod.POST)
	public String uploadPictureTest(Model m, @RequestParam("file") MultipartFile uploadedFile) throws IOException {
		
		String newFileName = uploadFile(uploadedFile, POSTERS_FILEPATH, null);
		
		if(newFileName != null && !newFileName.isEmpty()) {

			m.addAttribute("imagesrc", newFileName);
		}
		return "pictureTest";
	}
	
	public static final String uploadTrailer(MultipartFile uploadedFile, String filename) throws IOException{
		return uploadFile(uploadedFile, TRAILERS_FILEPATH, filename);
	}
	
	public static final String uploadPoster(MultipartFile uploadedFile, String filename) throws IOException {
		return uploadFile(uploadedFile, POSTERS_FILEPATH, filename);
	}
	
	private static final String uploadFile(MultipartFile uploadedFile, String filepath, String filename) throws IOException {
		try {
			//Check if file is empty
			if(uploadedFile.isEmpty()) {
				return null;
			}
			
			//If the given filename is null
			if(filename == null || filename.isEmpty()) {
				filename = uploadedFile.getOriginalFilename();
			}
			
			//Create new file at the given destination
			File newFile = new File(filepath + File.separator + filename);

			//Copy multipart file data into the new file
			Files.copy(uploadedFile.getInputStream(), newFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
			return newFile.getName();
		}
		catch (IOException e) {
			throw new IOException(IO_ERROR_MESSAGE, e);
		}
	}
	
	
	@RequestMapping(value = "/getPic", method = RequestMethod.GET)
	@ResponseBody
	public byte[] getPicture(@RequestParam("pic") String pic) throws FileNotFoundException, IOException {
		//Grab the file where the picture is saved at
		File f = new File(POSTERS_FILEPATH + File.separator + pic);
		
		//Check if the file exists
		if(!f.exists()) {
			//Assign the no image photo to the product without an image
			f = new File(POSTERS_FILEPATH + File.separator + NO_IMAGE);
		}
		
		byte[] byteArray = new byte[(int) f.length()];
		//Write the picture in a byte array and return it
		try(BufferedInputStream is = new BufferedInputStream(new FileInputStream(f));){
			is.read(byteArray);
		}
		
		return byteArray;
	}
}
