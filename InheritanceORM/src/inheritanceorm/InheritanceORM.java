/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inheritanceorm;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import inheritanceorm.AbstractEmployee;
import java.util.Scanner;

/**
 *
 * @author bossa
 */
public class InheritanceORM {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Scanner sc = new Scanner(System.in);
        int tmp = 0;

        while(tmp != -1){
            System.out.println("FulltimeEmployee = 1");
            System.out.println("ParttimeEmployee = 2");
            System.out.println("delete id = 3");
            System.out.println("update id = 4");
            tmp = sc.nextInt();
            if(tmp == 1){
                FulltimeEmployee emp = new FulltimeEmployee();
                System.out.print("name : ");
                String name = sc.next();
                emp.setName(name);
                
                System.out.println("Salary : ");
                int salary = sc.nextInt();
                emp.setSalary(salary);
                persist(emp);
                System.out.println(emp.toString());
            }
            if(tmp == 2){
                ParttimeEmployee emp = new ParttimeEmployee();
                System.out.print("name : ");
                String name = sc.next();
                emp.setName(name);
                
                System.out.println("HoursWork : ");
                int hoursWork = sc.nextInt();
                emp.setHoursWork(hoursWork);
                persist(emp);
            }
            if(tmp == 3){
                System.out.println("delete id : ");
                long id = sc.nextLong();
                
                removeEmployee(id);
            }
            if(tmp == 4){
                System.out.println("update id : ");
                long id = sc.nextLong();
                AbstractEmployee emp = findEmployeeById(id);
                String name = sc.next();
                emp.setName(name);
                
                updateEmployee(emp);
            }
        }
    }

    public static void persist(Object object) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("InheritanceORMPU");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            em.persist(object);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }
    
    public static void updateEmployee(AbstractEmployee emp) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("InheritanceORMPU");
        EntityManager em = emf.createEntityManager();
        AbstractEmployee fromDb = em.find(AbstractEmployee.class, emp.getId());
        fromDb.setName(emp.getName());
        
        em.getTransaction().begin();
        try {
            em.persist(fromDb);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }
    
    public static void removeEmployee(long id) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("InheritanceORMPU");
        EntityManager em = emf.createEntityManager();
        AbstractEmployee fromDb = em.find(AbstractEmployee.class, id);
        em.getTransaction().begin();
        try {
            em.remove(fromDb);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }
    
    public static AbstractEmployee findEmployeeById(long id) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("InheritanceORMPU");
        EntityManager em = emf.createEntityManager();
        AbstractEmployee emp = em.find(AbstractEmployee.class, id);
        em.close();
        return emp;
    }
}
