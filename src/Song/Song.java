package Song;

import java.util.Objects;

public class Song {

    private String title;
    private String Artist;
    private Genre genre;

    public Song (String title, String Artist, Genre genre) {
        this.title = title;
        this.Artist = Artist;
        this.genre = genre;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return Artist;
    }

    public Genre getGenre() {
        return genre;
    }

    @Override
    public String toString() {
        return "Название песни: " + getTitle() + " - Исполнитель: " + getArtist() + " - Жанр: " + getGenre() + " " + getGenre().getEmoji();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Song)) return false;
        Song other = (Song) obj;
        return getTitle().equals(other.getTitle()) && getGenre().equals(other.getGenre());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle(), getGenre());
    }
}
