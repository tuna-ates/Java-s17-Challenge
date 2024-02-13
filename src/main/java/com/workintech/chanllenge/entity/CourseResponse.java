package com.workintech.chanllenge.entity;

import lombok.AllArgsConstructor;
import lombok.Data;


@AllArgsConstructor
@Data
public class CourseResponse {
    private Course course;
    private Integer totalGpa;
}
