package Movie;

import java.util.Objects;

public class Movie {
    String title;
    Director director;
    double rating;

    public Movie (String title, Director director, double rating) {
        this.title = title;
        this.director = director;
        this.rating = rating;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDirector(Director director) {
        this.director = director;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public Director getDirector() {
        return director;
    }

    public double getRating() {
        return rating;
    }

    @Override
    public String toString() {
        return "🎬 " + getTitle() + " — реж. " + getDirector() + " — рейтинг: ⭐ " + getRating();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if(!(obj instanceof Movie)) return false;
        Movie other = (Movie) obj;
        return getTitle().equals(other.getTitle()) && getDirector().equals(other.getDirector()) && Math.abs(this.rating - other.rating) < 0.0001;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle(), getDirector(), getRating());
    }
}
