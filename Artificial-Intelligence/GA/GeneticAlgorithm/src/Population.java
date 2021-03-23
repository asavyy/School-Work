import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author brice
 */

public class Population {
    public static ArrayList<Period> periodList = new ArrayList<>();
    public static ArrayList<Room> roomList = new ArrayList<>();
    public static ArrayList<Professors> professorList = new ArrayList<>();
    public static ArrayList<Course> bestScheduleList = new ArrayList<>();
    public static ArrayList<ArrayList> populationList = new ArrayList<>();
    public static ArrayList<ArrayList> childPopulationList = new ArrayList<>();
    
    
    //----------------------------THE FOLLOWING METHODS HELP POPULATE THE LISTS WITH HARDCODED DATA-----------------------
    //POPULATING PERIOD LIST
    public void populatePeriodList(ArrayList<Period> tempPeriodList) {
      //Here we individually add each tuple of the 
      //Period table provided in the assignment details starting
      //From the top.
        Period period = new Period();// Create a new instance of Period
        //Based off of the charts given on the page, we populate our periodList as it's ordered top to bottom.
        period.setPeriodID(2); //Set the ID (order)
        period.setClassTime(9); // Set the time in which it starts
        tempPeriodList.add(period); // add it to our temporay array list
//----------------------------------------------------------------------------------------------------------------
        period = new Period(); // create a new instance of Period to store information about the next tuple.     
        period.setPeriodID(3);
        period.setClassTime(10);
        tempPeriodList.add(period);
//----------------------------------------------------------------------------------------------------------------
        period = new Period();        
        period.setPeriodID(4);
        period.setClassTime(11);
        tempPeriodList.add(period);
//----------------------------------------------------------------------------------------------------------------
        period = new Period();
        period.setPeriodID(5);
        period.setClassTime(12);
        tempPeriodList.add(period);
//----------------------------------------------------------------------------------------------------------------
        period = new Period();
        period.setPeriodID(6);
        period.setClassTime(1);
        tempPeriodList.add(period);
//----------------------------------------------------------------------------------------------------------------
        period = new Period();
        period.setPeriodID(7);
        period.setClassTime(2);
        tempPeriodList.add(period);
//----------------------------------------------------------------------------------------------------------------
        period = new Period();
        period.setPeriodID(8);
        period.setClassTime(3);
        tempPeriodList.add(period);
//----------------------------------------------------------------------------------------------------------------
        //We set our public periodList equal to the tempPeriodList in order
        //to use the elements further down the road. 
        periodList = tempPeriodList;
    }
//----------------------------------------------------------------------------------------------------------------
    //POPULATING ROOM LIST
    public void populateRoomList(ArrayList<Room> tempRoomList){
        //Here we individually add each tuple of the 
        //Period table provided in the assignment details starting
        //From the top.
        Room room = new Room(); // create a new instance of room
        room.setRoomName("BL134");
        room.setRoomID(1);
        room.setRoomCapacity(30);
        room.hasMedia(true);
        tempRoomList.add(room);
//----------------------------------------------------------------------------------------------------------------
        room = new Room();
        room.setRoomName("BL138");
        room.setRoomID(2);
        room.setRoomCapacity(50);
        room.hasMedia(true);
        tempRoomList.add(room);
//----------------------------------------------------------------------------------------------------------------
        room = new Room();
        room.setRoomName("KR224");
        room.setRoomID(3);
        room.setRoomCapacity(40);
        room.hasMedia(false);
        tempRoomList.add(room);
//----------------------------------------------------------------------------------------------------------------
        room = new Room();
        room.setRoomName("KR206");
        room.setRoomID(4);
        room.setRoomCapacity(30);
        room.hasMedia(true);
        tempRoomList.add(room);
//----------------------------------------------------------------------------------------------------------------
        room = new Room();
        room.setRoomName("Biddle123");
        room.setRoomID(5);
        room.setRoomCapacity(35);
        room.hasMedia(false);
        tempRoomList.add(room);
//----------------------------------------------------------------------------------------------------------------
        room = new Room();
        room.setRoomName("Biddle205");
        room.setRoomID(6);
        room.setRoomCapacity(45);
        room.hasMedia(false);
        tempRoomList.add(room);
//----------------------------------------------------------------------------------------------------------------
        room = new Room();
        room.setRoomName("ES100");
        room.setRoomID(7);
        room.setRoomCapacity(100);
        room.hasMedia(true);
        tempRoomList.add(room);
//----------------------------------------------------------------------------------------------------------------   
        //We set our public roomList equal to the tempRoomList in order
        //to use the elements further down the road. 
        roomList = tempRoomList;
    }
    
