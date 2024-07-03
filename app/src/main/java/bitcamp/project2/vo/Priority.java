package bitcamp.project2.vo;

public enum Priority {
    HIGH(9),
    MIDDLE(5),
    LOW(1);

    private final int priority;

    Priority(int priority) {
        this.priority = priority;
    }

    public String getGrade() {
        switch (priority) {
            case 9:
                return "상";
            case 5:
                return "중";
            case 1:
                return "하";
            default:
                return "중";
        }
    }

    // int priority를 반환하는 메서드
    public int getName() {
        return priority;
    }

}
