package com.mertalptekin.springbootrestapp.secondarydb.repository;

import com.mertalptekin.springbootrestapp.secondarydb.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICourseRepository extends JpaRepository<Course,Integer> {
}
