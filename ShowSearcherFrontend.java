// --== CS400 Project One File Header ==--
// Name: <Dennis Leung>
// CSL Username: <dleung>
// Email: <dhleung@wisc.edu>
// Lecture #: <002 @1:00pm>
// Notes to Grader: <None>

import java.util.List;
import java.util.Scanner;

/**
 * This method drives the entire read, eval, print loop (repl) for the
 * Song Search App.  This loop will continue to run until the user
 * explicitly enters the quit command.
 * @author dhleung
 */
public class ShowSearcherFrontend implements IShowSearcherFrontend{



    /**
     * The welcome message final
     */
    private final String welcomeMsg =
            "Welcome to the Show Searcher App!" +
                    "\n" +
                    "=================================";

    /**
     * The command message of the main menu final
     */
    private final String commandMenuMsg =
            "Command Menu:" + "\n" +
                    "\t1) Search by [T]itle Word" + "\n" +
                    "\t2) Search by [Y]ear First Produced" + "\n" +
                    "\t3) [F]ilter by Streaming Provider" + "\n" +
                    "\t4) [Q]uit" + "\n" +
                    "Choose a command from the menu above: ";

    /**
     * private backend obj
     */
    private IShowSearcherBackend backend;

    /**
     * the string storing the input of the user from scanner
     */
    String input;
    /**
     * the scanner that reads user input from System.in
     */
    Scanner cin;

    /**
     * Constructor that takes in a backend obj and assigns to the private backend field
     * and also creating a new scanner, storing it in the private cin field
     * @param backend
     */
    public ShowSearcherFrontend(IShowSearcherBackend backend){
        this.backend = backend;
        this.cin = new Scanner(System.in);
    }

    /**
     *
     * Constructor that takes in a backend obj and assigns to the private backend field
     * and also creating a new scanner, storing it in the private cin field
     * @param input specified input
     * @param backend
     */
    public ShowSearcherFrontend(String input, ShowSearcherBackend backend){
        this(backend);
        this.input = input;
    }


    /**
     * run the main cycle of the machine
     */
    @Override
    public void runCommandLoop() {
        System.out.print(this.welcomeMsg);
        System.out.println(this.backend.getNumberOfShows());
        // infinite loop that only breaks if q or 4 is given as an input, calls helper methods to implement other
        // behavior otherwise
        while(true){
            displayCommandMenu();
            // puts the input to lowercase always
            this.input = cin.next().toLowerCase();
            // check cases and quit by breaking out of the infinite while loop
            if (input.equals("t") || input.equals("1")){
                titleSearch();
            }
            else if (input.equals("y") || input.equals("2")){
                yearSearch();
            }
            else if (input.equals("f") || input.equals("3")){
                toggleProvider();
            }
            else if (input.equals("q") || input.equals("4")){
                this.cin.close();
                System.out.println("Good Bye!");
                break;
            }
        }
    }

    /**
     * prints out the final command menu string
     */
    @Override
    public void displayCommandMenu() {
        System.out.println(this.commandMenuMsg);
    }

    /**
     * builds and print out the show output
     * @param shows
     */
    @Override
    public void displayShows(List<IShow> shows) {
        // matches by calling the backend
        System.out.println("Found " + shows.size() + "/" + this.backend.getNumberOfShows() + " matches.");
        // counter to keep count of the shows printed
        int counter = 1;
        // enchanced for loop to iterate over the list of shows
        // only add the provider if the user has toggled on the provider field
        // example: A show both on hulu and netflix will only have hulu printed out if netflix is filtered
        for(IShow show : shows){
            String msg =
                    counter + ". " + show.getTitle() + "\n" +
                            "\t" + show.getRating() + "/100 " + "(" + show.getYear() + ") " + "on: ";
            if (show.isAvailableOn("Netflix") && this.backend.getProviderFilter("netflix")) {
                msg += "Netflix ";
            }
            if (show.isAvailableOn("Hulu") && this.backend.getProviderFilter("hulu")){
                msg += "Hulu ";
            }
            if (show.isAvailableOn("Prime Video") && this.backend.getProviderFilter("prime video")){
                msg += "Prime Video ";
            }
            if (show.isAvailableOn("Disney+") && this.backend.getProviderFilter("disney+")){
                msg += "Disney+ ";
            }
            System.out.println(msg);
            // iterate over to keep count
            counter++;
        }
    }

    /**
     * implements the titleSearch option
     * accepts another input from user and calls backend with the input
     * IMPORTANT: to lower case by default
     */
    @Override
    public void titleSearch() {
        System.out.print("Choose a word that you would like to search for: ");
        input = cin.next().toLowerCase();
        this.displayShows(this.backend.searchByTitleWord(input));
    }

    /**
     * implements the yearSearch option
     * accepts another input from user and calls backend with the input
     */
    @Override
    public void yearSearch() {
        System.out.print("Please input the year to search for: ");
        input = cin.next();
        this.displayShows(this.backend.searchByYear(Integer.valueOf(input)));
    }

    /**
     * lets the user to toggle on or off the providers
     */
    public void toggleProvider(){
        // infinite loop like the main menu
        while(true){
            printProvider();
            // to lower case by default
            // check both string and number input and calling backend toggle method
            this.input = this.cin.next().toLowerCase();
            if (input.equals("n") || input.equals("1")) {
                backend.toggleProviderFilter("netflix");
            } else if (input.equals("h") || input.equals("2")) {
                backend.toggleProviderFilter("hulu");
            } else if (input.equals("p") || input.equals("3")) {
                backend.toggleProviderFilter("prime video");
            } else if (input.equals("d") || input.equals("4")) {
                backend.toggleProviderFilter("disney+");
            } else if (input.equals("q") || input.equals("5")) {
                break;
            }
        }
    }

    /**
     * prints out the providers for in the toggle option
     */
    private void printProvider() {
        // checks if the providers are true or false and prints out line with/without "x"
        if (this.backend.getProviderFilter("netflix")) {
            System.out.println("\t1) _x_ [N]eflix");
        } else System.out.println("\t1) ___ [N]eflix");

        if (this.backend.getProviderFilter("hulu")) {
            System.out.println("\t2) _x_ [H]ulu");
        } else System.out.println("\t2) ___ [H]ulu");

        if (this.backend.getProviderFilter("prime video")) {
            System.out.println("\t3) _x_ [P]rime Video");
        } else System.out.println("\t3) ___ [P]rime Video");

        if (this.backend.getProviderFilter("disney+")) {
            System.out.println("\t4) _x_ [D]isney+");
        } else System.out.println("\t4) ___ [D]isney+");

        System.out.println("\t5) [Q]uit toggling provider filters");
        System.out.println("Choose the provider that you'd like to toggle the filter for: ");
    }
}