/* **********   CookiesPersistance.java   **********
 *
 * This piece of garbage was brought to you by nothing less than the almighty lord
 * of programming, the Java God and ruler of all the non living things, McBeengs, 
 * A.K.A. myself. I don't mind anyone steal or using my codes at their own business,
 * but at least, and I meant VERY least, give me the proper credit for it. I really
 * don't know what the code below does at this point in time while I write this stuff, 
 * but if you took all this time to sit, rip the .java files and read all this 
 * unnecessary bullshit, you know for what you came, doesn't ?
 * 
 * Copyright(c) {YEAR!!!} Mc's brilliant mind. All Rights (kinda) Reserved.
 */

 /*
 * {Insert class description here}
 */
package com.util.serialize;

import java.io.IOException;
import java.io.Serializable;
import java.net.CookieManager;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CookiesPersistance extends CookieManager implements Serializable {

    private Map<URI, Map<String, List<String>>> setter = new HashMap<>();
    
    public CookiesPersistance() {
        
    }
    
    public CookiesPersistance(Map<URI, Map<String, List<String>>> cookies) {
        Object[] injector = cookies.keySet().toArray();
        setter = cookies;
        
        for (Object get : injector) {
            try {
                URI key = new URI(get.toString());
                super.put(key, cookies.get(key));
            }catch (URISyntaxException | IOException ex) {
                Logger.getLogger(CookiesPersistance.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public Map<String, List<String>> get(URI uri, Map<String, List<String>> requestHeaders) throws IOException {
        return super.get(uri, requestHeaders);
    }

    @Override
    public void put(URI uri, Map<String, List<String>> responseHeaders) throws IOException {
        if (!setter.containsValue(responseHeaders)) {
            super.put(uri, responseHeaders);
            setter.put(uri, responseHeaders);
        }
    }
    
    public Map<URI, Map<String, List<String>>> getCookies() {
        return setter;
    }
}
