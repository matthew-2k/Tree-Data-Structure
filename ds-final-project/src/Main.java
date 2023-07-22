import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

class Trie
{
    private static Trie.TrieNode root;

    public Trie() {
        root = new Trie.TrieNode(true); // root can represent an empty string
    }

    /**
     * Inserts a word into the trie.
     */
    public static void insert(String word) {
        if (word == null || word.length() == 0) {
            return;
        }

        Trie.TrieNode parent = root;
        for (int i = 0; i < word.length(); i++) {
            char cur = word.charAt(i);

            Trie.TrieNode child = parent.children.get(cur); // Check if having a TrieNode associated with 'cur'
            if (child == null) {
                child = new Trie.TrieNode(false);
                parent.children.put(cur, child);
            }

            parent = child; // Navigate to next level
        }

        parent.isEndOfWord = true;
    }

    /**
     * Returns true if the word is in the trie.
     */
    public static boolean search(String word) {
        if (word == null) { // Assume that empty string is in the trie
            return false;
        }

        Trie.TrieNode parent = root;
        for (int i = 0; i < word.length(); i++) {
            char value = word.charAt(i);

            Trie.TrieNode child = parent.children.get(value); // Check if having a TrieNode associated with 'value'
            if (child == null) { // null if 'word' is way too long or its prefix doesn't appear in the Trie
                return false;
            }

            parent = child; // Navigate to next level
        }

        return parent.isEndOfWord;
    }

    /** Returns true if there is any word in the trie that starts with the given prefix. */
    public static boolean startsWith(String prefix) { // Assume that empty string is a valid prefix
        if (prefix == null) {
            return false;
        }

        TrieNode parent = root;
        for (int i = 0; i < prefix.length(); i++) {
            char value = prefix.charAt(i);

            TrieNode child = parent.children.get(value); // Check if having a TrieNode associated with 'value'
            if (child == null) { // null if 'prefix' is way too long or its prefix doesn't appear in the Trie
                return false;
            }

            parent = child; // Navigate to next level
        }

        return true;
    }

    /**
     * Deletes a word from the trie if present, and return true if the word is deleted successfully.
     */
    public static boolean delete(String word) {
        if (word == null || word.length() == 0) {
            return false;
        }

        // All nodes below 'deleteBelow' and on the path starting with 'deleteChar' (including itself) will be deleted if needed
        Trie.TrieNode deleteBelow = null;
        char deleteChar = '\0';

        // Search to ensure word is present
        Trie.TrieNode parent = root;
        for (int i = 0; i < word.length(); i++) {
            char value = word.charAt(i);

            Trie.TrieNode child = parent.children.get(value); // Check if having a TrieNode associated with 'value'
            if (child == null) { // null if 'word' is way too long or its prefix doesn't appear in the Trie
                return false;
            }

            if (parent.children.size() > 1 || parent.isEndOfWord) { // Update 'deleteBelow' and 'deleteChar'
                deleteBelow = parent;
                deleteChar = value;
            }

            parent = child;
        }

        if (!parent.isEndOfWord) { // word isn't in trie
            return false;
        }

        if (parent.children.isEmpty()) {
            deleteBelow.children.remove(deleteChar);
        } else {
            parent.isEndOfWord = false; // Delete word by mark it as not the end of a word
        }

        return true;
    }

    private static class TrieNode {
        boolean isEndOfWord;
        Map<Character, Trie.TrieNode> children;

        TrieNode(boolean isEndOfWord) {
            this.isEndOfWord = isEndOfWord;
            this.children = new HashMap<>();
        }
    }





    public static Scanner scanner = new Scanner(System.in);
    public static Map<String,Student> map = new HashMap<String, Student>();

