package TheoreticalFoundation;

import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class DFA {
    private Set<String> alphabet; // A set of symbols that the DFA can recognize.
    private Set<String> states; //A set of all possible states in the DFA.
    private String startState; //The state where the DFA begins processing input.
    private Set<String> acceptStates; // A set of states that are considered accepting (or final) states
    private Map<String, Map<String, String>> transitions; //A map representing the state transition function, where each state maps to another map of input symbols to next states.

    public DFA(Set<String> alphabet, Set<String> states, String startState, Set<String> acceptStates) {
        this.alphabet = alphabet;
        this.states = states;
        this.startState = startState;
        this.acceptStates = acceptStates;
        this.transitions = new HashMap<>();
    }

    //  This method allows adding a transition to the DFA.
    public void addTransition(String currentState, String inputSymbol, String nextState) { //It checks if the current state, next state, and input symbol are valid (exist in their respective sets).
        if (!states.contains(currentState) || !states.contains(nextState) || !alphabet.contains(inputSymbol)) {
            throw new IllegalArgumentException("Invalid transition.");
        }
        transitions.computeIfAbsent(currentState, k -> new HashMap<>()).put(inputSymbol, nextState);//If valid, it adds the transition from currentState to nextState on inputSymbol.
    }

    public boolean accepts(String input) {
        String currentState = startState;
        for (char symbol : input.toCharArray()) { //Checks if the symbol is in the alphabet.
            String inputSymbol = String.valueOf(symbol);
            if (!alphabet.contains(inputSymbol)) {
                throw new IllegalArgumentException("Input contains symbols not in the alphabet.");
            }
            currentState = transitions.getOrDefault(currentState, Collections.emptyMap()).get(inputSymbol);
            if (currentState == null) {
                return false; // If the next state is null, reject the input.
            }
        }
        return acceptStates.contains(currentState); // Check if we end in an accept state.
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String inputFile;

        // Check if a command line argument is provided
        if (args.length > 0) {
            inputFile = args[0];
        } else {
            // Prompt the user to enter the file path
            System.out.print("Please provide a DFA description file path: ");
            inputFile = scanner.nextLine(); // Get user input for the file path
        }
        //It reads the first four lines for the alphabet, states, start state, and accept states, using parseSet to convert the lines into appropriate data structures.
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            Set<String> alphabet = parseSet(reader.readLine());
            Set<String> states = parseSet(reader.readLine());
            String startState = reader.readLine().trim();
            Set<String> acceptStates = parseSet(reader.readLine());

            DFA dfa = new DFA(alphabet, states, startState, acceptStates);
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("->");
                String[] transitionParts = parts[0].substring(1, parts[0].length() - 1).split(",");
                String currentState = transitionParts[0].trim();
                String inputSymbol = transitionParts[1].trim();
                String nextState = parts[1].trim();

                // Debug: Print the transition being added
                System.out.println("Adding transition: (" + currentState + ", " + inputSymbol + ") -> " + nextState);
                dfa.addTransition(currentState, inputSymbol, nextState);
            }

            // Interactive string testing loop
            //This part reads the transition lines and splits them to extract the current state, input symbol, and next state.
            //It calls addTransition to add each transition to the DFA.
            while (true) {
                System.out.print("Enter a string to test (or 'exit' to quit): ");
                String inputString = scanner.nextLine();

                if (inputString.equals("exit")) {
                    break;
                }

                boolean accepted = dfa.accepts(inputString);
                System.out.println("String " + (accepted ? "accepted" : "rejected") + ".");
            }

        } catch (IOException e) {
            System.out.println("Error reading the input file: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid DFA description: " + e.getMessage());
        } finally {
            scanner.close(); // Close the scanner
        }
    }

    private static Set<String> parseSet(String input) {
        String[] elements = input.substring(1, input.length() - 1).split(",");
        return new HashSet<>(Arrays.asList(elements));
    }
}
