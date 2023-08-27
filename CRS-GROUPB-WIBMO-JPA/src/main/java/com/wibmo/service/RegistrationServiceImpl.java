package com.wibmo.service;

import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wibmo.constants.PaymentModeConstant;
import com.wibmo.entity.Course;
import com.wibmo.entity.Grade;
import com.wibmo.entity.GradeCard;
import com.wibmo.entity.Notification;
import com.wibmo.exception.CourseLimitExceededException;
import com.wibmo.exception.CourseNotFoundException;
import com.wibmo.exception.SeatNotAvailableException;
import com.wibmo.repository.RegistrationDAOImpl;
import com.wibmo.repository.RegistrationDAOInterface;
import com.wibmo.validator.StudentValidator;

/**
 * @author bhuvan
 * The Registration Operation provides the business logic for student registration.
 * 
 */

@Service
public class RegistrationServiceImpl implements RegistrationServiceInterface {
	private static final Logger logger = LogManager.getLogger(RegistrationServiceImpl.class);
	
	@Autowired
	RegistrationDAOInterface registrationDAOInterface;

	/**
	 * Method to add Course selected by student 
	 * @param courseCode
	 * @param studentId
	 * @param courseList 
	 * @return boolean indicating if the course is added successfully
	 * @throws CourseNotFoundException
	 * @throws SeatNotAvailableException 
	 * @throws CourseLimitExceedException 
	 * @throws SQLException 
	 */
	@Override
	
	public boolean addCourse(String courseCode, String studentId) throws CourseNotFoundException, CourseLimitExceededException, SeatNotAvailableException, SQLException 
	{
       
		
		List<Course> availableCourseList=viewCourses(studentId);
		if (registrationDAOInterface.numOfRegisteredCourses(studentId) >= 6)
		{	
			logger.info("You have already registered for 6 courses");
		}
		else if (registrationDAOInterface.isRegistered(courseCode, studentId)) 
		{
			return false;
		} 
		else if (!registrationDAOInterface.seatAvailable(courseCode)) 
		{
			throw new SeatNotAvailableException(courseCode);
		} 
		else if(!StudentValidator.isValidCourseCode(courseCode, availableCourseList))
		{
			throw new CourseNotFoundException(courseCode);
		}
		
		  

		return registrationDAOInterface.addCourse(courseCode, studentId);

	}

	/**
	 *  Method to drop Course selected by student
	 * @param courseCode
	 * @param studentId
	 * @param registeredCourseList 
	 * @return boolean indicating if the course is dropped successfully
	 * @throws CourseNotFoundException
	 * @throws SQLException 
	 */
	@Override
	
	public boolean dropCourse(String courseCode, String studentId,List<Course> registeredCourseList) throws CourseNotFoundException, SQLException {
		  if(!StudentValidator.isRegistered(courseCode, studentId, registeredCourseList))
	        {
	        	throw new CourseNotFoundException(courseCode);
	        }
		
		return registrationDAOInterface.dropCourse(courseCode, studentId);

	}

	/** Method for Fee Calculation for selected courses
	 * Fee calculation for selected courses
	 * @param studentId
	 * @return Fee Student has to pay
	 * @throws SQLException 
	 */
	@Override
	
	public double calculateFee(String studentId) throws SQLException {
		return registrationDAOInterface.calculateFee(studentId);
	}


	/**
	 * Method to view grade card for students
	 * @param studentId
	 * @return GradeCard
	 * @throws SQLException 
	 */
	@Override
	
	public GradeCard viewGradeCard(String studentId) throws SQLException {
		return registrationDAOInterface.viewGradeCard(studentId);
	}

	/**
	 *  Method to view the list of available courses
	 * @param studentId
	 * @return List of courses
	 * @throws SQLException 
	 */
	@Override
	
	public List<Course> viewCourses(String studentId) throws SQLException {
		return registrationDAOInterface.viewCourses(studentId);
	}

	/**
	 * Method to view the list of courses registered by the student
	 * @param studentId
	 * @return List of courses
	 * @throws SQLException 
	 */
	@Override
	
	public List<Course> viewRegisteredCourses(String studentId) throws SQLException {
		return registrationDAOInterface.viewRegisteredCourses(studentId);
	}
    
	/**
	 *  Method to check student registration status
	 * @param studentId
	 * @return boolean indicating if the student's registration status
	 * @throws SQLException
	 */
	@Override

	public boolean getRegistrationStatus(String studentId) throws SQLException {
		return registrationDAOInterface.getRegistrationStatus(studentId);
	}
	
	/**
	 * Method to set student registration status
	 * @param studentId
	 * @throws SQLException
	 */
	@Override
	
	public void setRegistrationStatus(String studentId) throws SQLException {
		registrationDAOInterface.setRegistrationStatus(studentId);

	}

	@Override
	public boolean isReportGenerated(String studentId) throws SQLException {
		
		return registrationDAOInterface.isReportGenerated(studentId);
	}

	@Override
	public boolean getPaymentStatus(String studentId) throws SQLException 
	{
		return registrationDAOInterface.getPaymentStatus(studentId);
		
	}

	@Override
	public void setPaymentStatus(String studentId) throws SQLException{
		registrationDAOInterface.setPaymentStatus(studentId);
		
	}

}