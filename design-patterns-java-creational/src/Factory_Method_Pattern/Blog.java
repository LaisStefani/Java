package Factory_Method_Pattern;

import java.awt.desktop.AboutEvent;

public class Blog extends Website{
    @Override
    protected void createWebsite() {
        pages.add(new PostPage());
        pages.add(new AboutPage());
        pages.add(new CommentPage());
        pages.add(new ContractPage());
    }
}
