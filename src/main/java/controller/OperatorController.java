package controller;

import model.Classs;
import model.Operator;
import model.Skill;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class OperatorController {

    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private Session session;
    private ClasssController classsController;
    private SkillController skillController;

    public OperatorController() {
    }
    public OperatorController(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
        this.entityManager = entityManagerFactory.createEntityManager();
        this.session = this.entityManager.unwrap(Session.class);
        this.classsController = new ClasssController(entityManagerFactory);
        this.skillController= new SkillController(entityManagerFactory);
    }

    /**
     * Metodo Para listar todos las Operator del documento operator.txt
     * @return Una lista de Operator
     * @throws IOException Para que no pete
     */
    public List<Operator> readOperatorsFile() throws IOException {
        String nombreO, position_op, attack;
        boolean alter_op;
        int classId;
        Classs clase;
        List<Operator> operatorList = new ArrayList<Operator>();

        BufferedReader br = new BufferedReader(new FileReader("src/main/resources/operator.txt"));
        String linea = "";
        while ((linea = br.readLine()) != null) {
            StringTokenizer str = new StringTokenizer(linea, ",");
            nombreO = str.nextToken();
            position_op = str.nextToken();
            attack = str.nextToken();
            alter_op = Boolean.parseBoolean(str.nextToken());
            classId = Integer.parseInt(str.nextToken());
            clase = classsController.buscarIdClass(classId+"").get(0);
            operatorList.add(new Operator(nombreO, position_op, attack, alter_op, clase, skillController.buscarSkill(nombreO)));
        }
        br.close();

        return operatorList;
    }

    /**
     * Muestra todos los Operator de la tabla Operator
     */
    public void listAllOperator() {
        String booleanBonito;
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        List<Operator> result = em.createQuery("from Operator", Operator.class)
                .getResultList();
        for (Operator operator : result) {
            booleanBonito = (operator.isAlter_op()) ? "Si" : "No";
            System.out.println("Nombre: " + operator.getNombreO() + " " +
                    "Tipo: " + operator.getPosition_op() + " " +
                    "Tipo de ataque: " + operator.getAttack() + " " +
                    "Tiene Alters?: " + booleanBonito + " " +
                    "Clase: " + operator.getaClass().getPrimary() + " " + operator.getaClass().getSecondary());
        }
        em.getTransaction().commit();
        em.close();
    }

    /**
     * Invoca el metodo addOperator() en cada Operator de la list que mando el metodo readOperatorsFile()
     * @throws IOException Para que no pete
     */
    public void addAllOperator() throws IOException {
        for (Operator operator : readOperatorsFile()) {
            addOperator(operator);
        }
    }

    /**
     * Metodo para añadir una Operator a la tabla Operator
     * @param operator El Operator que quieres añadir
     */
    public void addOperator(Operator operator) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.merge(operator);
        em.getTransaction().commit();
        em.close();
    }

    /**
     * Metodo para buscar todos los operator que tenga los siguiente param
     * @param columna Por cual columna quieres filtrar
     * @param valor Parametro que pide para filtrar
     */
    public void buscarOperator(String columna, String valor) {
        String booleanBonito;
        NativeQuery<Operator> query = session.createNativeQuery("SELECT * FROM Operator WHERE " + columna + " = :valor", Operator.class);
        query.setParameter("valor", valor);
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
    public void deletedAllOperator() {
        Transaction transaction = null;
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            transaction = session.beginTransaction();
            em.createNativeQuery("DROP TABLE IF EXISTS operator_skill").executeUpdate();
            em.createNativeQuery("DROP TABLE IF EXISTS Operator").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        em.close();
    }

    /**
     * Modifica el dato que quiera el usuario
     * @param columna - Por cual columna quieres filtrar
     * @param valor - Parametro que pide para filtrar
     * @param cambio - Parametro que pide para cambiar
     */
    public void modificarO(String columna, String valor, String cambio) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            String sql = "UPDATE Operator SET ='" + cambio + "' WHERE" + columna + " = :valor";
            NativeQuery query = session.createNativeQuery(sql);
            query.setParameter("valor", Integer.parseInt(valor));
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
     * @param columna Por cual columna quieres filtrar
     * @param valor Parametro que pide para filtrar
     */
    public void deletedOperator(String columna, String valor) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            String sql = "DELETE FROM operator_skill WHERE "+ columna +" = :valor";
            NativeQuery query = session.createNativeQuery(sql);
            query.setParameter("valor", valor);
            query.executeUpdate();
            sql = "DELETE FROM Operator WHERE " + columna + " = :valor";
            query = session.createNativeQuery(sql);
            query.setParameter("valor", valor);
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