    public static void main (String[] args)
    {
        root = new TrieNode(true); //define trie tree
        boolean quit = false; //exit

        while (!quit) { //switch case for input
            menu();
            System.out.println("Enter your choice : ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 0:
                    System.out.println("bye");
                    quit = true;
                    break;
                case 1:
                    addNewStudent();
                    break;
                case 2:
                    searchStudent();
                    break;
                case 3:
                    updateStudent();
                    break;
                case 4:
                    removeStudent();
                    break;
            }
        }
    }
    //method to add new student
    private static void addNewStudent() {
        System.out.println("enter student number:");
        String number = scanner.nextLine();
        if(search(number)) //search student
        {
            System.out.println("can't add , student with number: " + number + "  alreay exist!");
            return;
        }
        else { //get student information
            System.out.println("enter student name:");
            String name = scanner.nextLine();
            System.out.println("enter student field of study:");
            String Discipline = scanner.nextLine();
            System.out.println("enter student GPA:");
            String GPA = scanner.nextLine();
            Student newStudent = new Student(name, number, Discipline, GPA);
            map.put(newStudent.student_number, newStudent);
            insert(number);
            System.out.println("student added successfully!");
        }
    }
    //method to search new student
    private static void searchStudent(){
        System.out.println("enter student-number:");
        String number = scanner.nextLine();
            if (search(number)) { //search student
                System.out.println("student with number " + number + " info :");
                System.out.println("name : " + map.get(number).name);
                System.out.println("ield_of_study : " + map.get(number).field_of_study);
                System.out.println("GPA : " + map.get(number).GPA);
            }
            //autocomplite search
            else if (startsWith(number)) {//Traverse through a HashMap
                Iterator mapIterator = map.entrySet().iterator();
                while (mapIterator.hasNext()) {
                    Map.Entry mapElement = (Map.Entry) mapIterator.next();
                    if (mapElement.getKey().toString().contains(number)) {
                        System.out.println("did you mean " + mapElement.getKey().toString() + " ?? , enter <1> as yes OR <0> as no:");
                        int answer = scanner.nextInt();
                        if (answer == 1){
                            number = mapElement.getKey().toString();
                            System.out.println("student with number " + number + " info :");
                            System.out.println("name : " + map.get(number).name);
                            System.out.println("ield_of_study : " + map.get(number).field_of_study);
                            System.out.println("GPA : " + map.get(number).GPA);
                        }
                        else
                            continue;
                    }
                }
            }
            else{
                System.out.println("can't find , student with number: " + number + "  dosen't exist!");//in case student dose not exist
                }
        }
    //method to remove new student
    private static void removeStudent(){
        System.out.println("enter student-number");
        String number = scanner.nextLine();
        if(search(number)){
            map.remove(number);
            delete(number);
            System.out.println("student removed successfully!");
        }
        //autocomplite search
        else if (startsWith(number)) {
            Iterator mapIterator = map.entrySet().iterator();
            while (mapIterator.hasNext()) {//Traverse through a HashMap
                Map.Entry mapElement = (Map.Entry) mapIterator.next();
                if (mapElement.getKey().toString().contains(number)) {
                    System.out.println("did you mean " + mapElement.getKey().toString() + " ?? , enter <1> as yes OR <0> as no:");
                    int answer = scanner.nextInt();
                    if (answer == 1){
                        number = mapElement.getKey().toString();
                        map.remove(number);
                        delete(number);
                        System.out.println("student removed successfully!");
                    }
                    else
                        continue;
                }
            }
        }
        else {
            System.out.println("can't find , student with number: " + number + "  dosen't exist!");//in case student dose not exist
        }
    }
    //method to update student that already is in our hashmap and trie tree
    private static void updateStudent() {
        System.out.println("enter student number :");
        String number = scanner.nextLine();
        if(search(number)){
            System.out.println("enter new name:");
            String newname = scanner.nextLine();
            System.out.println("enter new field of study:");
            String newDiscipline = scanner.nextLine();
            System.out.println("enter new GPA");
            String newGPA = scanner.nextLine();
            Student newStudent = new Student(newname,number,newDiscipline,newGPA);
            map.replace(number,newStudent);
            System.out.println("student info updated successfully!");
        }
        //autocomplite search
        else if (startsWith(number)) {
            Iterator mapIterator = map.entrySet().iterator();
            while (mapIterator.hasNext()) {  //Traverse through a HashMap
                Map.Entry mapElement = (Map.Entry) mapIterator.next();
                if (mapElement.getKey().toString().contains(number)) {
                    System.out.println("did you mean " + mapElement.getKey().toString() + " ?? , enter <1> as yes OR <0> as no:");
                    int answer = scanner.nextInt();
                    if (answer == 1){
                        number = mapElement.getKey().toString();
                        String nulll = scanner.nextLine();
                        System.out.println("enter new name:");
                        String newname = scanner.nextLine();
                        System.out.println("enter new field of study:");
                        String newDiscipline = scanner.nextLine();
                        System.out.println("enter new GPA");
                        String newGPA = scanner.nextLine();
                        Student newStudent = new Student(newname,number,newDiscipline,newGPA);
                        map.replace(number,newStudent);
                        System.out.println("student info updated successfully!");
                    }
                    else
                        continue;
                }
            }
        }
        else{
            System.out.println("can't find , student with number: " + number + "  dosen't exist!"); //in case student dose not exist
        }
    }


    public static void menu(){ //main menu , shows everytime
        System.out.println("\n press one : ");
        System.out.println("0 - to quit the application.");
        System.out.println("1 - to add new student");
        System.out.println("2 - search student by student-number");
        System.out.println("3 - to update existing student");
        System.out.println("4 - remove student");
    }
}
