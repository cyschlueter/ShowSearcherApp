import java.util.List;

public class ShowSearcherApp {
    public static void main(String[] args) throws Exception {
        IShowLoader loader = new ShowLoader(); //new ShowLoader();
        List<IShow> shows = loader.loadShows("C:\\UW\\SP22\\Comp Sci 400\\P1 Integration\\src\\tv_shows.csv");
        IShowSearcherBackend backend = new ShowSearcherBackend(); //new ShowSearcherBackend();
        for(IShow show : shows) backend.addShow(show);
        IShowSearcherFrontend frontend = new ShowSearcherFrontend(backend); //new ShowSearcherFrontend(backend);
        frontend.runCommandLoop();
    }
}