    public void populateProfessorList(ArrayList<Professors> tempProfessorList){
        //Here we individually add each of the 
        //professors listed in the assignment. We
        //give them an ID to make it easier to use them
        //when generating our population.
        Professors prof = new Professors(); //create a new instance of the professor object
        prof.setProfessorID(1);
        prof.setProfessorName("Bilitski");
        tempProfessorList.add(prof);
//----------------------------------------------------------------------------------------------------------------
        prof = new Professors();
        prof.setProfessorID(2);
        prof.setProfessorName("Hagerich");
        tempProfessorList.add(prof);
//----------------------------------------------------------------------------------------------------------------
        prof = new Professors();
        prof.setProfessorID(3);
        prof.setProfessorName("Smigla");
        tempProfessorList.add(prof);
        prof = new Professors();
        prof.setProfessorID(4);
        prof.setProfessorName("IM");
        tempProfessorList.add(prof);
//----------------------------------------------------------------------------------------------------------------
        prof = new Professors();
        prof.setProfessorID(5);
        prof.setProfessorName("Frederick");
        tempProfessorList.add(prof);
        prof = new Professors();
        prof.setProfessorID(6);
        prof.setProfessorName("Ferencek");
        tempProfessorList.add(prof);
//----------------------------------------------------------------------------------------------------------------
        prof = new Professors();
        prof.setProfessorID(7);
        prof.setProfessorName("Thomson");
        tempProfessorList.add(prof);
//----------------------------------------------------------------------------------------------------------------
        prof = new Professors();
        prof.setProfessorID(8);
        prof.setProfessorName("Darling");
        tempProfessorList.add(prof);
//----------------------------------------------------------------------------------------------------------------
        prof = new Professors();
        prof.setProfessorID(9);
        prof.setProfessorName("Hinderliter");
        tempProfessorList.add(prof);
//----------------------------------------------------------------------------------------------------------------       
        professorList = tempProfessorList;
    }
    
    //-------------------------------------------HERE WE GENERATE OUR INITIAL POPULATION USING THE ABOVE LISTS-----------------------------
    
