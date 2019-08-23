package Builder_Pattern;

public class LunchOrderBeanDemo {
    public static void main(String[] args) {

        LuchOrder.Builder builder = new LuchOrder.Builder();

        builder.bread("Wheat").condiments("Lettuce").dressing("Mustard").meat("Ham");

        LuchOrder lunchOrder = builder.build();

        //       lunchOrderBean.setBread("Wheat");
        //       lunchOrderBean.setCondiments("Lettuce");
        //       lunchOrderBean.setDressing("Mustard");
        //       lunchOrderBean.setMeat("Ham");

        System.out.println(lunchOrder.getBread());
        System.out.println(lunchOrder.getCondiments());
        System.out.println(lunchOrder.getDressing());
        System.out.println(lunchOrder.getMeat());
    }
}
