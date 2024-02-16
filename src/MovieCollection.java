import java.awt.image.AreaAveragingScaleFilter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class MovieCollection {
    private ArrayList<Movie> movieList;

    public MovieCollection() {
        movieList = new ArrayList<>();
        readData();
        printMenu();
    }

    private void readData() {
        try {
            File myFile = new File("src\\movies_data.csv");
            Scanner fileScanner = new Scanner(myFile);
            fileScanner.nextLine();
            while (fileScanner.hasNext()) {
                String data = fileScanner.nextLine();
                if (!(data.startsWith("title"))) {
                    String[] splitData = data.split(",");
                    String title = splitData[0];
                    String cast = splitData[1];
                    String director = splitData[2];
                    String overview = splitData[3];
                    int runtime = Integer.parseInt(splitData[4]);
                    double userRating = Double.parseDouble(splitData[5]);
                    movieList.add(new Movie(title, cast, director, overview, runtime, userRating));
                }
            }
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }

    private void printMenu() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Welcome to the movie collection!");
        String menuOption = "";

        while (!menuOption.equals("q")) {
            System.out.println("------------ Main Menu ----------");
            System.out.println("- search (t)itles");
            System.out.println("- search (c)ast");
            System.out.println("- (q)uit");
            System.out.print("Enter choice: ");
            menuOption = scan.nextLine();

            if (menuOption.equals("t")) {
                searchTitles();
            } else if (menuOption.equals("c")) {
                searchCast();
            } else if (menuOption.equals("q")) {
                System.out.println("Goodbye!");
            } else {
                System.out.println("Invalid choice!");
            }
        }
    }

    private void searchTitles() {
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter a title search term: ");
        String titleUser = scan.nextLine();
        ArrayList<Movie> titleList= new ArrayList<>();
        for (int i = 0; i < movieList.size(); i++) {
            if (movieList.get(i).getTitle().toLowerCase().contains(titleUser)) {
                titleList.add(movieList.get(i));
            }
        }
        if (!titleList.isEmpty()) {
            selectionSortMovieList(titleList);
            for (int i = 0; i < titleList.size(); i++) {
                System.out.println( (i + 1) + ". " + titleList.get(i).getTitle());
            }
            System.out.println("Which movie would you like to learn more about?");
            System.out.print("Enter number: ");
            int number = scan.nextInt() - 1;
            scan.nextLine();
            System.out.println();
            System.out.println("Title: " + titleList.get(number).getTitle());
            System.out.println("Runtime: " + titleList.get(number).getRuntime() + " minutes");
            System.out.println("Directed by: " + titleList.get(number).getDirector());
            System.out.println("Cast: " + titleList.get(number).getCast());
            System.out.println("Overview: " + titleList.get(number).getOverview());
            System.out.println("User rating: " + titleList.get(number).getUserRating());
        } else {
            System.out.println();
            System.out.println("No movies titles match that search term!");
        }
    }

    private static void selectionSortMovieList(ArrayList<Movie> movies) {
        for (int i = 0; i < movies.size() - 1; i++) {
            String min = movies.get(i).getTitle();
            int minIDX = i;
            for (int j = i + 1; j < movies.size(); j++) {
                if (movies.get(j).getTitle().compareTo(min) < 0) {
                    min = movies.get(j).getTitle();
                    minIDX = j;
                }
            }
            Movie temp = movies.get(i);
            movies.set(i, movies.get(minIDX));
            movies.set(minIDX, temp);
        }
    }

    public static void selectionSortWordList(ArrayList<String> words) {
        for (int i = 0; i < words.size(); i++) {
            String min = words.get(i);
            int minIDX = i;
            for (int j = i; j < words.size(); j++) {
                if (words.get(j).compareTo(min) < 0) {
                    min = words.get(j);
                    minIDX = j;
                }
            }
            words.set(minIDX, words.get(i));
            words.set(i, min);
        }
    }


    private void searchCast() {
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter a person to search for (first or last name): ");
        String personUser = scan.nextLine();
        ArrayList<String> nameList = new ArrayList<>();
        for (int i = 0; i < movieList.size(); i++) {
            if (movieList.get(i).getCast().toLowerCase().contains(personUser)) {
                String[] names = movieList.get(i).getCast().split("\\|");
                for (int j = 0; j < names.length; j++) {
                    if (names[j].toLowerCase().contains(personUser) && !nameList.contains(names[j])) {
                        nameList.add(names[j]);
                    }
                }
            }
        }
        if (!nameList.isEmpty()) {
            selectionSortWordList(nameList);
            for (int i = 0; i < nameList.size(); i++) {
                System.out.println((i + 1) + ". " + nameList.get(i));
            }
            System.out.println("Which would you like to see all movies for? ");
            System.out.print("Enter number: ");
            int numberUser = scan.nextInt();
            scan.nextLine();
            String nameSearch = nameList.get(numberUser - 1);
            ArrayList<Movie> movieList2 = new ArrayList<>();
            for (int i = 0; i < movieList.size(); i++) {
                if (movieList.get(i).getCast().contains(nameSearch)) {
                    movieList2.add(movieList.get(i));
                }
            }
            selectionSortMovieList(movieList2);
            for (int i = 0; i < movieList2.size(); i++) {
                System.out.println((i + 1) + ". " + movieList2.get(i).getTitle());
            }
            System.out.println("Which movie would you like to learn more about?");
            System.out.print("Enter number: ");
            int numberUser1 = scan.nextInt() - 1;
            System.out.println();
            System.out.println("Title: " + movieList2.get(numberUser1).getTitle());
            System.out.println("Runtime: " + movieList2.get(numberUser1).getRuntime() + " minutes");
            System.out.println("Directed by: " + movieList2.get(numberUser1).getDirector());
            System.out.println("Cast: " + movieList2.get(numberUser1).getCast());
            System.out.println("Overview: " + movieList2.get(numberUser1).getOverview());
            System.out.println("User rating: " + movieList2.get(numberUser1).getUserRating());
        } else {
            System.out.println("No results match your search");
        }
    }
}
