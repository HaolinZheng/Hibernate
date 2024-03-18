package controller;

import model.Classs;
import model.Operator;
import model.Skill;

import javax.persistence.EntityManagerFactory;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class OperatorController {

    private EntityManagerFactory entityManagerFactory;
    private ClasssController classsController = new ClasssController(entityManagerFactory);

    public OperatorController() {
    }
    public List<Operator> readOperatorsFile(String filename, String classFilename, String skillFilename) throws IOException {
        String nombreO, position_op, attack;
        boolean alter_op;
        int classId;
        Classs clase;
        List<Skill> skills;
        List<Operator> operatorList = new ArrayList<Operator>();

        BufferedReader br = new BufferedReader(new FileReader(filename));
        String linea = "";
        List<Classs> classList = classsController.readClasssFile();
        while ((linea = br.readLine()) != null) {
            StringTokenizer str = new StringTokenizer(linea, ",");
            nombreO = str.nextToken();
            position_op = str.nextToken();
            attack = str.nextToken();
            alter_op = Boolean.parseBoolean(str.nextToken());
            classId = Integer.parseInt(str.nextToken());
            clase = classsController.buscarIdClass(classId+"");
            System.out.println(nombreO + position_op + attack + alter_op);
            operatorList.add(new Operator(nombreO, position_op, attack, alter_op, clase, skills));
        }
        br.close();

        return operatorList;
    }

    public OperatorController(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }
}