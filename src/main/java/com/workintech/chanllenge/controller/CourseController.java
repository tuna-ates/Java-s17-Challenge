package com.workintech.chanllenge.controller;

import com.workintech.chanllenge.entity.Course;
import com.workintech.chanllenge.entity.CourseGpa;
import com.workintech.chanllenge.entity.CourseResponse;
import com.workintech.chanllenge.exceptions.CourseException;
import com.workintech.chanllenge.validation.CourseValidation;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private List<Course> coursesList;
    private final CourseGpa lowCourseGpa;
    private final CourseGpa mediumCourseGpa;
    private final CourseGpa highCourseGpa;

    @Autowired
    public CourseController(@Qualifier("lowCourseGpa") CourseGpa lowCourseGpa,
                            @Qualifier("mediumCourseGpa") CourseGpa mediumCourseGpa,
                            @Qualifier("highCourseGpa") CourseGpa highCourseGpa) {
        this.lowCourseGpa = lowCourseGpa;
        this.mediumCourseGpa = mediumCourseGpa;
        this.highCourseGpa = highCourseGpa;
    }


    @PostConstruct
    public void createListCourse(){
        coursesList=new ArrayList<>();
    }

    @GetMapping("/")
    public List<Course> allCourses(){
        return coursesList;
    }
    @GetMapping("/{name}")
    public Course nameWithCourse(@PathVariable String name){
        CourseValidation.checkName(name);
        return coursesList.stream().filter(course -> course.getName().equalsIgnoreCase(name)).findAny()
                .orElseThrow(()->new CourseException("course not found with name: "+name,HttpStatus.NOT_FOUND));


    }
    @PostMapping("/")
    public ResponseEntity<CourseResponse> createCourse(@RequestBody Course course){
        CourseValidation.checkName(course.getName());
        CourseValidation.checkCredit(course.getCredit());
        CourseValidation.checkNameExist(coursesList,course.getName());
        coursesList.add(course);
        Integer getTotal =getTotal(course);
      CourseResponse courseResponse=new CourseResponse(course,getTotal);
        return new  ResponseEntity<>(courseResponse,HttpStatus.CREATED);
    }

    public Integer getTotal(Course course){
        if (course.getCredit()<=2){
            return course.getGrade().getCoefficient()*course.getCredit()* lowCourseGpa.getGpa();
        } else if (course.getCredit()==3) {
            return course.getGrade().getCoefficient()*course.getCredit()* mediumCourseGpa.getGpa();

        } else if (course.getCredit()==4) {
            return course.getGrade().getCoefficient()*course.getCredit()* highCourseGpa.getGpa();

        }
        return null;
    }
    @PutMapping("/{id}")
    public ResponseEntity<CourseResponse> updateCourse(@PathVariable int id,@RequestBody Course course){
      CourseValidation.checkId(id);
      CourseValidation.checkCredit(course.getCredit());
      CourseValidation.checkName(course.getName());
       Course existCourse= coursesList.stream().filter(course1 -> course1.getName()
               .equalsIgnoreCase(course.getName()))
               .findAny().orElseThrow(()->new CourseException("record not found with id :"+id,HttpStatus.NOT_FOUND));
       int indexOfExistCourse=coursesList.indexOf(existCourse);
       course.setId(indexOfExistCourse);
       CourseValidation.checkNameExist(coursesList,course.getName());
       coursesList.set(indexOfExistCourse,course);
       Integer totalGpa=getTotal(course);
       CourseResponse courseResponse=new CourseResponse(course,totalGpa);


       return new ResponseEntity<>(courseResponse,HttpStatus.OK);
    }



    @DeleteMapping("/{id}")
    public Course deleteCourse(@PathVariable int id){
        CourseValidation.checkId(id);
        Course course=coursesList.stream()
                .filter(course1 -> course1.getId().equals(id))
                .findFirst()
                .orElseThrow(()->new CourseException("record not found with id: "+id,HttpStatus.NOT_FOUND));
        coursesList.remove(course);
   return  course;
    }

}
