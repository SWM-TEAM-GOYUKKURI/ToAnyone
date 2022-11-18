package anyone.to.soma.user.domain.type;

public enum DefaultAchievement {

    LEVEL_ONE("To. Anyone에 어서오세요.", "To. Anyone에 가입합니다.", 500L, 1),
    LEVEL_TWO("마음이 담긴 편지", "편지를 작성합니다.", 300L, 2),
    LEVEL_THREE("어떤 편지가 왔을까?", "편지를 읽습니다.", 100L, 3),
    LEVEL_FOUR("소중한 편지 Lv. 1", "상대방이 내 편지에 좋아요를 남깁니다.", 100L, 4),
    LEVEL_FIVE("소중한 편지 Lv. 2", "상대방이 내 편지에 좋아요를 남깁니다.", 500L, 5),
    LEVEL_SIX("소중한 편지 Lv. 3", "상대방이 내 편지에 좋아요를 남깁니다.", 1000L, 6),
    LEVEL_SEVEN("따뜻한 마음", "상대방이 내 편지에 좋아요를 남깁니다.", 3000L, 7),
    LEVEL_EIGHT("내 이야기도 들려줄게요.", "받은 편지함에서 답장을 보냅니다.", 300L, 8),
    LEVEL_NINE("정이 많은 사람", "받은 편지에 답장을 누적 3번 작성해요.", 800L, 9),
    LEVEL_TEN("정이 넘치는 사람", "받은 편지에 답장을 누적 10번 작성해요.", 1500L, 10),
    LEVEL_ELEVEN("첫 쇼핑", "처음으로 꾸미기 아이템을 얻어요.", 100L, 11),
    LEVEL_TWELVE("프로 쇼퍼 Lv. 1", "꾸미기 아이템을 5개 얻어요.", 300L, 12),
    LEVEL_THIRTEEN("프로 쇼퍼 Lv. 2", "꾸미기 아이템을 15개 얻어요.", 500L, 13),
    LEVEL_FOURTEEN("내가 그린 편지 그림은 잘 그린 편지 그림이다", "꾸미기 아이템을 30개 얻어요.", 1000L, 14),
    LEVEL_FIFTEEN("나만의 우체통", "처음으로 편지를 받아요.", 200L, 15),
    LEVEL_SIXTEEN("소곤소곤 우체통", "편지를 누적 3통 받아요.", 500L, 16),
    LEVEL_SEVENTEEN("웅성웅성 우체통", "편지를 누적 10통 받아요.", 1000L, 17),
    LEVEL_EIGHTEEN("시끌시끌 우체통", "편지를 누적 25통 받아요.", 2000L, 18);

    private final String name;
    private final String tag;
    private final Long point;
    private final int level;


    DefaultAchievement(String name, String tag, Long point, int level) {
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

    public Long getPoint() {
        return point;
    }

    public int getLevel() {
        return level;
    }
}
