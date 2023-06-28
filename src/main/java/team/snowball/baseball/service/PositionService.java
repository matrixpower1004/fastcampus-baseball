package team.snowball.baseball.service;

public class PositionService implements CommandService{

    private static PositionService positionService;
    private PositionService() {
    }

    public static PositionService getInstance() {
        if (positionService == null) {
            positionService = new PositionService();
        }
        return positionService;
    }

    @Override
    public void create() {
        System.out.println("등록");
    }

    @Override
    public void read() {
        System.out.println("조회");
    }

    @Override
    public void update() {
        System.out.println("수정");
    }

    @Override
    public void delete() {
        System.out.println("삭제");
    }
}


