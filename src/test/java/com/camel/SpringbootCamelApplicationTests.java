package com.camel;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.MockitoAnnotations;

import org.springframework.boot.test.context.SpringBootTest;

import com.camel.entity.Course;
import com.camel.repository.CourseRepository;

import com.camel.service.CourseService;

@SpringBootTest
class SpringbootCamelApplicationTests {

	@Test
	void contextLoads() {
	}

	@Mock
	private CourseRepository courseRepository;

	@InjectMocks
	private CourseService courseService;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testFindAllCourses() {
		// Create sample data
		List<Course> courses = new ArrayList<>();
		courses.add(new Course(1, "Math", "New York"));
		courses.add(new Course(2, "Science", "San Francisco"));

		// Mock behavior of courseRepository.findAll()
		when(courseRepository.findAll()).thenReturn(courses);

		// Test the service method
		List<Course> result = courseService.all();

		// Assertions
		assertEquals(2, result.size()); // Assuming two courses are returned
		// Add more specific assertions if needed
	}

	@Test
	public void testSaveCourse() {
		// Create a sample course
		Course courseToSave = new Course(3, "Java", "React js");

		// Mock behavior of courseRepository.save()
		when(courseRepository.save(any(Course.class))).thenReturn(courseToSave);

		// Test the service method
		Course savedCourse = courseService.save(courseToSave);

		// Assertions
//        assertNotNull(savedCourse); // Ensure the saved course is not null
		assertEquals("Java", savedCourse.getName()); // Validate specific attributes
		// Add more assertions as needed
	}

	@Test
	public void testFindCourseById() {
		// Create a sample course
		Course sampleCourse = new Course(4, "Java", "Paris");

		// Mock behavior of courseRepository.findById()
		when(courseRepository.findById(4)).thenReturn(Optional.of(sampleCourse));

		// Test the service method
		Course foundCourse = courseService.findById(4);

		// Assertions
//        assertNotNull(foundCourse); // Ensure a course is found
		assertEquals("Paris", foundCourse.getDescription()); // Validate specific attributes
		// Add more assertions as needed
	}

	@Test
	public void testDeleteCourse() {
		// Define the ID of the course to delete
		int courseIdToDelete = 5;

		// Mock behavior of courseRepository.deleteById()
		doNothing().when(courseRepository).deleteById(courseIdToDelete);

		// Test the service method
		assertDoesNotThrow(() -> courseService.delete(courseIdToDelete));

		// If the method completes without throwing an exception, it's considered
		// successful
		// You might also want to verify if the deleteById method was called
		verify(courseRepository, times(1)).deleteById(courseIdToDelete);
	}

}
