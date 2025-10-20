package ru.morozov.testsecurity2db.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.morozov.testsecurity2db.entity.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
}