package com.example.pracownicy;

import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.List;

public class DBManager {
    private static DBManager INSTANCE;
    private SessionFactory sessionFactory;

    private DBManager(){
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().build();
        try {
            sessionFactory = new MetadataSources(registry)
                    .addAnnotatedClass(Employee.class)
                    .addAnnotatedClass(EmployeeClass.class)
                    .addAnnotatedClass(Rate.class)
                    .buildMetadata().buildSessionFactory();
        }catch (Exception e){
            System.out.println(e.getMessage());
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    public static DBManager getInstance(){
        if(INSTANCE == null)
            INSTANCE = new DBManager();

        return INSTANCE;
    }

    public void saveGroup(EmployeeClass employeeClass){
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.persist(employeeClass);
            session.getTransaction().commit();
        }
    }

    public void deleteGroup(EmployeeClass employeeClass) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            CriteriaBuilder builder = session.getCriteriaBuilder();

            CriteriaDelete<Employee> criteriaDeleteEmployee = builder.createCriteriaDelete(Employee.class);
            Root<Employee> rootEmployee = criteriaDeleteEmployee.from(Employee.class);
            criteriaDeleteEmployee.where(builder.equal(rootEmployee.get("employeeGroup"), employeeClass));

            session.createQuery(criteriaDeleteEmployee).executeUpdate();

            CriteriaDelete<Rate> criteriaDeleteRate = builder.createCriteriaDelete(Rate.class);
            Root<Rate> rootRate = criteriaDeleteRate.from(Rate.class);
            criteriaDeleteRate.where(builder.equal(rootRate.get("employeeGroup"), employeeClass));

            session.createQuery(criteriaDeleteRate).executeUpdate();

            CriteriaDelete<EmployeeClass> criteriaDeleteEmployeeGroup = builder.createCriteriaDelete(EmployeeClass.class);
            Root<EmployeeClass> rootEmployeeGroup = criteriaDeleteEmployeeGroup.from(EmployeeClass.class);
            criteriaDeleteEmployeeGroup.where(builder.equal(rootEmployeeGroup, employeeClass));

            session.createQuery(criteriaDeleteEmployeeGroup).executeUpdate();

            session.getTransaction().commit();
        }
    }

    public List<EmployeeClass> getAllGroups() {
        List<EmployeeClass> employeeClasse;
        try(Session session = sessionFactory.openSession()){
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<EmployeeClass> criteriaQuery = cb.createQuery(EmployeeClass.class);
            Root<EmployeeClass> root = criteriaQuery.from(EmployeeClass.class);
            criteriaQuery.select(root);
            Query<EmployeeClass> query = session.createQuery(criteriaQuery);
            employeeClasse = query.getResultList();
        }
        return employeeClasse;
    }

    public void saveEmployee(Employee newEmployee) {
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.persist(newEmployee);
            session.getTransaction().commit();
        }
    }

    public void deleteEmployee(Employee employee) {
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.remove(employee);
            session.getTransaction().commit();
        }
    }

    public void modifyEmployee(Employee employee) {
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.merge(employee);
            session.getTransaction().commit();
        }
    }

    public List<Employee> getEmployeeListByGroup(EmployeeClass employeeClass) {
        List<Employee> employeeList;
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Employee> query = builder.createQuery(Employee.class);
            Root<Employee> root = query.from(Employee.class);

            query.select(root).where(builder.equal(root.get("employeeClass"), employeeClass));

            employeeList = session.createQuery(query).getResultList();
        }
        return employeeList;
    }

    public List<Rate> getRateListByGroup(EmployeeClass employeeClass) {
        List<Rate> rateList;
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Rate> query = builder.createQuery(Rate.class);
            Root<Rate> root = query.from(Rate.class);

            query.select(root).where(builder.equal(root.get("employeeClass"), employeeClass));

            rateList = session.createQuery(query).getResultList();
        }
        return rateList;
    }

    public void saveRate(Rate rate) {
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.persist(rate);
            session.getTransaction().commit();
        }
    }

    public List<Employee> getAllEmployees() {
        List<Employee> employees;
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Employee> criteriaQuery = cb.createQuery(Employee.class);
            Root<Employee> root = criteriaQuery.from(Employee.class);
            criteriaQuery.select(root);
            Query<Employee> query = session.createQuery(criteriaQuery);
            employees = query.getResultList();
        }
        return employees;
    }

    public List<Rate> getAllRates() {
        List<Rate> rates;
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Rate> criteriaQuery = cb.createQuery(Rate.class);
            Root<Rate> root = criteriaQuery.from(Rate.class);
            criteriaQuery.select(root);
            Query<Rate> query = session.createQuery(criteriaQuery);
            rates = query.getResultList();
        }
        return rates;
    }

    public void exportToCSV() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("./export/EmployeeClass.csv"))) {
            StatefulBeanToCsv<EmployeeClass> beanToCsvEmployeeGroup = new StatefulBeanToCsvBuilder<EmployeeClass>(writer)
                    .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                    .build();

            beanToCsvEmployeeGroup.write(DBManager.getInstance().getAllGroups());

            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("./export/Employee.csv"))) {
            StatefulBeanToCsv<Employee> beanToCsvEmployeeGroup = new StatefulBeanToCsvBuilder<Employee>(writer)
                    .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                    .build();
            beanToCsvEmployeeGroup.write(DBManager.getInstance().getAllEmployees());

            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("./export/Rate.csv"))) {
            StatefulBeanToCsv<Rate> beanToCsvEmployeeGroup = new StatefulBeanToCsvBuilder<Rate>(writer)
                    .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                    .build();

            beanToCsvEmployeeGroup.write(DBManager.getInstance().getAllRates());

            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
