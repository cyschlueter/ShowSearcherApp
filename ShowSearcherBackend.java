import java.util.*;

/**
 * This class keeps track of many shows in a database comprised of two hash tables. One table hashes shows by title words
 * and the other hashes shows by the year of their release.
 *
 */
public class ShowSearcherBackend implements IShowSearcherBackend {

	public IHashTableSortedSets<String, IShow> titleTable;
	public IHashTableSortedSets<Integer, IShow> yearTable;
	private boolean[] filters;
	private int numShows;
	
	/**
	 * Constructor for the ShowSearcherBackend class. Creates two hash tables for storing shows, and sets an array of
	 * boolean filters to true. Also sets numShows to zero.
	 */
	public ShowSearcherBackend() {
		titleTable = new HashTableSortedSets<>();
		yearTable = new HashTableSortedSets<>();
		filters = new boolean[4];
		
		//all filters initially set to true so that if you call a search method, all providers will show up
		for (int i=0; i < 4; ++i) {
			filters[i] = true;
		}
		numShows = 0;
	}
	
	public ShowSearcherBackend(boolean b) {
		titleTable = new HashTableSortedSets<String, IShow>();
		yearTable = new HashTableSortedSets<Integer, IShow>();
		filters = new boolean[4];
		
		//set all filters initially to true
		for (int i=0; i < 4; ++i) {
			filters[i] = true;
		}
		numShows = 0;
		
	}
	
	/**
	 * This method adds Shows to the lists of shows being maintained in the two hash tables. It hashes the show to the 
	 * titleTable using every word in its title independently as a key. Also hashes the show into the yearsTable using 
	 * its release year as a key
	 */
	@Override
	public void addShow(IShow show) {
		
		//add show to a list in the title hash table for every different word in the title
		String title = show.getTitle().toLowerCase();
		Scanner scnr = new Scanner(title);
		while (scnr.hasNext()) {
			String titleWord = scnr.next().trim();
			titleTable.add(titleWord, show);
		} 
		scnr.close();
		
		//add show to the years table
		yearTable.add(show.getYear(), show);
		++numShows;
	}

	/**
	 * Returns the actual number of shows in the table, not the number of lists or the number of instances of all shows
	 */
	@Override
	public int getNumberOfShows() {
		//could have also returned the size of yearsTable but it shouldn't matter
		return numShows;
	}

	/**
	 * This method sets a single specified provider filter to either true or false
	 */
	@Override
	public void setProviderFilter(String provider, boolean filter) {
		if (provider == null) {
			return;
		}
		provider = provider.toLowerCase();
		
		//determine which boolean to set based off the string parameter
		if (provider.contains("netflix")) {
			filters[0] = filter;
		}
		if (provider.contains("hulu")) {
			filters[1] = filter;
		}
		if (provider.contains("disney+")) {
			filters[2] = filter;
		}
		if (provider.contains("prime video")) {
			filters[3] = filter;
		}
	}

	/**
	 * This method returns the boolean associated with a single specified provider filter
	 */
	@Override
	public boolean getProviderFilter(String provider) {
		
		//determine which boolean from the filters array to return based on the provider parameter
		if (provider == null) {
			return false;
		}
		provider = provider.toLowerCase();
		
		if (provider.equals("netflix")) {
			return filters[0];
		}
		if (provider.equals("hulu")) {
			return filters[1];
		}
		if (provider.equals("disney+")) {
			return filters[2];
		}
		if (provider.equals("prime video")) {
		    return filters[3];
		}
		return false;
	}

	/**
	 * This method takes a specified provider and switches its filter from on-to-off or off-to-on depending on 
	 * the current state of the filter
	 */
	@Override
	public void toggleProviderFilter(String provider) {
		//null check
		if (provider == null) {
			return;
		}
		//accept provider strings of all cases
		provider = provider.toLowerCase();
		
		if (provider.equals("netflix")) {
			filters[0] = !filters[0];
		}
		if (provider.equals("hulu")) {
			filters[1] = !filters[1];
		}
		if (provider.equals("disney+")) {
			filters[2] = !filters[2];
		}
		if (provider.equals("prime video")) {
			filters[3] = !filters[3];
		}
	}

