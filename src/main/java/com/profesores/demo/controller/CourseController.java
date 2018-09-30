package com.profesores.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.profesores.demo.model.Course;
import com.profesores.demo.model.SocialMedia;
import com.profesores.demo.service.CourseService;
import com.profesores.demo.util.CustomErrorType;

@Controller
@RequestMapping("/v1")
public class CourseController {

	@Autowired
	CourseService _courseService;
	
	//GET
	@RequestMapping(value="/courses", method = RequestMethod.GET, headers = "Accept=Application/json")
	public ResponseEntity<List<Course>> getCourses (@RequestParam(value="name", required = false) String name, @RequestParam (value="id_teacher", required = false) Long idTeacher ) {
		List<Course> courses = new ArrayList<>();

		if (idTeacher!=null) {
			courses = _courseService.findByIdTeacher(idTeacher);
			if (courses.isEmpty()) {
				return new ResponseEntity(HttpStatus.NO_CONTENT);
			}
		}
		
		if (name!=null) {
			Course course = _courseService.findByName(name);
			if (course == null) {
				return new ResponseEntity(HttpStatus.NO_CONTENT);
			}
			courses.add(course);
		}
		
		
		courses = _courseService.findAllCourses();
		if (courses == null || courses.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Course>>(courses, HttpStatus.OK);
	}
	
	//GET BY ID
	@RequestMapping(value="/courses/{id}", method = RequestMethod.GET, headers = "Accept=Application/json")
	public ResponseEntity<Course> getCourseById (@PathVariable("id") Long idCourse ) {
		
		if (idCourse == null || idCourse.equals(null) || idCourse <= 0) {
			return new ResponseEntity(new CustomErrorType("idcourse is requered"),  HttpStatus.CONFLICT);
		}
		
		Course course = _courseService.findById(idCourse);
		if (course == null || course.equals(null)) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Course>(course, HttpStatus.OK);
		
	}
	
	//POST
	@RequestMapping(value="/courses", method = RequestMethod.POST, headers = "Accept=Application/json")
	public ResponseEntity<?> createCourses (@RequestBody Course course, 
			UriComponentsBuilder uriComponentsBuilder) {
	
		if (course.getName().equals(null) || course.getProject().isEmpty()) {
			return new ResponseEntity(new CustomErrorType("Course name is requered"),  HttpStatus.CONFLICT);
		}
		
		if (_courseService.findByName(course.getName()) != null) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		
		_courseService.saveCourse(course);
		Course  course2 = _courseService.findByName(course.getName());
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uriComponentsBuilder.path("/v1/courses/{id}")
				.buildAndExpand(course2.getIdCourse()).toUri());
		
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);

	}
	
	//UPDATE
	@RequestMapping(value="/courses/{id}", method = RequestMethod.PATCH, headers = "Accept=Application/json")
	public ResponseEntity<Course> updateCourses (@PathVariable("id") Long idCourse, @RequestBody Course course) {
		
		if (idCourse == null || idCourse.equals(null) || idCourse <= 0) {
			return new ResponseEntity(new CustomErrorType("idCourse is requered"), HttpStatus.CONFLICT);
		}
		
		Course currentCourse = _courseService.findById(idCourse);
		if (currentCourse == null || currentCourse.equals(null)) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		
		currentCourse.setName(course.getName());
		currentCourse.setProject(course.getProject());
		currentCourse.setThemes(course.getThemes());
		
		_courseService.updateCourse(currentCourse);
		return new ResponseEntity<Course>(currentCourse, HttpStatus.OK);
	}
	
	//DELETE
	@RequestMapping(value="/courses/{id}", method = RequestMethod.DELETE, headers = "Accept=Application/json")
	public ResponseEntity<Course> deleteCourse (@PathVariable("id") Long idCourse) {
		
		if (idCourse == null || idCourse.equals(null) || idCourse <= 0) {
			return new ResponseEntity(new CustomErrorType("idCourse is requered"), HttpStatus.CONFLICT);
		}
		
		Course course = _courseService.findById(idCourse);
		if (course == null || course.equals(null)) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		
		_courseService.deleteCourseById(idCourse);
		return new ResponseEntity<Course>(HttpStatus.OK);
	}
}
