package com.workintech.chanllenge.entity;

import org.springframework.stereotype.Component;

@Component
public class MediumCourseGpa implements CourseGpa{
    @Override
    public int getGpa() {
        return 5;
    }
}