	/**
	 * This method returns a list of all shows in the database that have the specified word in their title. It filters 
	 * out all shows from providers currently being filtered, and organizes the remaining shows from high to low 
	 * according to rating
	 */
	@Override
	public List<IShow> searchByTitleWord(String word) {
	//use get() method to obtain full list of shows with word in their title
	List<IShow> titleList = (List<IShow>) titleTable.get(word);
	
	//remove all elements whose provider filter is false
	if (filters[0] == false) {
	  for (int i=0; i < titleList.size(); ++i) {
		  if (titleList.get(i).isAvailableOn("netflix")) {
			 titleList.remove(i);
			 --i;
		  }
	}
	}
	
	if (filters[1] == false) {
		for (int i=0; i < titleList.size(); ++i) {
			  if (titleList.get(i).isAvailableOn("hulu")) {
				 titleList.remove(i);
				 --i;
			  }
		}
	}
	
	if (filters[2] == false) {
		for (int i=0; i < titleList.size(); ++i) {
			  if (titleList.get(i).isAvailableOn("disney+")) {
				 titleList.remove(i);
				 --i;
			  }
		}
	}
	if (filters[3] == false) {
		for (int i=0; i < titleList.size(); ++i) {
			  if (titleList.get(i).isAvailableOn("prime video")) {
				 titleList.remove(i);
				 --i;
			  }
		}
	}
	
	//sort the list by rating - highest ratings first
	for (int i=0; i < titleList.size() - 1; ++i) {
		int highestRating = titleList.get(i).getRating();
		for (int j=i+1; j < titleList.size(); ++j) {
			if (titleList.get(j).getRating() > highestRating) {
				IShow temp = titleList.get(i);
				titleList.set(i, titleList.get(j));
				titleList.set(j,  temp);
			}
		}
	}
	
	//selection sort the list by rating - highest ratings first
	int jIndex;
	for (int i=0; i < titleList.size() -1; ++i) {
		IShow highestRated = titleList.get(i);
		jIndex = i;
		for (int j=i+1; j < titleList.size(); ++j) {
			if (titleList.get(j).getRating() > highestRated.getRating()) {
				highestRated = titleList.get(j);
				jIndex = j;
			}
		}
		IShow temp = titleList.get(i);
		titleList.set(i,  highestRated);
		titleList.set(jIndex, temp);
	}
	
	
	    //return filters list of shows
		return titleList;
	}

	/**
	 * This method returns a list of all shows in the database that were released in the specified year. It filters out all
	 * shows whose providers are currently being filtered, and organizes the remaining shows from high to low according to
	 * rating.
	 */
	@Override
	public List<IShow> searchByYear(int year) {
		//use get() method to obtain list of all shows made in specified year
		ArrayList<IShow> yearsList = (ArrayList<IShow>) yearTable.get(year);
		
		//use filters to get rid of all shows on services currently being filtered
		if (filters[0] == false) {
			  for (int i=0; i < yearsList.size(); ++i) {
				  if (yearsList.get(i).isAvailableOn("netflix")) {
					 yearsList.remove(i);
					 --i;
				  }
			}
			}
			
			if (filters[1] == false) {
				for (int i=0; i < yearsList.size(); ++i) {
					  if (yearsList.get(i).isAvailableOn("hulu")) {
						 yearsList.remove(i);
						 --i;
					  }
				}
			}
			
			if (filters[2] == false) {
				for (int i=0; i < yearsList.size(); ++i) {
					  if (yearsList.get(i).isAvailableOn("disney+")) {
						 yearsList.remove(i);
						 --i;
					  }
				}
			}
			if (filters[3] == false) {
				for (int i=0; i < yearsList.size(); ++i) {
					  if (yearsList.get(i).isAvailableOn("prime video")) {
						 yearsList.remove(i);
						 --i;
					  }
				}
			}
			
		//use selection sort to put higher rated shows first
			int jIndex;
			for (int i=0; i < yearsList.size() -1; ++i) {
				IShow highestRated = yearsList.get(i);
				jIndex = i;
				for (int j=i+1; j < yearsList.size(); ++j) {
					if (yearsList.get(j).getRating() > highestRated.getRating()) {
						highestRated = yearsList.get(j);
						jIndex = j;
					}
				}
				IShow temp = yearsList.get(i);
				yearsList.set(i,  highestRated);
				yearsList.set(jIndex, temp);
			}
		
		//return filtered list
		return yearsList;
	}


}
