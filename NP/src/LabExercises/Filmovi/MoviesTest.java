package LabExercises.Filmovi;

import java.util.*;
import java.util.stream.Collectors;


class Movie {
    private String name;
    private List<Integer> ratings;  // 1 - 10

    static int maxNumRatings = 0;

    public Movie(String name, List<Integer> ratings) {
        this.name = name;
        this.ratings = ratings;
        if (ratings.size() > maxNumRatings) {
            Movie.maxNumRatings = ratings.size();
        }
    }


    public String getName() {
        return name;
    }

    public List<Integer> getRatings() {
        return ratings;
    }

    public double getAvgRating() {
        return ratings.stream().mapToDouble(ratings -> ratings).average().orElse(0);
    }

    public double getRatingCoef() {
        if (Movie.maxNumRatings == 0) return 0;
        return getAvgRating() * ratings.size() / (double)Movie.maxNumRatings;
    }

    @Override
    public String toString() {
        return String.format("%s (%.2f) of %d ratings", name, getAvgRating(), ratings.size());
    }

}

class MoviesList {
    private List<Movie> movies;


    public MoviesList() {
        movies = new ArrayList<>();
    }

    public void addMovie(String title, int[] ratings) {
        movies.add(new Movie(title, Arrays.stream(ratings)
                .mapToObj(Integer::valueOf)
                .collect(Collectors.toList())));
    }

    public List<Movie> top10ByAvgRating() {
        return movies.stream().sorted(Comparator.comparing(Movie::getAvgRating).reversed().thenComparing(Movie::getName))
                      //  .thenComparing(m -> m.getRatings().size()))
                .limit(10)
                .collect(Collectors.toList());
    }


    public List<Movie> top10ByRatingCoef() {
        return movies.stream().sorted(Comparator.comparing(Movie::getRatingCoef).reversed().thenComparing(Movie::getName))
                .limit(10)
                .collect(Collectors.toList());
    }
}


public class MoviesTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MoviesList moviesList = new MoviesList();
        int n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; ++i) {
            String title = scanner.nextLine();
            int x = scanner.nextInt();
            int[] ratings = new int[x];
            for (int j = 0; j < x; ++j) {
                ratings[j] = scanner.nextInt();
            }
            scanner.nextLine();
            moviesList.addMovie(title, ratings);
        }
        scanner.close();
        List<Movie> movies = moviesList.top10ByAvgRating();
        System.out.println("=== TOP 10 BY AVERAGE RATING ===");
        for (Movie movie : movies) {
            System.out.println(movie);
        }
        movies = moviesList.top10ByRatingCoef();
        System.out.println("=== TOP 10 BY RATING COEFFICIENT ===");
        for (Movie movie : movies) {
            System.out.println(movie);
        }
    }
}

// vashiot kod ovde