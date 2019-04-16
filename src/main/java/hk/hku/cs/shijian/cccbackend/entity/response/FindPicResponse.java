package hk.hku.cs.shijian.cccbackend.entity.response;

public class FindPicResponse extends CommonResponse {

    private long[] timeStamps;
    private double[] similarities;

    public FindPicResponse() {
        super();
    }

    public FindPicResponse(int errcode, String errmsg) {
        super(errcode, errmsg);
    }

    public FindPicResponse(int errcode, String errmsg, long[] timeStamps, double[] similarities) {
        super(errcode, errmsg);
        this.timeStamps = timeStamps;
        this.similarities = similarities;
    }

    public long[] getTimeStamps() {
        return timeStamps;
    }

    public void setTimeStamps(long[] timeStamps) {
        this.timeStamps = timeStamps;
    }

    public double[] getSimilarities() {
        return similarities;
    }

    public void setSimilarities(double[] similarities) {
        this.similarities = similarities;
    }
}
