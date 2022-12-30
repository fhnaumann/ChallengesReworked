package wand555.github.io.challengesreworkedgui;

import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

// this class effectively does nothing, but it will be loaded by the
// application class loader
// instead of the system class loader.
public class ResourceBundleWrapper extends ResourceBundle {

    private final ResourceBundle bundle;

    public ResourceBundleWrapper(ResourceBundle bundle) {
        this.bundle = bundle;
    }

    @Override
    protected Object handleGetObject(String key) {
        return bundle.getObject(key);
    }

    @Override
    public Enumeration<String> getKeys() {
        return bundle.getKeys();
    }

    @Override
    public boolean containsKey(String key) {
        return bundle.containsKey(key);
    }

    @Override
    public Locale getLocale() {
        return bundle.getLocale();
    }

    @Override
    public Set<String> keySet() {
        return bundle.keySet();
    }

}
