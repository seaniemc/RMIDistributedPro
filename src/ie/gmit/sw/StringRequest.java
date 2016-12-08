package ie.gmit.sw;


public class StringRequest  {
    String str1;
    String str2;
    String algo;
    String taskNumber;

    public StringRequest(String str1, String str2, String algo, String taskNumber) {
        this.str1 = str1;
        this.str2 = str2;
        this.algo = algo;
        this.taskNumber = taskNumber;
    }

    public String getStr1() {
        return str1;
    }

    public void setStr1(String str1) {
        this.str1 = str1;
    }

    public String getStr2() {
        return str2;
    }

    public void setStr2(String str2) {
        this.str2 = str2;
    }

    public String getAlgo() {
        return algo;
    }

    public void setAlgo(String algo) {
        this.algo = algo;
    }

    public String getTaskNumber() {
        return taskNumber;
    }

    public void setTaskNumber(String taskNumber) {
        this.taskNumber = taskNumber;
    }


}