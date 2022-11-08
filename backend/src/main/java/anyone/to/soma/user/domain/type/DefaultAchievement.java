package anyone.to.soma.user.domain.type;

public enum DefaultAchievement {

    LEVEL_ONE("To. Anyone에 어서오세요.", "To. Anyone에 가입합니다.", 500, 1),
    LEVEL_TWO("마음이 담긴 편지", "편지를 작성합니다.", 300, 2),
    LEVEL_THREE("어떤 편지가 왔을까?", "어떤 편지가 왔을까?", 100, 3),
    LEVEL_FOUR("소중한 편지 Lv. 1", "상대방이 내 편지에 좋아요를 남깁니다.", 100, 4),
    LEVEL_FIVE("소중한 편지 Lv. 2", "상대방이 내 편지에 좋아요를 남깁니다.", 500, 5),
    LEVEL_SIX("소중한 편지 Lv. 3", "상대방이 내 편지에 좋아요를 남깁니다.", 1000, 6);

    private final String name;
    private final String tag;
    private final int point;
    private final int level;


    DefaultAchievement(String name, String tag, int point, int level) {
        this.name = name;
        this.tag = tag;
        this.point = point;
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public String getTag() {
        return tag;
    }

    public int getPoint() {
        return point;
    }

    public int getLevel() {
        return level;
    }
}
