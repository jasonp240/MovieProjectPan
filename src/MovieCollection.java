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
//            } else if (menuOption.equals("c")) {
//                searchCast();
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
            if (movieList.get(i).getTitle().contains(titleUser)) {
                titleList.add(movieList.get(i));
            }
        }
        selectionSortWordList(titleList);
        System.out.println(titleList);
        for (int i = 0; i < titleList.size(); i++) {
                System.out.println( i + 1 + ". " + titleList.get(i).getTitle());
        }
        System.out.println("Which movie would you like to learn more about?");
        System.out.print();

    }

    private static void selectionSortWordList(ArrayList<Movie> words) {
        int counter = 0;
        for (int i = 0; i < words.size(); i++) {
            String min = words.get(i).getTitle();
            int minIDX = i;
            for (int j = i; j < words.size(); j++) {
                counter++;
                if (words.get(j).getTitle().compareTo(min) < 0) {
                    min = words.get(j).getTitle();
                    minIDX = j;
                }
            }
            words.set(minIDX, words.get(i));
            words.set(i, words.get(minIDX));
        }
        System.out.println(counter);
    }

}
