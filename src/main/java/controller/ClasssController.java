package controller;

import model.Classs;
import model.Operator;
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
    private EntityManager entityManager;
    private Session session;

    public ClasssController(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
        this.entityManager = entityManagerFactory.createEntityManager();
        this.session = this.entityManager.unwrap(Session.class);
    }

    /**
     * Metodo Para listar todos las class del documento class.txt
     * @return Una lista de Classs
     * @throws IOException Para que no pete
     */
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
    /**
     * Muestra todos las Classs de la tabla Classs
     */
    public void listAllClasss() {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        List<Classs> result = em.createQuery("from Classs", Classs.class)
                .getResultList();
        for (Classs classs : result) {
            System.out.println("Primario: " + classs.getPrimary() + " Secundario: " + classs.getSecondary());        }
        em.getTransaction().commit();
        em.close();
    }

    /**
     * Invoca el metodo addClasss() en cada Class de la list que mando el metodo readClasssFile()
     * @throws IOException Para que no pete
     */
    public void addAllClasss() throws IOException {
        for (Classs classs : readClasssFile()) {
            addClasss(classs);
        }
    }

    /**
     * Metodo para añadir una Classs a la tabla Classs
     * @param classs El Classs que quieres añadir
     */
    public void addClasss(Classs classs) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.merge(classs);
        em.getTransaction().commit();
        em.close();
    }

    /**
     * Metodo para operatorController donde devolvera una classs dependiendo cual id le des
     * @param number Id por la cual Buscar
     * @return Devuelve un Classs filtrado
     */
    public List<Classs> buscarIdClass(String number) {
            NativeQuery<Classs> query = session.createNativeQuery("SELECT * FROM classs WHERE class_id = :id", Classs.class);
            query.setParameter("id", Integer.parseInt(number));
        return query.getResultList();
    }

    /**
     * Metodo para buscar un Classs por ID
     * @param number Id por la cual filtrar
     */
    public void buscarC(String number) {
        String booleanBonito;
        NativeQuery<Operator> query = session.createNativeQuery("SELECT * FROM Operator WHERE class_Id = :id", Operator.class);
        query.setParameter("id", Integer.parseInt(number));
        List<Operator> tableNames = query.getResultList();
        for (Operator operator : tableNames) {
            booleanBonito = (operator.isAlter_op()) ? "Si" : "No";
            System.out.println("Nombre: " + operator.getNombreO() + " " +
                    "Tipo: " + operator.getPosition_op() + " " +
                    "Tipo de ataque: " + operator.getAttack() + " " +
                    "Tiene Alters?: " + booleanBonito + " " +
                    "Clase: " + operator.getaClass().getPrimary() + " " + operator.getaClass().getSecondary());
        }
    }

    /**
     * Metodo donde borrara todos los datos de la clase seleccionada
     */
    public void deletedAllClasss() {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            String sql = "TRUNCATE TABLE classs";
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

    /**
     * Borra el dato que quiera el usuario
     * @param number Id por la cual filtrar
     */
    public void deletedClasss(String number) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                String sql = "DELETE FROM operator_skill WHERE class_id = :id";
                NativeQuery query = session.createNativeQuery(sql);
                query.setParameter("id", Integer.parseInt(number));
                query.executeUpdate();
                sql = "DELETE FROM operator WHERE class_id = :id";
                query = session.createNativeQuery(sql);
                query.setParameter("id", Integer.parseInt(number));
                query.executeUpdate();
                sql = "DELETE FROM classs WHERE class_id = :id";
                query = session.createNativeQuery(sql);
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