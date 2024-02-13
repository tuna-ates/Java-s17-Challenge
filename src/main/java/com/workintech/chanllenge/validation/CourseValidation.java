package com.workintech.chanllenge.validation;

import com.workintech.chanllenge.entity.Course;
import com.workintech.chanllenge.exceptions.CourseException;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

public class CourseValidation {
    public static  void  checkName(String name){
        if (name==null||name.isEmpty()){
            throw new CourseException("name can not be null or empty!!", HttpStatus.BAD_REQUEST);
        }
    }

    public static void checkCredit(Integer credit) {
        if(credit==null||credit<0||credit>4){
            throw new CourseException("Credit is null or not between 0-4",HttpStatus.BAD_REQUEST);
        }
    }


    public static void checkNameExist(List<Course> coursesList, String name) {
       Optional<Course> courseOptional= coursesList.stream().filter(c -> c.getName().equalsIgnoreCase(name))
                .findAny();

       if (courseOptional.isPresent()){
           throw new CourseException("course already exist  with name: "+name,HttpStatus.BAD_REQUEST);
       }
    }

    public static void checkId(Integer id) {
        if (id==null||id<0){
            throw new CourseException("Id can not less than 0 or null",HttpStatus.BAD_REQUEST);
        }
    }
}
