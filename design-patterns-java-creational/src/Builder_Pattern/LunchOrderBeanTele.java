package Builder_Pattern;

public class LunchOrderBeanTele {
    private String bread;
    private String condiments;
    private String dressing;
    private String meat;

    public LunchOrderBeanTele(String bread) {

        this.bread = bread;
    }

    public LunchOrderBeanTele(String bread, String condiments) {
        this.condiments = condiments;
    }

    public LunchOrderBeanTele(String bread, String condiments, String dressing) {
        this.dressing = dressing;
    }

    public LunchOrderBeanTele(String bread, String condiments, String dressing, String meat) {
        this.meat = meat;
    }

    public String getBread() {
        return bread;
    }

    public void setBread(String bread) {
        this.bread = bread;
    }

    public String getCondiments() {
        return condiments;
    }

    public void setCondiments(String condiments) {
        this.condiments = condiments;
    }

    public String getDressing() {
        return dressing;
    }

    public void setDressing(String dressing) {
        this.dressing = dressing;
    }

    public String getMeat() {
        return meat;
    }

    public void setMeat(String meat) {
        this.meat = meat;
    }
}