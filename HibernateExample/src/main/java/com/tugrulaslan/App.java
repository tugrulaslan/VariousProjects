package com.tugrulaslan;

import com.tugrulaslan.dao.EmployeeDAO;
import com.tugrulaslan.entity.Employee;

import java.util.Iterator;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {

        EmployeeDAO dao = new EmployeeDAO();

        //Inserting new user
        Employee employee = new Employee();
        employee.setName("Tugrul");
        employee.setLastname("ASLAN");
        employee.setRegDate(new java.sql.Timestamp(System.currentTimeMillis()));
        //dao.insertEmployee(employee);

        Employee employee1 = new Employee();
        employee1.setName("Hakan");
        employee1.setLastname("PEKCAN");
        employee1.setRegDate(new java.sql.Timestamp(System.currentTimeMillis()));
        //dao.insertEmployee(employee1);

        Employee employee2 = new Employee();
        employee2.setName("Mursel");
        employee2.setLastname("EKINCI");
        employee2.setRegDate(new java.sql.Timestamp(System.currentTimeMillis()));
        //dao.insertEmployee(employee2);


        //updating one of newly added users
        Employee updateEmployee = new Employee();
        updateEmployee.setName("Mehmet");
        updateEmployee.setLastname("BARAK");
        updateEmployee.setRegDate(new java.sql.Timestamp(System.currentTimeMillis()));
      //  dao.updateEmployee(updateEmployee, 23);

        //deleting an employee by id
        //dao.deleteEmployee(21);

        //listing employees

        List<Employee> employeeList = dao.listEmployees();
        Iterator<Employee> iterator = employeeList.iterator();
        while (iterator.hasNext()){
            Employee currentEmployee = (iterator.next());
            System.out.println("ID:" + currentEmployee.getId() + "\n"
                             + "Name: " + currentEmployee.getName() + "\n"
                             + "Lastname: " + currentEmployee.getLastname() + "\n"
                             + "Registration Date: " + currentEmployee.getRegDate());
        }
    }
}
