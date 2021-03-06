/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.springmvcpracticecrudrestfulapi;

import com.mycompany.constraintgroups.StudentDetails;
import com.mycompany.exceptions.CustomException;
import com.mycompany.model.Student;
import java.sql.SQLException;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Thammana Srinivas
 */
@RestController  // equalent to @Controller + @ResponseBody
public class StudentCRUDController {
    
    @Autowired
    private StudentDAO studentDAO;
    
    @PostMapping("/student/{id}")
    public Student insert(@PathVariable int id, @Validated(StudentDetails.class) @RequestBody Student student) throws CustomException, SQLException{
        return studentDAO.addStudent(id,student);
    }
    
    @GetMapping("/student/{id}")
    public Student read(@PathVariable int id) throws CustomException {
        if(id<=0) throw new CustomException("Error Message: Student Id must be >0.");
        Student student = studentDAO.getStudent(id);
        return student;
    }
    
    @GetMapping(value={"/student","/students"})  // string array String[]
    public List<Student> selectAll() throws SQLException {
        return studentDAO.getAllStudents();
    }
    
    @DeleteMapping("/student/{id}")
    public Student remove(@PathVariable int id) throws CustomException, SQLException {
        if(id<=0) throw new CustomException("HttpStatus: "+HttpStatus.BAD_REQUEST+", Error Message: Student Id must be >0.");
        return studentDAO.deleteStudent(id);
    }
    
    @PutMapping("/student/{id}")
    public Student update(@Validated(StudentDetails.class) @RequestBody Student student, @PathVariable int id) throws CustomException, SQLException {
        return studentDAO.updateStudent(student);
    }
    
    @GetMapping("/studentdetails")
    public List<Student> getStudentDetails() throws SQLException {
        return studentDAO.getStudentDetails();
    }
}

/*

Table1: student
id     int          primary key
name   varchar(20)  not null
age    int          not null
course int          not null

Table2: task
id    int          primary key    auto_increment
name  varchar(40)  not null       unique{because user is entering the task name}

Table3: studenttaskmapper
sId  int     foreign key    reference student(id)  on delete cascade -> studentIdForeignKeyConstraint
tId  int     foreign key    reference task(id)     on delete cascade -> taskIdForeignKeyCosntraint 
primary key (studentId, taskId)

*/


// error handling 
// response code - success(2-series), failure-error.
// @valid, interceptor , json property
// concepts depth
// logger
// ResponseEntity
// obect handler
// exception in aftercompletion
// method arguments
// without coming to controller valid - directly to exceptionhandler
// github how are they calling api's in different projects - self evaluate.
// Multiple requestMapping for handler
// MySql
// jdbc


// normalization
// login