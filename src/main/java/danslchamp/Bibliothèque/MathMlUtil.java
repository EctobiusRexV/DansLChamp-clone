package danslchamp.Bibliothèque;

import javafx.scene.web.WebView;

public class MathMlUtil{
    public static WebView creatWebview(String formule) {
        WebView webView = new WebView();
        webView.getEngine().loadContent(formule);
        return webView;
    }
}

