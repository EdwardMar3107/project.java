package Song;

public enum Genre {
    ROCK("🎸"), POP("🎤"), RAP("🎧"), JAZZ("🎷");

    private final String emoji;

    Genre (String emoji) {
        this.emoji = emoji;
    }

    public String getEmoji() {
        return emoji;
    }
}