    public void generatePopulation(ArrayList<ArrayList> tempPopulationList, ArrayList<Room> tempRoomList, ArrayList<Period> tempPeriodList, ArrayList<Professors> tempProfessorList, int populationSize){
        professorList = tempProfessorList;
        periodList = tempPeriodList;
        roomList = tempRoomList;
        populationList = tempPopulationList;
        
        ArrayList<Course> schedule;
        for (int currentAnimal = 0; currentAnimal < populationSize; currentAnimal++) {
            Course course;
            schedule = new ArrayList<>();
            
//----------------------------------------------------------------------------------------------------------------
            course = new Course();
            course.setCourseRoomNumber(1);
            course.setCourse("CS456");
            course.setProfessor(0);
            course.setClassSize(20);
            course.needMedia(true);
             
            schedule.add(course);
            
            if (checkCourse(schedule, course, tempRoomList)) {
                currentAnimal--;
                continue;
            }
//----------------------------------------------------------------------------------------------------------------
            course = new Course();
            course.setCourseRoomNumber(2);
            course.setCourse("CS456");
            course.setProfessor(0);
            course.setClassSize(20);
            course.needMedia(true);
             
            schedule.add(course);
            
            if (checkCourse(schedule, course, tempRoomList)) {
                currentAnimal--;
                continue;
            }
//----------------------------------------------------------------------------------------------------------------            
            course = new Course();
            course.setCourseRoomNumber(3);
            course.setCourse("CS1783");
            course.setProfessor(0);
            course.setClassSize(15);
            course.needMedia(true);
             
            schedule.add(course);
            
            if (checkCourse(schedule, course, tempRoomList)) {
                currentAnimal--;
                continue;
            }
//----------------------------------------------------------------------------------------------------------------            
            course = new Course();
            course.setCourseRoomNumber(4);
            course.setCourse("CS455");
            course.setProfessor(1);
            course.setClassSize(20);
            course.needMedia(true);
             
            schedule.add(course);
            
            if (checkCourse(schedule, course, tempRoomList)) {
                currentAnimal--;
                continue;
            }
//----------------------------------------------------------------------------------------------------------------            
            course = new Course();
            course.setCourseRoomNumber(5);
            course.setCourse("CS455");
            course.setProfessor(1);
            course.setClassSize(20);
            course.needMedia(true);
             
            schedule.add(course);
            
            if (checkCourse(schedule, course, tempRoomList)) {
                currentAnimal--;
                continue;
            }
//----------------------------------------------------------------------------------------------------------------
            course = new Course();
            course.setCourseRoomNumber(6);
            course.setCourse("CS015");
            course.setProfessor(2);
            course.setClassSize(35);
            course.needMedia(true);
             
            schedule.add(course);
            
            if (checkCourse(schedule, course, tempRoomList)) {
                currentAnimal--;
                continue;
            }
//----------------------------------------------------------------------------------------------------------------            
            course = new Course();
            course.setCourseRoomNumber(7);
            course.setCourse("CS015");
            course.setProfessor(3);
            course.setClassSize(35);
            course.needMedia(false);
             
            schedule.add(course);
            
            if (checkCourse(schedule, course, tempRoomList)) {
                currentAnimal--;
                continue;
            }
//----------------------------------------------------------------------------------------------------------------            
            course = new Course();
            course.setCourseRoomNumber(8);
            course.setCourse("CS015");
            course.setProfessor(3);
            course.setClassSize(35);
            course.needMedia(false);
             
            schedule.add(course);
            
            if (checkCourse(schedule, course, tempRoomList)) {
                currentAnimal--;
                continue;
            }   
//----------------------------------------------------------------------------------------------------------------            
            course = new Course();
            course.setCourseRoomNumber(9);
            course.setCourse("CS015");
            course.setProfessor(4);
            course.setClassSize(35);
            course.needMedia(false);
             
            schedule.add(course);
            
            if (checkCourse(schedule, course, tempRoomList)) {
                currentAnimal--;
                continue;
            }
//----------------------------------------------------------------------------------------------------------------            
            course = new Course();
            course.setCourseRoomNumber(10);
            course.setCourse("MATH001");
            course.setProfessor(5);
            course.setClassSize(40);
            course.needMedia(false);
             
            schedule.add(course);
            
            if (checkCourse(schedule, course, tempRoomList)) {
                currentAnimal--;
                continue;
            }
//----------------------------------------------------------------------------------------------------------------            
            course = new Course();
            course.setCourseRoomNumber(11);
            course.setCourse("MATH001");
            course.setProfessor(5);
            course.setClassSize(50);
            course.needMedia(false);
             
            schedule.add(course);
              
            if (checkCourse(schedule, course, tempRoomList)) {
                currentAnimal--;
                continue;
            }
//----------------------------------------------------------------------------------------------------------------            
            course.setCourseRoomNumber(12);
            course.setCourse("MATH001");
            course.setProfessor(5);
            course.setClassSize(60);
            course.needMedia(false);
             
            schedule.add(course);
            
            if (checkCourse(schedule, course, tempRoomList)) {
                currentAnimal--;
                continue;
            }
//----------------------------------------------------------------------------------------------------------------            
            course = new Course();
            course.setCourseRoomNumber(13);
            course.setCourse("MATH002");
            course.setProfessor(6);
            course.setClassSize(40);
            course.needMedia(false);
             
            schedule.add(course);
            
            if (checkCourse(schedule, course, tempRoomList)) {
                currentAnimal--;
                continue;
            }
 //----------------------------------------------------------------------------------------------------------------           
            course = new Course();
            course.setCourseRoomNumber(14);
            course.setCourse("MATH002");
            course.setProfessor(6);
            course.setClassSize(50);
            course.needMedia(false);
             
            schedule.add(course);
            
            if (checkCourse(schedule, course, tempRoomList)) {
                currentAnimal--;
                continue;
            }
//----------------------------------------------------------------------------------------------------------------           
            course = new Course();
            course.setCourseRoomNumber(15);
            course.setCourse("MATH002");
            course.setProfessor(6);
            course.setClassSize(60);
            course.needMedia(false);
             
            schedule.add(course);
            
            if (checkCourse(schedule, course, tempRoomList)) {
                currentAnimal--;
                continue;
            }
//----------------------------------------------------------------------------------------------------------------                                                           
            course = new Course();
            course.setCourseRoomNumber(16);
            course.setCourse("SOC100");
            course.setProfessor(7);
            course.setClassSize(45);
            course.needMedia(true);
             
            schedule.add(course);
            
            if (checkCourse(schedule, course, tempRoomList)) {
                currentAnimal--;
                continue;
            }
//----------------------------------------------------------------------------------------------------------------            
            course = new Course();
            course.setCourseRoomNumber(17);
            course.setCourse("SOC100");
            course.setProfessor(7);
            course.setClassSize(40);
            course.needMedia(true);
             
            schedule.add(course);
            
            if (checkCourse(schedule, course, tempRoomList)) {
                currentAnimal--;
                continue;
            }                                                              
//----------------------------------------------------------------------------------------------------------------
            course = new Course();
            course.setCourseRoomNumber(18);
            course.setCourse("SOC100");
            course.setProfessor(7);
            course.setClassSize(35);
            course.needMedia(true);
             
            schedule.add(course);
            
            if (checkCourse(schedule, course, tempRoomList)) {
                currentAnimal--;
                continue;
            }
//----------------------------------------------------------------------------------------------------------------            
            course = new Course();
            course.setCourseRoomNumber(19);
            course.setCourse("CS047");
            course.setProfessor(0);
            course.setClassSize(15);
            course.needMedia(true);
             
            schedule.add(course);
            
            if (checkCourse(schedule, course, tempRoomList)) {
                currentAnimal--;
                continue;
            }
//----------------------------------------------------------------------------------------------------------------            
            course = new Course();
            course.setCourseRoomNumber(20);
            course.setCourse("CS047");
            course.setProfessor(0);
            course.setClassSize(15);
            course.needMedia(true);
             
            schedule.add(course);
            
            if (checkCourse(schedule, course, tempRoomList)) {
                currentAnimal--;
                continue;
            }
//----------------------------------------------------------------------------------------------------------------            
            course = new Course();
            course.setCourseRoomNumber(21);
            course.setCourse("PSY200");
            course.setProfessor(8);
            course.setClassSize(30);
            course.needMedia(false);
             
            schedule.add(course);
            
            if (checkCourse(schedule, course, tempRoomList)) {
                currentAnimal--;
                continue;
            }
//----------------------------------------------------------------------------------------------------------------           
            course = new Course();
            course.setCourseRoomNumber(22);
            course.setCourse("PSY200");
            course.setProfessor(8);
            course.setClassSize(35);
            course.needMedia(false);
             
            schedule.add(course);
            
            if (checkCourse(schedule, course, tempRoomList)) {
                currentAnimal--;
                continue;
            }
//----------------------------------------------------------------------------------------------------------------                     
            course = new Course();
            course.setCourseRoomNumber(23);
            course.setCourse("PSY200");
            course.setProfessor(8);
            course.setClassSize(30);
            course.needMedia(false);
             
            schedule.add(course);
            
            if (checkCourse(schedule, course, tempRoomList)) {
                currentAnimal--;
                continue;
            }
//----------------------------------------------------------------------------------------------------------------            
            course = new Course();
            course.setCourseRoomNumber(24);
            course.setCourse("CS045");
            course.setProfessor(1);
            course.setClassSize(20);
            course.needMedia(true);
             
            schedule.add(course);
            
            if (checkCourse(schedule, course, tempRoomList)) {
                currentAnimal--;
                continue;
            }
//----------------------------------------------------------------------------------------------------------------
            course = new Course();
            course.setCourseRoomNumber(25);
            course.setCourse("CS045");
            course.setProfessor(1);
            course.setClassSize(20);
            course.needMedia(true);
             
            schedule.add(course);
            
            if (checkCourse(schedule, course, tempRoomList)) {
                currentAnimal--;
                continue;
            }
//----------------------------------------------------------------------------------------------------------------
            course = new Course();
            course.setCourseRoomNumber(26);
            course.setCourse("CS015");
            course.setProfessor(2);
            course.setClassSize(20);
            course.needMedia(true);
             
            schedule.add(course);   
            
            if (checkCourse(schedule, course, tempRoomList)) {
                currentAnimal--;
                continue;
            }                      
//----------------------------------------------------------------------------------------------------------------            
             tempPopulationList.add(schedule);
        }
    }
//--------------------------------- END OF LIST POPULATION METHODS------------------------------------------------
//-----------------------------------HERE WE CHECK TO SEE WHETHER A TIME & DATE HAS ALREADY BEEN USED--------------------------
    public static boolean checkCourse(ArrayList<Course> tempPopulationList, Course course, ArrayList<Room> roomListTemp) {
        //get a random date and time and see if that combo is already used
        //if it is, get a new date and time and recheck until get a combo not used
        int timeDoesNotExist = 1;
        Random rand = new Random(); // Declare random to use later
        int time = 0; // integer var to store a random time
        int room = 0; //integer to store a random room selected
        int check = 0;
        //We prime our while loop by setting it to true. If we find a valid match for classes then we set it to false.
        while (timeDoesNotExist == 1) {
            check++; //We check all 50 "animals" in our current population so see whether or not they already exist or not.
            if (check > 50) {//If check is larger than 50, we leave. Not >= because we need to count the 50th "animal"
                return true; //returning a negative one means that we are done.
            }
            timeDoesNotExist = -1;
            //Initializing our random variables to 7. 
            time = rand.nextInt(7);
            room = rand.nextInt(7);
            //Initial check on the class size vs. the room capacity
            if (course.getClassSize() > roomList.get(room).getRoomCapacity()){
                timeDoesNotExist = 1;
                continue; // If the class size is larger than the room capacity, continue the loop and attempt to find another room.
            }
            //Here we check to see whether or not a room was multimedia or not. 
            if (course.isMedia() == true && roomList.get(room).isMedia() == false) {
                timeDoesNotExist = 1;
                continue; // continue the loop and try to pick a new room.
            }
            //This checks to see whether or not 2 professors are within the same class at the same time. 
            //It also checks to see whether or not the schedule contains two classes at the exact same time.
            for (int i=0; i < tempPopulationList.size(); i++){
                if (((tempPopulationList.get(i).getTime() == time) && (tempPopulationList.get(i).getRoomID() == room))//if time and room are the same anywhere on tempPopulationList
                    || ((tempPopulationList.get(i).getProfessor() == course.getProfessor()) && (tempPopulationList.get(i).getTime() == time))){ // or two professors are in the same room on the list
                        timeDoesNotExist = 1; //set true, break from the loop.
                        break;
                }
            }
        }
        //If we find a combination of class time and a room that aren't in existence yet, then they can be added into the course for schedule.
        course.setClassTime(time);
        course.setRoomID(room);
        return false;
    }
    
