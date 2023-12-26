package com.camel.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.camel.entity.Course;
import com.camel.repository.CourseRepository;

@Service
public class CourseService {
	
	@Autowired
	private CourseRepository courseRepository;
	
	
	public List<Course> all(){
		return courseRepository.findAll();
	}
	
	public Course save(Course course) {
		return courseRepository.save(course);
	}
	
	public Course findById( int id) {
		return courseRepository.findById(id).get();
	}
	
	public void delete(int id) {
		courseRepository.deleteById(id);
	}

}
