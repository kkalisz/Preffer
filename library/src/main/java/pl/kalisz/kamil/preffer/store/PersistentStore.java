package pl.kalisz.kamil.preffer.store;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import pl.kalisz.kamil.preffer.SetUtils;

/**
 * Store implementation for marking preferences that should be persistent
 */
public class PersistentStore implements Store {
    private static final String DELIMITER = "[$]";

    public static final String PERSISTENT_KEY_PREFIX = "@PERSISTENT@";

    private Store delegate;

    /**
     * @param saveStore store to be used to save/read values with added information about persist
     */
    public PersistentStore(Store saveStore) {
        this.delegate = saveStore;
    }


    @Override
    public void setValue(String key, String value) {
        delegate.setValue(new ContextKey(PERSISTENT_KEY_PREFIX,key).generateContextKey(), value);
    }

    @Override
    public String getValue(String key) {
        return delegate.getValue(new ContextKey(PERSISTENT_KEY_PREFIX, key).generateContextKey());
    }

    @Override
    public Set<String> getKeys()
    {
        Set<String> allDelegateKeys = delegate.getKeys();
        Collection<ContextKey> contextKeys = ContextKeyUtils.filterByContext(allDelegateKeys, PERSISTENT_KEY_PREFIX);
        return ContextKeyUtils.getRawKeys(contextKeys);
    }

}
