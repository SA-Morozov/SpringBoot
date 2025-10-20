package ru.morozov.testsecurity2db.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.morozov.testsecurity2db.entity.Student;
import ru.morozov.testsecurity2db.dao.StudentRepository;

import java.util.Optional;

@Controller
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @GetMapping("/list")
    public String listStudents(Model model) {
        model.addAttribute("students", studentRepository.findAll());
        return "list-students";
    }

    @GetMapping("/addStudentForm")
    public String addStudentForm(Model model) {
        Student student = new Student();
        model.addAttribute("student", student);
        return "add-student-form";
    }

    @PostMapping("/saveStudent")
    public String saveStudent(@ModelAttribute Student student) {
        studentRepository.save(student);
        return "redirect:/list";
    }

    @GetMapping("/showUpdateForm")
    public String showUpdateForm(@RequestParam Long studentId, Model model) {
        Optional<Student> optionalStudent = studentRepository.findById(studentId);
        Student student = new Student();
        if (optionalStudent.isPresent()) {
            student = optionalStudent.get();
        }
        model.addAttribute("student", student);
        return "add-student-form";
    }

    @GetMapping("/deleteStudent")
    public String deleteStudent(@RequestParam Long studentId) {
        studentRepository.deleteById(studentId);
        return "redirect:/list";
    }
}