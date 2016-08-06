package org.ufcg.si.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.ufcg.si.models.User;
import org.ufcg.si.repositories.UserService;
import org.ufcg.si.repositories.UserServiceImpl;
import org.ufcg.si.util.ServerConstants;

/**
 * This controller class uses JSON data format to be the 
 * endpoint of requests related to directories of users
 * on the server-side.
 * 
 * <strong>This controller needs to a valid token to be accessed.</strong>
 */
@RestController
@RequestMapping(ServerConstants.SERVER_REQUEST_URL + ServerConstants.USERS_DIRECTORY_REQUEST_URL)
public class UsersDirectoryController {
	private UserService userService;
	
	@Autowired
	public void setUserService(UserServiceImpl userServiceImpl) {
		this.userService = userServiceImpl;
	}
	
	@RequestMapping(value = "/newfolder/{dirname}", 
					method = RequestMethod.POST,
					produces = MediaType.APPLICATION_JSON_VALUE,
					consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> addDirectory(@RequestBody User user, @PathVariable String dirname) throws Exception {
		User newUser = userService.findByUsername(user.getUsername());
		newUser.getUserDirectory().createDirectory(dirname);
		User updateUser = userService.update(newUser);
		return new ResponseEntity<>(updateUser, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/newfolder/{dirpath}/{dirname}", 
				method = RequestMethod.POST,
				produces = MediaType.APPLICATION_JSON_VALUE,
				consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> addDirectory(@RequestBody User user, @PathVariable String dirname, @PathVariable String dirpath) throws Exception {
		User newUser = userService.findByUsername(user.getUsername());
		newUser.getUserDirectory().createDirectory(dirname,dirpath);
		User updateUser = userService.update(newUser);
		return new ResponseEntity<>(updateUser, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/newfile/{filename}",
				   	method = RequestMethod.POST,
				   	produces = MediaType.APPLICATION_JSON_VALUE,
				   	consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> addFile(@RequestBody User user, @PathVariable("filename") String fileName) throws Exception {
		User newUser = userService.findByUsername(user.getUsername());
		newUser.getUserDirectory().createFile(fileName, "txt", new String ("Curau Mago!"));
		User updateUser = userService.update(newUser);
		return new ResponseEntity<>(updateUser, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/newfile/{filepath}/{filename}",
		   			method = RequestMethod.POST,
		   			produces = MediaType.APPLICATION_JSON_VALUE,
		   			consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> addFile(@RequestBody User user, @PathVariable("filename") String fileName, @PathVariable("filepath") String filePath) throws Exception {
		User newUser = userService.findByUsername(user.getUsername());
		newUser.getUserDirectory().createFile(fileName, "txt", new String ("Curau Mago!"), filePath);
		User updateUser = userService.update(newUser);
		return new ResponseEntity<>(updateUser, HttpStatus.OK);
	}
}