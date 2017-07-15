package com.tugrulaslan.dao;

import com.tugrulaslan.entity.Employee;
import com.tugrulaslan.util.HibernateUtil;
import org.hibernate.*;

import java.util.List;

/**
 * Created by 90003327 on 7/10/2014.
 */
public class EmployeeDAO {

    public void insertEmployee(Employee employee) {
        Session session = HibernateUtil.getSessionAnnotationFactory().openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.save(employee);
            transaction.commit();
        } catch (HibernateException e) {
            System.out.println(e);
            transaction.rollback();
        } finally {
            HibernateUtil.getSessionAnnotationFactory().close();
        }
    }

    public void updateEmployee(Employee employee, int employeeId) {
        Session session = HibernateUtil.getSessionAnnotationFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Query query = null;
        try {
            query = session.createQuery("from Employee where id = :empId");
            query.setParameter("empId", employeeId);

            session.update(employee);
            transaction.commit();
        } catch (HibernateException e) {
            System.out.println(e);
            transaction.rollback();
        } finally {
            HibernateUtil.getSessionAnnotationFactory().close();
        }

    }

    public void deleteEmployee(int employeeId) {
        Session session = HibernateUtil.getSessionAnnotationFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Query query = null;
        try {
            query = session.createQuery("delete from Employee where id = :empId");
            query.setInteger("empId", employeeId);
            query.executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            System.out.println(e);
            transaction.rollback();
        } finally {
            HibernateUtil.getSessionAnnotationFactory().close();
        }
    }

    public List<Employee> listEmployees() {
        Session session = HibernateUtil.getSessionAnnotationFactory().openSession();
        Transaction transaction = session.beginTransaction();
        List<Employee> employeeList = null;
        try {
            employeeList = session.createQuery("from Employee ").list();
            transaction.commit();
        } catch (HibernateException e) {
            System.out.println(e);
            transaction.rollback();
        } finally {
            HibernateUtil.getSessionAnnotationFactory().close();
        }
        return employeeList;
    }

}
