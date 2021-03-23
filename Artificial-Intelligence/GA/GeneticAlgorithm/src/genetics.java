import java.util.*;
import java.io.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author brice
 */
public class genetics {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        ArrayList<Population.Period> mainPeriodList = new ArrayList<>();
        ArrayList<Population.Room> mainRoomList = new ArrayList<>();
        ArrayList<Population.Professors> mainProfessorList = new ArrayList<>();
        ArrayList<Population.Course> mainBestScheduleList = new ArrayList<>();
        ArrayList<ArrayList> mainPopulationList = new ArrayList<>();
        ArrayList<ArrayList> mainChildPopulationList = new ArrayList<>();
        Population thing = new Population();
        int populationSize = 50;
        int numberOfIterations = 10000;
        int fitness = 0;
        int oldFitness = 0;
        int bestFitGen = 0;
        int probComb = 75/100;
        int probMut = 5/100;
        int n = 50;
        //CAN USE THESE ARRAY LISTS TO POPULATE THE ONES IN POPULATION.
        thing.populatePeriodList(mainPeriodList);
        thing.populateProfessorList(mainProfessorList);
        thing.populateRoomList(mainRoomList);
        System.out.println("Please enter the number of iterations you want: ");
        numberOfIterations = input.nextInt();
        
        //FINDING THE BEST SCHEDULE POSSIBLE METHOD.
        thing.generatePopulation(mainPopulationList, mainRoomList, mainPeriodList, mainProfessorList, populationSize);
        for (int i = 0; i < numberOfIterations; i++) {
            mainChildPopulationList = new ArrayList();
            //score the fitness of the original population
            thing.fitness(mainPopulationList, mainRoomList);
            for (int j = 0; j < mainPopulationList.size(); j++) {
                fitness = thing.getFitness(mainPopulationList.get(j));
                if (fitness > oldFitness) {
                    bestFitGen = i;
                    oldFitness = fitness;
                    mainBestScheduleList = new ArrayList();
                    ArrayList<Population.Course> sch = mainPopulationList.get(j);
                    for (int k =0; k < sch.size(); k++) {
                        Population.Course temp = new Population.Course();
                        temp.setCourseRoomNumber(sch.get(k).getCourseRoomNumber());
                        temp.setCourse(sch.get(k).getCourse());
                        temp.setProfessor(sch.get(k).getProfessor());
                        temp.setClassSize(sch.get(k).getClassSize());
                        temp.needMedia(sch.get(k).isMedia());
                        temp.setClassTime(sch.get(k).getTime());
                        temp.setRoomID(sch.get(k).getRoomID());
                        temp.setFitness(fitness);
                        mainBestScheduleList.add(temp);
                    }
                }
            }
            //select parents and make child population
            thing.tournamentSelection(mainPopulationList, mainChildPopulationList, n, probComb, probMut);
            //make child pop the population and go again
            mainPopulationList = new ArrayList();
            mainPopulationList = mainChildPopulationList;
            
        }
        System.out.println("Best schedule found at generation " + bestFitGen + " with a score " + fitness + ":");
        thing.printSchedule(mainBestScheduleList, mainProfessorList, mainPeriodList, mainRoomList);
    }
   
    
}