    public static void tournamentSelection(ArrayList<ArrayList> population, ArrayList<ArrayList> childPop,int n, int probComb, int probMut) {
        //We use tournament selection for selection. We'll start by picking two random animals and compare their fitness. 
        //Whoever has the highest fitness will be the first parent. Next, we select another two random animals and compare their fitness
        //Whoever has the highest fitness will be selected at the second parent.
        Random rand = new Random();
        ArrayList<Course> firstSchedule, secondSchedule, firstParent, secondParent, firstChild, secondChild;
        int firstFitness, secondFitness;
        int currentPopulationSize = 0;
        int checkProb = 0;
        while (currentPopulationSize < n) { //Loop until we get to N parents
            //--------How we select 2 random parents---------
            firstSchedule = population.get(rand.nextInt(n));
            secondSchedule = population.get(rand.nextInt(n));
            //--------Gathering their fitness------------
            firstFitness = getFitness(firstSchedule);
            secondFitness = getFitness(secondSchedule);
            //----------If-else to determine who has the highest fitness------
            if (firstFitness >= secondFitness) {
                firstParent = firstSchedule; //Set first parent to first schedule if it has higher fitness
            } else {
                firstParent = secondSchedule; //Else, set first parent equal to the second schedule. (secondSchedule had higher fitness)
            }
            //Reupdating our first & second schedule values to be new population members.
            firstSchedule = population.get(rand.nextInt(n));
            secondSchedule = population.get(rand.nextInt(n));
            //--------Gathering their fitness------------
            firstFitness = getFitness(firstSchedule);
            secondFitness = getFitness(secondSchedule);
            //----------If-else to determine who has the highest fitness------
            if (firstFitness >= secondFitness) {
                secondParent = firstSchedule; //Set first parent to first schedule if it has higher fitness
            } else {
                secondParent = secondSchedule; //Else, set first parent equal to the second schedule. (secondSchedule had higher fitness)
            }
            
            //Now that we got the parents try to clone/crossover
            checkProb = rand.nextInt(100)/100; // crossover is determined by generating a random value / 100 (percentage)
            firstChild = new ArrayList();
            secondChild = new ArrayList();
            if (checkProb < probComb) { // If the crossover probability is less than the chance of probability defined, perform crossover.
                crossover(firstParent, secondParent, firstChild, secondChild);
            } else { //Else, we will clone that animal and continue elsewhere.
                Course tempSchedule1;
                Course tempSchedule2;
                for (int i = 0; i < firstParent.size(); i++) { //THIS FOR LOOP IS WHAT CLONES A SCHEDULE
                    tempSchedule1 = new Course();
                    tempSchedule2 = new Course();
                    //SET TEMPSCHEDULE1 EQUAL TO THE FIRST PARENT TO CLOSE
                    tempSchedule1.setCourseRoomNumber(firstParent.get(i).getCourseRoomNumber());
                    tempSchedule1.setCourse(firstParent.get(i).getCourse());
                    tempSchedule1.setProfessor(firstParent.get(i).getProfessor());
                    tempSchedule1.setClassSize(firstParent.get(i).getClassSize());
                    tempSchedule1.needMedia(firstParent.get(i).isMedia());
                    tempSchedule1.setClassTime(firstParent.get(i).getTime());
                    tempSchedule1.setRoomID(firstParent.get(i).getRoomID());
                    //SET TEMPSCHEDULE2 EQUAL TO THE SECOND PARENT TO CLONE.
                    tempSchedule2.setCourseRoomNumber(secondParent.get(i).getCourseRoomNumber());
                    tempSchedule2.setCourse(secondParent.get(i).getCourse());
                    tempSchedule2.setProfessor(secondParent.get(i).getProfessor());
                    tempSchedule2.setClassSize(secondParent.get(i).getClassSize());
                    tempSchedule2.needMedia(secondParent.get(i).isMedia());
                    tempSchedule2.setClassTime(secondParent.get(i).getTime());
                    tempSchedule2.setRoomID(secondParent.get(i).getRoomID());
                    //ADD THEM BOTH TO THEIR RESPECTIVE CHILD LISTS FOR NEXT GENERATION.
                    firstChild.add(tempSchedule1);
                    secondChild.add(tempSchedule2);
                }
            }
            
            //CHECK TO SEE MUTATION HAPPENS
            checkProb = rand.nextInt(100)/100;
            if (checkProb > probMut) { // IF IT IS POSSIBLE, WE RNADOMLY 
                int spot = rand.nextInt(25); //Randomly select a spot within the list
                int time = rand.nextInt(7); // Random time
                firstChild.get(spot).setClassTime(time); //Set new mutation
                spot  = rand.nextInt(25); // update mutations so they differ from the first
                time = rand.nextInt(7); //Same thing as above line, in regards to time
                secondChild.get(spot).setClassTime(time); // Set new mutation
                //MUST CHECK TO SEE IF WE BROKE ANYTHING!
                checkSchedule(firstChild);
                checkSchedule(secondChild);
            }
            //IF ALL IS GOOD, ADD THE CHILDREN AND CONTINUE.
            childPop.add(firstChild);
            childPop.add(secondChild);
            currentPopulationSize = currentPopulationSize +2;
        }
    }
    
