package mvc2;

public class CalendarPane {
    // data members
    private String month;
    private String Year;
    private int[] dates;
    private String[] nameOfDays;

    public CalendarPane(String month, String year, int[] dates, String[] nameOfDays) {
        this.month = month;
        Year = year;
        this.dates = dates;
        this.nameOfDays = nameOfDays;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return Year;
    }

    public void setYear(String year) {
        Year = year;
    }

    public int[] getDates() {
        return dates;
    }

    public void setDates(int[] dates) {
        this.dates = dates;
    }

    public String[] getNameOfDays() {
        return nameOfDays;
    }

    public void setNameOfDays(String[] nameOfDays) {
        this.nameOfDays = nameOfDays;
    }
}
