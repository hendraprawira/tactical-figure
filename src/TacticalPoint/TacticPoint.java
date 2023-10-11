package TacticalPoint;

public final class TacticPoint {

    public int mockID;
    public String message = "";
    public String tacticType = "";

    public TacticPoint() {
    }

    public TacticPoint(
        int _mockID,
        String _message,
        String _tacticType)
    {
        mockID = _mockID;
        message = _message;
        tacticType = _tacticType;
    }

}
