/**
 * 
 */
package com.wibmo.constants;

/**
 * @author Himank
 * 
 */
/**
 * User queries
 * @author Bhuvan
 */

public class SQLQueriesConstant {
		// user queries
		public static final String GET_USER="SELECT * FROM user WHERE userId=?,password=?";
		public static final String UPDATE_USER="UPDATE user SET password=? WHERE userId=?";
		public static final String GET_ROLE="SELECT role FROM user WHERE userId=?";
		
		//professor queries
		public static final String GET_COURSES="select * from course where professorId=?";
		public static final String GET_ENROLLED_STUDENTS = "select * from student inner join registeredcourse on student.studentId=registeredcourse.studentId where registeredcourse.courseid=?";
		public static final String ADD_GRADE="update registeredcourse set grade=? where courseId=? and studentId=?";

		//student queries
		public static final String GET_COURSE_CATALOUGE = "select * from course";
		public static final String GET_PROFESSORS = "select * from professor";
		public static final String GET_PENDING_ADMINSSIONED_STUDENTS = "select * from student WHERE student.feePaid=false";
		public static final String GET_GRADES = "select credits,grade from registeredcourse inner join course on course.courseId=registeredcourse.courseId where studentId=?";
		public static final String ADD_PROFESSOR = "insert into professor values(?,?)";
		public static final String ADD_COURSE = "insert into course(`courseId`,`credits`,`courseName`) values(?,?,?,?)";
		public static final String REMOVE_COURSE = "DELETE  FROM course WHERE course.courseId=?";
		public static final String assignCourse = "UPDATE course SET course.professorId=? WHERE course.courseId=?";
		public static final String UNENROLL_COURSE = "DELETE FROM RegisteredCourse WHERE courseId=?";
		public static final String INVALID_COURSES_IDS = "SELECT courseId from course where studentsEnrolled<3";

		

}
