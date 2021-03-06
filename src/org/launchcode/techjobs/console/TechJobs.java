package org.launchcode.techjobs.console;

import java.util.*;

/**
 * Created by LaunchCode
 */
public class TechJobs {

    private static Scanner in = new Scanner(System.in);

    public static void main (String[] args) {

        // Initialize our field map with key/name pairs
        HashMap<String, String> columnChoices = new HashMap<>();
        columnChoices.put("core competency", "Skill");
        columnChoices.put("employer", "Employer");
        columnChoices.put("location", "Location");
        columnChoices.put("position type", "Position Type");
        columnChoices.put("all", "All");

        // Top-level menu options
        HashMap<String, String> actionChoices = new HashMap<>();
        actionChoices.put("search", "Search");
        actionChoices.put("list", "List");
        System.out.println("Welcome to LaunchCode's TechJobs App!");

        ArrayList<String> sorted = new ArrayList<>();

        // Allow the user to search until they manually quit
        while (true) {
            String actionChoice = getUserSelection("View jobs by:", actionChoices);
            if (actionChoice.equals("list")) {
                String columnChoice = getUserSelection("List", columnChoices);
                if (columnChoice.equals("all")) {
                    printJobs(JobData.findAll());
                } else {
                    ArrayList<String> results = JobData.findAll(columnChoice);
                    System.out.println("\n*** All " + columnChoices.get(columnChoice) + " Values ***");
                    // Print list of skills, employers, etc
                    // Collections.sort(results);               // if sorting matters, case sensitive ie A-Z then a-z
                    results.sort(String::compareToIgnoreCase);  // case insensitive ie Aa-Zz, java 8 only
                    // TODO: could also remove duplicates when misspelled, ie JavaScript and Javascript
                    for (String item : results) {
                         System.out.println(item);
                    }
                }
            } else { // choice is "search"
                // How does the user want to search (e.g. by skill or employer)
                String searchField = getUserSelection("Search by:", columnChoices);
                // What is their search term?
                System.out.println("\nSearch term: ");
                String searchTerm = in.nextLine();
                if (searchField.equals("all")) {
                    printJobs(JobData.findByValue(searchTerm));
                } else {
                    printJobs(JobData.findByKeyAndValue(searchField, searchTerm));
                }
            }
        }
    }

    // ﻿Returns the key of the selected item from the choices Dictionary
    private static String getUserSelection(String menuHeader, HashMap<String, String> choices) {
        Integer choiceIdx;
        Boolean validChoice = false;
        String[] choiceKeys = new String[choices.size()];

        // Put the choices in an ordered structure so we can
        // associate an integer with each one
        Integer i = 0;
        for (String choiceKey : choices.keySet()) {
            choiceKeys[i] = choiceKey;
            i++;
        }
        do {
            System.out.println("\n" + menuHeader);

            // Print available choices
            for (Integer j = 0; j < choiceKeys.length; j++) {
                System.out.println("" + j + " - " + choices.get(choiceKeys[j]));
            }
            choiceIdx = in.nextInt();
            in.nextLine();

            // Validate user's input
            if (choiceIdx < 0 || choiceIdx >= choiceKeys.length) {
                System.out.println("Invalid choice. Try again.");
            } else {
                validChoice = true;
            }
        } while(!validChoice);
        return choiceKeys[choiceIdx];
    }

    // Print a list of jobs
    // 1 list, 0 all. AL of HM's
    private static void printJobs(ArrayList<HashMap<String, String>> someJobs) {
        if (someJobs.size() == 0) {                                // case: 0 jobs
            System.out.println("They took r jerbs!");
        } else {                                                   // case 1: we have jerbs
            Set<String> keys = null;
            for (int i = 0; i <= someJobs.size() - 1; ++i) {       // loop jobs
                HashMap map = someJobs.get(i);                     // store current ArrayList i into a HashMap map
                keys = map.keySet();                               // create set that holds all keys (could do the same with values)
                System.out.println("*****");
                for (String key : keys) {                          // nested loop
                    System.out.println(key + ": " + map.get(key)); // print key and value by way of nested loop
                }
            }
            System.out.println("*****");
        }
    }
}