    public static void crossover(ArrayList<Course> firstParent, ArrayList<Course> secondParent, ArrayList<Course> firstChild, ArrayList<Course> secondChild) {
        Random rand = new Random();
        //We pick a random position to start with our crossover.
        int position = rand.nextInt(25); //selecting a random position
        Course tempSchedule1; //Create two new objects of Course so we cna perform crossover with them TEMPORARILY
        Course tempSchedule2;
        for (int i = 0; i < position; i++) {
            //HERE WE MOVE THE TOP HALF FROM OUR FIRST PARENT
            tempSchedule1 = new Course();
            tempSchedule1.setCourseRoomNumber(firstParent.get(i).getCourseRoomNumber());
            tempSchedule1.setCourse(firstParent.get(i).getCourse());
            tempSchedule1.setProfessor(firstParent.get(i).getProfessor());
            tempSchedule1.setClassSize(firstParent.get(i).getClassSize());
            tempSchedule1.needMedia(firstParent.get(i).isMedia());
            tempSchedule1.setClassTime(firstParent.get(i).getTime());
            tempSchedule1.setRoomID(firstParent.get(i).getRoomID());
            firstChild.add(tempSchedule1);
            //TO CHILD 1
            tempSchedule2 = new Course();
            tempSchedule2.setCourseRoomNumber(secondParent.get(i).getCourseRoomNumber());
            tempSchedule2.setCourse(secondParent.get(i).getCourse());
            tempSchedule2.setProfessor(secondParent.get(i).getProfessor());
            tempSchedule2.setClassSize(secondParent.get(i).getClassSize());
            tempSchedule2.needMedia(secondParent.get(i).isMedia());
            tempSchedule2.setClassTime(secondParent.get(i).getTime());
            tempSchedule2.setRoomID(secondParent.get(i).getRoomID());
            secondChild.add(tempSchedule2);
        }
        for (int i = position; i < firstParent.size(); i++) {
            // FOR LOOP FOR THE BOTTOM HALF.
            tempSchedule1 = new Course();
            tempSchedule1.setCourseRoomNumber(secondParent.get(i).getCourseRoomNumber());
            tempSchedule1.setCourse(secondParent.get(i).getCourse());
            tempSchedule1.setProfessor(secondParent.get(i).getProfessor());
            tempSchedule1.setClassSize(secondParent.get(i).getClassSize());
            tempSchedule1.needMedia(secondParent.get(i).isMedia());
            tempSchedule1.setClassTime(secondParent.get(i).getTime());
            tempSchedule1.setRoomID(secondParent.get(i).getRoomID());
            firstChild.add(tempSchedule1);
            
            tempSchedule2 = new Course();
            tempSchedule2.setCourseRoomNumber(firstParent.get(i).getCourseRoomNumber());
            tempSchedule2.setCourse(firstParent.get(i).getCourse());
            tempSchedule2.setProfessor(firstParent.get(i).getProfessor());
            tempSchedule2.setClassSize(firstParent.get(i).getClassSize());
            tempSchedule2.needMedia(firstParent.get(i).isMedia());
            tempSchedule2.setClassTime(firstParent.get(i).getTime());
            tempSchedule2.setRoomID(firstParent.get(i).getRoomID());
            secondChild.add(tempSchedule2);
        }
        
        //WE NEED TO CHECK TO SEE IF ANYTHING BROKE WHILE PERFORMING CROSSOVER.
        checkSchedule(firstChild);
        checkSchedule(secondChild);
    }
    
