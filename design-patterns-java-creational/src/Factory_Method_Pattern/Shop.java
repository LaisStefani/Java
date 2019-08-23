package Factory_Method_Pattern;

import javax.naming.directory.SearchResult;

public class Shop extends Website{

    @Override
    protected void createWebsite() {
        pages.add(new CartPage());
        pages.add(new ItemPage());
        pages.add(new SearchPage());
    }
}
