import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.File;

//--== CS400 Project One File Header ==--
//Name: Cinthya Nguyen
//CSL Username: cinthya
//Email: 37cnguyen@wisc.edu
//Lecture #: 002 @1:00pm
//Notes to Grader: N/A

/**
 * Loads a list of shows from a specified CSV source file.
 * The following csv columns are used to load these show attributes:
 *   - Title: the complete title for a show
 *   - Year: the year that the show was first produced
 *   - Rotten Tomatoes: the review score (out of 100) for this show
 *   - Netflix: 1 = available on this service, otherwise 0
 *   - Hulu: 1 = available on this service, other wise 0
 *   - Prime Video: 1 = available on this service, otherwise 0
 *   - Disney+: 1 = available on this service, otherwise 0
 *   
 * @author Cinthya Nguyen
 */
public class ShowLoader implements IShowLoader {

  /**
   * Loads the list of songs described within a CSV file.
   * 
   * @param filepath is relative to executable's working directory
   * @return a list of show objects that were read from specified file
   */
  @Override
  public List<IShow> loadShows(String filepath) throws FileNotFoundException {

    List<IShow> list = new ArrayList<>();

    Scanner scnr = new Scanner(new File(filepath));
    String line = scnr.nextLine();

    while (scnr.hasNextLine()) {

      line = scnr.nextLine();
      String[] showArr = line.split(","); // array of strings split around commas in current line
      ArrayList<String> show = new ArrayList<>();

      for (int i = 0; i < showArr.length; i++) {
        show.add(showArr[i]);
      }

      if (show.get(2).contains("\"")) { // if an element as a " it means that the title has commas
        titleHelper(show); // formats title correctly after being delimited by commas
      }

      String title = show.get(2);
      int year = Integer.valueOf(show.get(3));
      int rating = Integer.valueOf(show.get(6).substring(0, show.get(6).indexOf("/")));

      String providers = providerHelper(show); // create String of providers

      list.add(new Show(title, year, rating, providers));
    }

    return list;
  }

  /**
   * Helper method that makes sure the title in the list is formatted correctly after being split by
   * commas.
   * 
   * @param show show ArrayList that contains a show's info (title, rating, year, and providers)
   */
  private void titleHelper(ArrayList<String> show) {

    int count = 0;

    for (int i = 0; i < show.size(); i++) { // look for first occurrence of a quote

      if (show.get(i).contains("\"")) {

        for (int k = 0; k < show.get(i).length(); k++) {
          if (show.get(i).charAt(k) == '"') { // count up how many quotes are in the String
            count++;
          }
        }

        if (count % 2 == 1) { // if # of quotes are odd, then title is incomplete
          for (int j = i + 1; j < show.size(); j++) {
            // combine each part of the title into one string and element
            if (show.get(j).contains("\"")) {
              show.set(i, show.get(i).concat("," + show.get(j)));
              show.remove(j);
              break; // end quote has been found, break out of loop
            } else {
              show.set(i, show.get(i).concat("," + show.get(j)));
              show.remove(j);
              j--; // decrement since show's size is dynamic
            }
          }

          break; // break out of loop: title has been correctly formatted
        }
      }
    }

    if (show.get(2).contains("\"")) { // remove unnecessary quotes
      show.set(2, show.get(2).substring(1, show.get(2).length() - 1));
      show.set(2, show.get(2).replaceAll("\"\"", "\""));
    }

  }

  /**
   * Helper method to combine all providers of a show into one String.
   * 
   * @param show ArrayList that contains a show's info (title, rating, year, and providers)
   * @return String that contains all the providers of show
   */
  private String providerHelper(ArrayList<String> show) {
    String providers = "";

    for (int i = 7; i < show.size() - 1; i++) {
      if (i == 7 && show.get(i).equals("1")) {
        providers += "Netflix";
      }

      if (i == 8 && show.get(i).equals("1")) {
        providers += " Hulu";
      }

      if (i == 9 && show.get(i).equals("1")) {
        providers += " Prime Video";
      }

      if (i == 10 && show.get(i).equals("1")) {
        providers += " Disney+";
      }
    }

    return providers;
  }

}
