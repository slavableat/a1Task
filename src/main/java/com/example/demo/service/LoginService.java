package com.example.demo.service;

import com.example.demo.dto.LoginDTO;
import com.example.demo.model.*;
import com.example.demo.repository.*;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.demo.service.PostingsService.LOGINS_FILE_PATH;

@Service
public class LoginService {
    private final LoginRepository loginRepository;
    private final ApplicationRepository applicationRepository;
    private final AccountRepository accountRepository;
    private final JobRepository jobRepository;
    private final DepartmentRepository departmentRepository;


    @Autowired
    public LoginService(LoginRepository loginRepository, ApplicationRepository applicationRepository, AccountRepository accountRepository, JobRepository jobRepository, DepartmentRepository departmentRepository) {
        this.loginRepository = loginRepository;
        this.applicationRepository = applicationRepository;
        this.accountRepository = accountRepository;
        this.jobRepository = jobRepository;
        this.departmentRepository = departmentRepository;
    }

    public void initTable() {
        List<LoginDTO> loginDTOS = getLoginDTOs();
        loginDTOS.forEach(this::saveLoginIntoDataBase);
    }

    public List<Login> getAll() {
        return loginRepository.findAll();
    }

    private void saveLoginIntoDataBase(LoginDTO loginDTO) {
        Login login = new Login();
        Optional<Application> application = applicationRepository.findByTitle(loginDTO.getApplicationTitle());
        if (application.isPresent()) {
            login.setApplication(application.get());
        } else login.setApplication(new Application(null, loginDTO.getApplicationTitle()));
        Optional<Account> account = accountRepository.findByUsername(loginDTO.getAccountName());
        if (account.isPresent()) {
            login.setAccount(account.get());
        } else login.setAccount(new Account(null, loginDTO.getAccountName()));
        Optional<Job> job = jobRepository.findByTitle(loginDTO.getJobTitle());
        if (job.isPresent()) {
            login.setJob(job.get());
        } else login.setJob(new Job(null, loginDTO.getJobTitle()));
        Optional<Department> department = departmentRepository.findByTitle(loginDTO.getDepartmentTitle());
        if (department.isPresent()) {
            login.setDepartment(department.get());
        } else login.setDepartment(new Department(null, loginDTO.getDepartmentTitle()));
        login.setIsActive(loginDTO.getIsActive());
        loginRepository.save(login);
    }

    private static List<LoginDTO> getLoginDTOs() {
        List<LoginDTO> loginDTOS = new ArrayList<>();
        try (CSVReader fileReader = new CSVReader(new FileReader(LOGINS_FILE_PATH), '\t', CSVWriter.NO_QUOTE_CHARACTER, ',')) {
            fileReader.readNext();
            String[] line;

            while ((line = fileReader.readNext()) != null) {
                loginDTOS.add(new LoginDTO(line[0], line[1], Boolean.parseBoolean(line[2]), line[3], line[4]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return loginDTOS;
    }
}