    //Basically a version of checkCourse, but for the entirety of a schedule.
    //It will also modify the schedule if any issues occur. 
    public static void checkSchedule(ArrayList<Course> schedule) {
        Random rand = new Random();
        int index = 0;
        boolean scheduleNeedsFixed = true;
        while (scheduleNeedsFixed) {
            for (int i = 0; i < schedule.size(); i++) { // This for loop goes through the entirety of a schedule and checks to see whether 
                //or not a room is used twice at the same time or if a professor is teaching twice or more at the same time
                if (((schedule.get(index).getTime() == schedule.get(i).getTime()) && (schedule.get(index).getRoomID() == schedule.get(i).getRoomID()))
                    || ((schedule.get(index).getProfessor() == schedule.get(i).getProfessor() && (schedule.get(index).getTime() == schedule.get(i).getTime())))){ 
                        if (index == i) { 
                        } else {
                            schedule.get(i).setClassTime(rand.nextInt(7));//Try to change the time if complications are found.
                            index = 0; //reset our index
                            i = 0; //reset i
                        }
                } 
            }
            index++; //update the index to continue iterating the same schedule. 
            scheduleNeedsFixed = false; 
        }
    }
       
    //THIS FUNCTION IS WHAT DETERMINES OUR FITNESS SCORE
    //IT DOES SO FOR EVERY ELEMENT WITHIN THE POPULATION LIST.
    public static void fitness(ArrayList<ArrayList> population, ArrayList<Room> room) {
        Course checkCourse;
        
        for (int j=0; j < population.size(); j++) {
            ArrayList<Course> schedule;

            //This generally checks to see if a seat was wasted. This subtracts 1 off of the initial fitness.
            int diffSeats;
            schedule = population.get(j);
            for (int i = 0; i<population.get(j).size(); i++) {
                int fitnessScore = 100;
                checkCourse = schedule.get(i);
                diffSeats = room.get(checkCourse.getRoomID()).getRoomCapacity() - checkCourse.getClassSize();
                int bonus = getBonus(schedule, checkCourse);
                int penalty = getPenalty(schedule, checkCourse);
                fitnessScore = fitnessScore - diffSeats + bonus - penalty; 
                checkCourse.setFitness(fitnessScore);
            }
        }
    }
    //THIS IS A GETTER FOR OUR FITNESS SCORES.
    public static int getFitness(ArrayList<Course> schedule) {
        int score = 0;
        for (int i=0; i<schedule.size(); i++) {
            score += schedule.get(i).getFitness();
        }
        return score;
    }
    
