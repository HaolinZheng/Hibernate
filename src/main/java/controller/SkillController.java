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

    /**
     * Metodo Para listar todos las Skill del documento skill.txt
     * @return Una lista de Skill
     * @throws IOException Para que no pete
     */
    public List<Skill> readSkillFile() throws IOException {
        String nombre, nombreS, charge, duration;
        int cost, initial;
        boolean auto;
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
            auto = Boolean.parseBoolean(str.nextToken());
            SkillList.add(new Skill(nombre, nombreS, charge, duration, cost, initial, auto));
        }
        br.close();

        return SkillList;
    }

    /**
     * Muestra todos los Skill de la tabla Skill
     */
    public void listAllSkill() {
        String booleanBonito;
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        List<Skill> result = em.createQuery("from Skill", Skill.class)
                .getResultList();
        for (Skill skill : result) {
            booleanBonito = (skill.isAuto()) ? "si" : "no";
            System.out.println("Operador: " + skill.getNombre() + " " +
                    "Nombre: " + skill.getNombreS() + " " +
                    "Tipo de recarga: " + skill.getCharge() + " " +
                    "Duracion: " + skill.getDuration() + " " +
                    "Coste: " + skill.getCost() + " " +
                    "Coste inicial: " + skill.getInitial() + " " +
                    "Automatico?: " + booleanBonito);
        }
        em.getTransaction().commit();
        em.close();
    }

    /**
     * Invoca el metodo addSkill() en cada Skill de la list que mando el metodo readSkillFile()
     * @throws IOException
     */
    public void addAllSkill() throws IOException {
        for (Skill Skill : readSkillFile()) {
            addSkill(Skill);
        }
    }
    /**
     * Metodo para añadir una Operator a la tabla Operator
     * @param Skill El Skill que quieres añadir
     */
    public void addSkill(Skill Skill) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.merge(Skill);
        em.getTransaction().commit();
        em.close();
    }

    /**
     * Metodo para buscar todos las Skill que tenga los siguiente param
     * @param columna Por cual columna quieres filtrar
     * @param valor Parametro que pide para filtrar
     */
    public void buscarSkill(String columna, String valor) {
        String booleanBonito;
        NativeQuery<Skill> query = session.createNativeQuery("SELECT * FROM Skill WHERE " + columna + " = :valor", Skill.class);
        query.setParameter("valor", Integer.parseInt(valor));
        List<Skill> tableNames = query.getResultList();
        for (Skill skill : tableNames) {
            booleanBonito = (skill.isAuto()) ? "si" : "no";
            System.out.println("Operador: " + skill.getNombre() + " " +
                    "Nombre: " + skill.getNombreS() + " " +
                    "Tipo de recarga: " + skill.getCharge() + " " +
                    "Duracion: " + skill.getDuration() + " " +
                    "Coste: " + skill.getCost() + " " +
                    "Coste inicial: " + skill.getInitial() + " " +
                    "Automatico?: " + booleanBonito);
        }
    }

    /**
     * Metodo donde borrara todos los datos de la clase seleccionada
     */
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

    /**
     * Borra el dato que quiera el usuario
     * @param columna Por cual columna quieres filtrar
     * @param valor Parametro que pide para filtrar
     */
    public void deletedSkill(String columna, String valor) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            String sql = "DELETE FROM Skill WHERE " + columna + " = :valor";
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
     * Modifica el dato que quiera el usuario
     * @param columna - Por cual columna quieres filtrar
     * @param valor - Parametro que pide para filtrar
     * @param cambio - Parametro que pide para cambiar
     */
    public void modificarS(String columna, String valor, String cambio) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            String sql = "UPDATE skill SET ='" + cambio + "' WHERE" + columna + " = :valor";
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
     * Metodo que utiliza el operatorController que recoge un List de Skill filtrado
     * @param valor Nombre por la cual sera filtrada
     * @return List de Skill filtrada
     */
    public List<Skill> buscarSkill(String valor) {
        NativeQuery<Skill> query = session.createNativeQuery("SELECT * FROM Skill WHERE operator_name = :valor", Skill.class);
        query.setParameter("valor", Integer.parseInt(valor));
        List<Skill> tableNames = query.getResultList();
        return tableNames;
    }
}