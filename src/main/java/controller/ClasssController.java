package controller;

import model.Classs;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class ClasssController {
    private EntityManagerFactory entityManagerFactory;
    private final EntityManager entityManager;
    private final Session session;

    public ClasssController(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
        this.entityManager = entityManagerFactory.createEntityManager();
        this.session = this.entityManager.unwrap(Session.class);
    }
    public List<Classs> readClasssFile() throws IOException {
        String primary, secondary;
        int classId;
        List<Classs> classsList = new ArrayList<Classs>();

        BufferedReader br = new BufferedReader(new FileReader("src/main/resources/class.txt"));
        String linea = "";
        while ((linea = br.readLine()) != null) {
            StringTokenizer str = new StringTokenizer(linea, ",");
            classId = Integer.parseInt(str.nextToken());
            primary = str.nextToken();
            secondary = str.nextToken();
            classsList.add(new Classs(classId, primary, secondary));
        }
        br.close();

        return classsList;
    }
    public void listAllClasss() {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        List<Classs> result = em.createQuery("from Classs", Classs.class)
                .getResultList();
        for (Classs classs : result) {
            System.out.println(classs.toString());
        }
        em.getTransaction().commit();
        em.close();
    }
    public void addAllClasss() throws IOException {
        for (Classs classs : readClasssFile()) {
            addClasss(classs);
        }
    }
    public void addClasss(Classs classs) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.merge(classs);
        em.getTransaction().commit();
        em.close();
    }
    public Classs buscarIdClass(String number) {
            NativeQuery<Classs> query = session.createNativeQuery("SELECT * FROM classs WHERE class_id = :id", Classs.class);
            query.setParameter("id", Integer.parseInt(number));
        return (Classs) query.getResultList();
    }
    public void buscarC(String number) {
        NativeQuery<Classs> query = session.createNativeQuery("SELECT * FROM Operator WHERE class_Id = :id", Classs.class);
        query.setParameter("id", Integer.parseInt(number));
        List<Classs> tableNames = query.getResultList();
        for (Classs classs : tableNames) {
            System.out.println(classs.toString());
        }
    }
    public void deletedAllClasss() {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            String sql = "TRUNCATE TABLE classs CASCADE";
            NativeQuery query = session.createNativeQuery(sql);
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void deletedClasss(String number) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                String sql = "DELETE FROM classs WHERE class_id = :id";
                NativeQuery query = session.createNativeQuery(sql);
                query.setParameter("id", Integer.parseInt(number));
                query.executeUpdate();
                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                e.printStackTrace();
            }
    }
}