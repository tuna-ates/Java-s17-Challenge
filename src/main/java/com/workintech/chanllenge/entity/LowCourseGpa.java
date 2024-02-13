package com.workintech.chanllenge.entity;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class LowCourseGpa implements CourseGpa{
    @Override
    public int getGpa() {
        return 3;
    }
}