    public static int getBonus(ArrayList<Course> schedule, Course course) {
        int bonus = 0;
        
        int prof = course.getProfessor();
        int room = course.getRoomID();
        for (int j=0; j < schedule.size(); j++) {
            if (prof == schedule.get(j).getProfessor() && room == schedule.get(j).getRoomID()) {
                bonus = bonus +5;
            }
        }
        //remove the count for itself that it does
        bonus = bonus - 5;
        return bonus;
    }
    
    public static int getPenalty(ArrayList<Course> schedule, Course course) {
        int penalty = 0;
        int prof = course.getProfessor();
       // int time = course.getTime();
        int hoursDelay = 0;
        int classInRow = 0;
        int[] timeArray = new int[8];
        int j = 0;
        for (int i =0; i < schedule.size(); i++) {
            if (prof == schedule.get(i).getProfessor()) {
                // add time to time Table, sort it then use that to figure out penalty
                timeArray[j] = schedule.get(i).getTime();
                j++;
            }
        }
        Arrays.sort(timeArray);
        //loop through Array to do checks
        for (int i = 0; i < 5; i++) {
            if (timeArray[i] != 0) {
                //If there are more than 3 classes in a row without a break.
                if (timeArray[i] + 1 == timeArray[i+1]) {
                    classInRow++;
                }
                //If the professor has atleast than 3 hours of a delay between classes
               if (timeArray[i+1] - timeArray[i] >= 3) {
                   hoursDelay = (timeArray[i+1] - timeArray[i]) - 2;
               } 
            }
        }
        classInRow = classInRow - 2;
        if (classInRow >= 0) {
            classInRow += 1;
        }
        if (classInRow < 0) {
            classInRow = 0;
        } 
        penalty = (classInRow * 10) + (hoursDelay * 15);
        return penalty;
    }
    
