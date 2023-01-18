// --== CS400 Project One File Header ==--
// Name: Cinthya Nguyen
// CSL Username: cinthya
// Email: 37cnguyen@wisc.edu
// Lecture #: 002 @1:00pm
// Notes to Grader: N/A

/**
 * Represents a single streaming show object that can be stored, sorted, and searched for based on
 * these accessors below.
 * 
 */
public class Show implements IShow {

  private String title;
  private int year;
  private int rating;
  private String providers;

  /**
   * Initializes all fields.
   * 
   * @param title title of show
   * @param year year show was released
   * @param rating show's Rotten Tomatoes
   * @param providers 
   */
  public Show(String title, int year, int rating, String providers) {
    this.title = title;
    this.year = year;
    this.rating = rating;
    this.providers = providers;
  }

  /**
   * Compares the ratings of two objects.
   * 
   * @param otherShow show to compare
   */
  @Override
  public int compareTo(IShow otherShow) {
    return rating - otherShow.getRating();
  }

  /**
   * Returns title of show.
   * 
   * @return show's title
   */
  @Override
  public String getTitle() {
    return title;
  }

  /**
   * Returns year show was released.
   * 
   * @return Show's release year
   */
  @Override
  public int getYear() {
    return year;
  }

  /**
   * Returns Rotten Tomato score of the show.
   * 
   * @return show's rating
   */
  @Override
  public int getRating() {
    return rating;
  }

  /**
   * Checks if show is on specified streaming service.
   * 
   * @param streaming service
   * @return true if show is on the service, false otherwise
   */
  @Override
  public boolean isAvailableOn(String provider) {
    return providers.contains(provider);
  }

}
