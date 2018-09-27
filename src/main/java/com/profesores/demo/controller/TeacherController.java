package com.profesores.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.profesores.demo.model.Teacher;
import com.profesores.demo.service.TeacherService;
import com.profesores.demo.util.CustomErrorType;

@Controller
@RequestMapping("/v1")
public class TeacherController {

	@Autowired
	TeacherService _teacherService;
	
	@RequestMapping(value="/teachers", method = RequestMethod.GET, headers = "Accept=Application/json")
	public ResponseEntity<List<Teacher>> getTeachers () {
		
		List<Teacher> teachers = new ArrayList<>();
		
		teachers = _teacherService.findAllTeachers();
		
		if (teachers==null || teachers.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<List<Teacher>>(teachers,HttpStatus.OK);
	}
	
	@RequestMapping(value="/teachers/{id}", method = RequestMethod.GET, headers = "Accept=Application/json")
	public ResponseEntity<Teacher> getTeacherById (@PathVariable("id") Long idTeacher) {
		
		if (idTeacher == null || idTeacher <= 0) {
			return new ResponseEntity(new CustomErrorType("idTeacher is required"), HttpStatus.CONFLICT);
		}
		
		Teacher teacher = _teacherService.findById(idTeacher);
		
		if (teacher == null || teacher.equals(null)) {
			return new ResponseEntity(new CustomErrorType("data empty"), HttpStatus.CONFLICT);
		}
		
		return new ResponseEntity<Teacher>(teacher, HttpStatus.OK);
	}
	
	@RequestMapping(value="/teachers/{id}", method = RequestMethod.DELETE, headers = "Accept=Application/json")
	public ResponseEntity<Teacher> deleteTeacher (@PathVariable("id") Long idTeacher) {
		
		if (idTeacher == null || idTeacher <= 0) {
			return new ResponseEntity(new CustomErrorType("idTeacher is required"), HttpStatus.CONFLICT);
			
		}
		
		Teacher teacher = _teacherService.findById(idTeacher);
		
		if (teacher == null || teacher.equals(null)) {
			return new ResponseEntity(new CustomErrorType("data empty"), HttpStatus.CONFLICT);
		}
		
		_teacherService.deleteTeacherById(idTeacher);
		return new ResponseEntity<Teacher>(HttpStatus.OK);
	}
}
