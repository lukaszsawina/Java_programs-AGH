package com.example.springapp;

import com.example.springapp.DTO.EmployeeDto;
import com.example.springapp.DTO.GroupDto;
import com.example.springapp.DTO.NewEmployeeDto;
import com.example.springapp.DTO.NewRateDto;
import com.opencsv.CSVWriter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.StringWriter;
import java.util.List;
import java.util.Map;

@RestController
public class Controller {

    private ClassContainer grupy = new ClassContainer();

    @PostMapping("/api/group")
    public ResponseEntity<String> NowaGrupa(@RequestBody GroupDto nowaGrupa)
    {
        if(grupy.getKontenery().get(nowaGrupa.getNazwa()) != null)
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Grupa już istnieje");

        if(nowaGrupa.getPojemnosc() < 1)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Pojemność zbyt mała");

        grupy.addClass(nowaGrupa.getNazwa(), nowaGrupa.getPojemnosc());

        return ResponseEntity.ok("Utworzono nową grupę " + nowaGrupa.getNazwa());
    }

    @GetMapping("/api/groups")
    public ResponseEntity<Map<String, EmployeeClass>> WszystkieGrupy()
    {
        return ResponseEntity.ok(grupy.getKontenery());
    }

    @DeleteMapping("/api/group")
    public ResponseEntity<String> UsunGrupe(@RequestParam String nazwaGrupy)
    {
        if(grupy.getKontenery().get(nazwaGrupy) == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Grupa nie istnieje");

        grupy.removeClass(nazwaGrupy);

        return  ResponseEntity.ok("Usunięto grupę!");

    }

    @GetMapping("/api/group/{nazwaGrupy}/employee")
    public ResponseEntity<List<Employee>> WyswietlPracownikowGrupy(@PathVariable String nazwaGrupy)
    {
        if(grupy.getKontenery().get(nazwaGrupy) == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

        return ResponseEntity.ok(grupy.getKontenery().get(nazwaGrupy).employeeList);
    }

    @GetMapping("/api/group/{nazwaGrupy}/fill")
    public ResponseEntity<String> WyswietlWypelnienie(@PathVariable String nazwaGrupy)
    {
        if(grupy.getKontenery().get(nazwaGrupy) == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Grupa nie istnieje");

        double zapelnienie = (double)grupy.getKontenery().get(nazwaGrupy).employeeList.size()/(double)grupy.getKontenery().get(nazwaGrupy).getSize()*100.0;

        return ResponseEntity.ok(Double.toString(zapelnienie));
    }

    @PostMapping("/api/employee")
    public ResponseEntity<String> DodajPracownika(@RequestBody NewEmployeeDto nowyPracownik)
    {
        try{
            if(grupy.getKontenery().get(nowyPracownik.getGroupName()) == null)
                ResponseEntity.status(HttpStatus.NOT_FOUND).body("Grupa nie istnieje");

            Employee pracownik = new Employee(
                    grupy.getKontenery().get(nowyPracownik.getGroupName()).employeeList.size(),
                    nowyPracownik.getEmployee().getName(),
                    nowyPracownik.getEmployee().getSurname(),
                    nowyPracownik.getEmployee().getSalary(),
                    nowyPracownik.getEmployee().getYearOfBirth(),
                    nowyPracownik.getEmployee().getEmployeeStatus()
                    );

            grupy.addEmployeeToClass(nowyPracownik.getGroupName(), pracownik);

            return ResponseEntity.ok("Dodano pracownika");
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Grupa nie istnieje");
        }
    }

    @PostMapping("/api/rating")
    public ResponseEntity<String> DodajOcene(@RequestBody NewRateDto nowaOcena)
    {
        if(grupy.getKontenery().get(nowaOcena.getGroupName()) == null)
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("Grupa nie istnieje");

        if(nowaOcena.getRate().getStarNumber() < 0 || nowaOcena.getRate().getStarNumber() > 10 )
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Zła ocena");

        Rate ocena = new Rate(nowaOcena.getRate().getStarNumber(),nowaOcena.getRate().getReviewDate(), nowaOcena.getRate().getComment());
        grupy.getKontenery().get(nowaOcena.getGroupName()).getReviewList().add(ocena);

        return ResponseEntity.ok("Dodano ocene");
    }

    @DeleteMapping("/api/group/{nazwaGrupy}/employee/{id}")
    public ResponseEntity<String> UsunGrupe(@PathVariable String nazwaGrupy, @PathVariable int id)
    {
        if(grupy.getKontenery().get(nazwaGrupy) == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Grupa nie istnieje");

        grupy.getKontenery().get(nazwaGrupy).employeeList.remove(id);

        return  ResponseEntity.ok("Usunięto osobe!");
    }

    @GetMapping("/api/group/{nazwaGrupy}/employee/csv")
    public ResponseEntity<String> exportToCsv(@PathVariable String nazwaGrupy) {

        if(grupy.getKontenery().get(nazwaGrupy) == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Grupa nie istnieje");

        String csvData = convertToCsv(grupy.getKontenery().get(nazwaGrupy).employeeList);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=employees.csv");

        return ResponseEntity.ok()
                .headers(headers)
                .body(csvData);
    }

    private String convertToCsv(List<Employee> employees) {
        try (StringWriter writer = new StringWriter();
             CSVWriter csvWriter = new CSVWriter(writer, CSVWriter.DEFAULT_SEPARATOR,
                     CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END)) {

            // Nagłówki CSV
            String[] header = {"Name", "Position", "Salary"};
            csvWriter.writeNext(header);

            // Dane pracowników
            for (Employee employee : employees) {
                String[] data = {employee.getName(), employee.getSurname(), String.valueOf(employee.getYearOfBirth()), String.valueOf(employee.getSalary())};
                csvWriter.writeNext(data);
            }

            return writer.toString();
        } catch (Exception e) {
            // Obsłuż wyjątek
            e.printStackTrace();
            return "";
        }
    }

}
