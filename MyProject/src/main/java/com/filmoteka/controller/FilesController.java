package com.filmoteka.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FilesController {
	private static final String POSTERS_FILEPATH = "C:\\images";
	private static final String NO_IMAGE = "no-photo.jpg";

	@RequestMapping(value = "/getPic", method = RequestMethod.GET,produces = MediaType.IMAGE_JPEG_VALUE)
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
