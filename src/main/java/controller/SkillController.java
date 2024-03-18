package controller;

import model.Skill;
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

public class SkillController {
    private EntityManagerFactory entityManagerFactory;
    private final EntityManager entityManager;
    private final Session session;

    public SkillController(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
        this.entityManager = entityManagerFactory.createEntityManager();
        this.session = this.entityManager.unwrap(Session.class);
    }
    public List<Skill> readSkillFile() throws IOException {
        String nombre, nombreS, charge, duration;
        int cost, initial;
        List<Skill> SkillList = new ArrayList<Skill>();

        BufferedReader br = new BufferedReader(new FileReader("src/main/resources/skill.txt"));
        String linea = "";
        while ((linea = br.readLine()) != null) {
            StringTokenizer str = new StringTokenizer(linea, ",");
            nombre = str.nextToken();
            nombreS = str.nextToken();
            charge = str.nextToken();
            duration = str.nextToken();
            cost = Integer.parseInt(str.nextToken());
            initial = Integer.parseInt(str.nextToken());
            SkillList.add(new Skill(nombre, nombreS, charge, duration, cost, initial));
        }
        br.close();

        return SkillList;
    }
    public void listAllSkill() {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        List<Skill> result = em.createQuery("from Skill", Skill.class)
                .getResultList();
        for (Skill Skill : result) {
            System.out.println(Skill.toString());
        }
        em.getTransaction().commit();
        em.close();
    }
    public void addAllSkill() throws IOException {
        for (Skill Skill : readSkillFile()) {
            addSkill(Skill);
        }
    }
    public void addSkill(Skill Skill) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.merge(Skill);
        em.getTransaction().commit();
        em.close();
    }
    public void buscarSkill(String columna, String valor) {
        NativeQuery<Skill> query = session.createNativeQuery("SELECT * FROM Skill WHERE " + columna + " = :valor", Skill.class);
        query.setParameter("valor", Integer.parseInt(valor));
        List<Skill> tableNames = query.getResultList();
        for (Skill Skill : tableNames) {
            System.out.println(Skill.toString());
        }
    }
    public void deletedAllSkill() {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            String sql = "TRUNCATE TABLE Skill";
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

    public void deletedSkill(String number) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            String sql = "DELETE FROM Skill WHERE class_id = :id";
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