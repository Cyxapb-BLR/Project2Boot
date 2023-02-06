package com.matskevich.Project2Boot.repositories;

import com.matskevich.springcourse.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {
}
