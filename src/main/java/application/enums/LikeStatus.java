package application.enums;

public enum LikeStatus {
    LIKED(1),
    NOT_LIKED(0);
    private int value;

    LikeStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}