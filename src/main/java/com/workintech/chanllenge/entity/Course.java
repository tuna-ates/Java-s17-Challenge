package com.workintech.chanllenge.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.PrimitiveIterator;
@AllArgsConstructor
@Data
public class Course {
    private  Integer id;
    private String name;
    private int credit;
    private Grade grade;
}
