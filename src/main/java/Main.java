import java.awt.*;
import java.io.IOException;

import controller.*;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import view.Menu;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {

    static SessionFactory sessionFactoryObj;

    private static SessionFactory buildSessionFactory() {
        try {
            StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder()
                    .configure("hibernate.cfg.xml").build();
            Metadata metadata = new MetadataSources(standardRegistry).getMetadataBuilder().build();
            return metadata.getSessionFactoryBuilder().build();
        } catch (HibernateException he) {
            System.out.println("Session Factory creation failure");
            throw he;
        }
    }

    public static EntityManagerFactory createEntityManagerFactory() {
        EntityManagerFactory emf;
        try {
            emf = Persistence.createEntityManagerFactory("JPAMagazines");
        } catch (Throwable ex) {
            System.err.println("Failed to create EntityManagerFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
        return emf;
    }

    public static void main(String[] args) throws IOException {
        Menu menu = new Menu();

        EntityManagerFactory entityManagerFactory = createEntityManagerFactory();

        ClasssController classController = new ClasssController(entityManagerFactory);
        OperatorController operatorController = new OperatorController(entityManagerFactory);
        SkillController skillController = new SkillController(entityManagerFactory);

        int option = menu.mainMenu();
        int primary;
        while (option > 0 && option < 23) {
            switch (option) {
                case 1:
                    classController.listAllClasss();
                    break;
                case 2:
                    primary = menu.primaryMenu();
                    switch (primary) {
                        case 1:
                            classController.buscarC(menu.casterMenu(primary));
                            break;
                        case 2:
                            classController.buscarC(menu.defenderMenu(primary));
                            break;
                        case 3:
                            classController.buscarC(menu.guardMenu(primary));
                            break;
                        case 4:
                            classController.buscarC(menu.medicMenu(primary));
                            break;
                        case 5:
                            classController.buscarC(menu.sniperMenu(primary));
                            break;
                        case 6:
                            classController.buscarC(menu.specialistMenu(primary));
                            break;
                        case 7:
                            classController.buscarC(menu.supporterMenu(primary));
                            break;
                        case 8:
                            classController.buscarC(menu.vanguardMenu(primary));
                            break;
                        case 9:
                            break;
                    }
                    break;
                case 3:
                    classController.addAllClasss();
                    break;
                case 4:
                    primary = menu.primaryMenu();
                    switch (primary) {
                        case 1:
                            classController.deletedClasss(menu.casterMenu(primary));
                            break;
                        case 2:
                            classController.deletedClasss(menu.defenderMenu(primary));
                            break;
                        case 3:
                            classController.deletedClasss(menu.guardMenu(primary));
                            break;
                        case 4:
                            classController.deletedClasss(menu.medicMenu(primary));
                            break;
                        case 5:
                            classController.deletedClasss(menu.sniperMenu(primary));
                            break;
                        case 6:
                            classController.deletedClasss(menu.specialistMenu(primary));
                            break;
                        case 7:
                            classController.deletedClasss(menu.supporterMenu(primary));
                            break;
                        case 8:
                            classController.deletedClasss(menu.vanguardMenu(primary));
                            break;
                        case 9:
                            break;
                    }
                    break;
                case 5:
                    classController.deletedAllClasss();
                    break;
                case 6:
                    break;
                case 7:
                    classController.deletedAllClasss();
                    break;
                case 8:

                    break;
                case 9:
                    break;
                case 10:
                    break;
                case 11:
                    break;
                case 12:
                    skillController.listAllSkill();
                    break;
                case 13:
                    skillController.buscarSkill(menu.pedirS(),menu.pedir());
                    break;
                case 14:
                    skillController.addAllSkill();
                    break;
                case 15:
                    break;
                case 16:
                    break;
                case 17:
                    skillController.deletedAllSkill();
                    break;
                case 18:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Introdueixi una de les opcions anteriors");
                    break;
            }
            option = menu.mainMenu();
        }

    }
}