package com.wibmo.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wibmo.constants.GenderConstant;
import com.wibmo.constants.RoleConstant;
import com.wibmo.entity.Student;
import com.wibmo.exception.StudentNotRegisteredException;
import com.wibmo.repository.CourseRepository;
import com.wibmo.repository.ProfessorCourseRequestRepository;
import com.wibmo.repository.ProfessorRepository;
import com.wibmo.repository.RegisteredCourseRepository;
import com.wibmo.repository.StudentDAOImpl;
import com.wibmo.repository.StudentDAOInterface;
import com.wibmo.repository.StudentRepository;

import net.bytebuddy.dynamic.DynamicType.Builder.FieldDefinition.Optional;

/**
 * 
 * @author Himank
 * Implementations of Student Operations
 *
 */

@Service
public class StudentServiceImpl implements StudentServiceInterface {
	private static final Logger logger = LogManager.getLogger(StudentServiceImpl.class);
	
	@Autowired
	StudentDAOInterface studentDaoInterface;

	@Autowired
	private CourseRepository courseRepository;
	
	@Autowired
	private ProfessorRepository professorRepository;
	
	@Autowired
	private RegisteredCourseRepository registeredCourseRepository;
	
	@Autowired
	private ProfessorCourseRequestRepository professorCourseRequestRepository;
	
	@Autowired
	StudentRepository studentRepository;

	/**
	 * Method to register a student, although student can't login until it's approved by admin
	 * @param name
	 * @param userID
	 * @param password
	 * @param gender
	 * @param batch
	 * @param branch
	 * @param address
	 * @param country
	 * @return Student ID
	 * @throws StudentNotRegisteredException
	 */
	public String register(String name,String userId,String password,GenderConstant gender,int batch,String branch,String address) throws StudentNotRegisteredException{
		String studentId;
		Student newStudent=new Student();
		newStudent.setName(name);
		newStudent.setUserId(userId);
		newStudent.setStudentId(userId);
		newStudent.setPassword(password);
		newStudent.setGender(gender);
		newStudent.setGradYear(batch);
		newStudent.setDepartment(branch);
		newStudent.setAddress(address);
		newStudent.setApproved(false);
		newStudent.setRole(RoleConstant.STUDENT);
		studentId = studentRepository.save(newStudent).getStudentId();
		logger.info("\nYour account has been created and pending for Approval by Admin.\n");
		return studentId;
	}

    /**
     * Method to get Student ID from User ID
     * @param userId
     * @return Student ID
     */
    @Override
    public String getStudentId(String userId) {

		return studentRepository.findStudentByUserId(userId).getStudentId();
    }



	
	/**
     * Method to check if student is approved by Admin or not
     * @param studentId
     * @return boolean indicating if student is approved
     */
	@Override
	public boolean isApproved(String studentId) {
		java.util.Optional<Student> st = studentRepository.findById(studentId);
		return st.get().isApproved();
	}



}