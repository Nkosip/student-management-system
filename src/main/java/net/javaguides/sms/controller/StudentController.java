package net.javaguides.sms.controller;

import jakarta.validation.Valid;
import net.javaguides.sms.dto.StudentDto;
import net.javaguides.sms.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("student")
public class StudentController {

    private StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/students")
    public String listStudents(Model model) {
        List<StudentDto> students = studentService.getAllStudents();
        model.addAttribute("students", students);
        return "students";
    }

    @GetMapping("/students/new")
    public String newStudent(Model model) {
        StudentDto studentDto = new StudentDto();
        model.addAttribute("student",studentDto);
        return "create_student";
    }

    @PostMapping("/students")
    public String saveStudent(
            @Valid @ModelAttribute("student") StudentDto student,
            BindingResult result,
            Model model
            ) {

        if (result.hasErrors()) {
            model.addAttribute("student",student);
            return "create_student";
        }

        studentService.createStudent(student);
        return "redirect:/student/students";
    }

    @GetMapping("/students/{studentId}/edit")
    public String editStudent(@PathVariable("studentId") Long id, Model model) {
        StudentDto student = studentService.getStudentById(id);
        model.addAttribute("student",student);
        return "edit_student";
    }

    @PostMapping("/students/{studentId}")
    public String updateStudent(
            @PathVariable("studentId") Long id,
            @Valid @ModelAttribute("student") StudentDto studentDto,
            BindingResult result,
            Model model
            ) {
        if (result.hasErrors()) {
            model.addAttribute("student",studentDto);
            return "edit_student";
        }

        studentDto.setId(id);
        studentService.updateStudent(studentDto);
        return "redirect:/student/students";
    }

    @GetMapping("/students/{studentId}/delete")
    public String deleteStudent(@PathVariable("studentId") Long id) {
        studentService.deleteStudent(id);
        return "redirect:/student/students";
    }

    @GetMapping("/students/{studentId}/view")
    public String viewStudent(@PathVariable("studentId") Long id, Model model) {
        StudentDto studentDto = studentService.getStudentById(id);
        model.addAttribute("student",studentDto);
        return "view_student";
    }
}