    public static void printSchedule(ArrayList<Course> schedule, ArrayList<Professors> prof, ArrayList<Period> time, ArrayList<Room> room){
        System.out.printf("%-6s %-8s %-12s %-15s %-12s %-8s %-18s", "CRN", "Course", "Professor", "Need MultiMedia", "Size", "Time","Room");
        System.out.println();
        for (int i = 0; i < schedule.size(); i++){
            System.out.format("%-6d %-8s %-12s %-15s %-12s %-8s %-18s", schedule.get(i).getCourseRoomNumber(), schedule.get(i).getCourse(), prof.get(schedule.get(i).getProfessor()).getProfessorName(), schedule.get(i).isMedia(), schedule.get(i).getClassSize(), time.get(schedule.get(i).getTime()).getTime(),  room.get(schedule.get(i).getRoomID()).getRoomName());
            System.out.println();
        }
    }
    
//GETTERS AND SETTERS BEYOND THIS POINT-------------------------------------------------------------------
    
        public static class Course {
        int CRN;
        String course;
        int professor;
        int size;
        boolean media;
        int time;
        int roomID;
        int fitness;

        public int getTime() {
            return time;
        }

        public void setClassTime(int time) {
            this.time = time;
        }

        public int getRoomID() {
            return roomID;
        }

        public void setRoomID(int roomID) {
            this.roomID = roomID;
        }


        public int getFitness() {
            return fitness;
        }

        public void setFitness(int fitness) {
            this.fitness = fitness;
        }

        public int getProfessor() {
            return professor;
        }

        public void setProfessor(int professor) {
            this.professor = professor;
        }


        public int getCourseRoomNumber() {
            return CRN;
        }

        public void setCourseRoomNumber(int CRN) {
            this.CRN = CRN;
        }

        public String getCourse() {
            return course;
        }

        public void setCourse(String course) {
            this.course = course;
        }

        public int getClassSize() {
            return size;
        }

        public void setClassSize(int size) {
            this.size = size;
        }

        public boolean isMedia() {
            return media;
        }

        public void needMedia(boolean media) {
            this.media = media;
        }
        
    }
    
    public static class Period {
        //don't need days since they are all
        //MWF
        int periodID;
        int time;

        public int getPeriodID() {
            return periodID;
        }

        public void setPeriodID(int periodID) {
            this.periodID = periodID;
        }

        public int getTime() {
            return time;
        }

        public void setClassTime(int time) {
            this.time = time;
        }
        
    }
    
    public static class Room {
        String roomName;
        int roomID;
        int size;
        boolean media;

        public String getRoomName() {
            return roomName;
        }

        public void setRoomName(String room) {
            this.roomName = room;
        }

        public int getRoomID() {
            return roomID;
        }

        public void setRoomID(int roomID) {
            this.roomID = roomID;
        }


        public int getRoomCapacity() {
            return size;
        }

        public void setRoomCapacity(int size) {
            this.size = size;
        }

        public boolean isMedia() {
            return media;
        }

        public void hasMedia(boolean media) {
            this.media = media;
        }
        
    }
    
    public static class Professors {
        int professorID;
        String professorName;

        public int getProfessorID() {
            return professorID;
        }

        public void setProfessorID(int professorID) {
            this.professorID = professorID;
        }

        public String getProfessorName() {
            return professorName;
        }

        public void setProfessorName(String name) {
            this.professorName = name;
        }
        
    }
}