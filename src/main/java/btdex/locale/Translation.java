package btdex.locale;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.Properties;

public class Translation {
	
	private static final String RESOURCE_FILE = "/locale/i18n.btdex";
	private static final String RESOURCE_TR_FILE = "/locale/tr/i18n_btdex";
	
	private static Properties enResource = new Properties();
	private static Properties resource;
	private static Locale locale;
	
	private static final Locale[] languages = {
		Locale.ENGLISH,
		Locale.GERMAN,
		// Locale.forLanguageTag("es"),
		Locale.forLanguageTag("pt"),
		Locale.forLanguageTag("it"),
		Locale.forLanguageTag("da"),
		Locale.forLanguageTag("lt"),
		Locale.forLanguageTag("ru"),
	};
	
	static {
		try {
			enResource.load(Translation.class.getResourceAsStream(RESOURCE_FILE + ".properties"));
			resource = enResource;			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Locale[] getSupportedLanguages() {
		return languages;
	}
	
	public static Locale getCurrentLocale() {
		return locale;
	}
	
	public static void setLanguage(String language) {
		if(language == null || language.length()==0) {
			// if never set, we will use the machine language by default
			language = Locale.getDefault().getLanguage();
		}
		locale = Locale.forLanguageTag(language);
		try {
			InputStream stream = Translation.class.getResourceAsStream(RESOURCE_TR_FILE + "_" + language + ".properties");
			if(stream != null) {
				resource = new Properties();
				resource.load(stream);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String tr(String key, Object... arguments) {
        return MessageFormat.format(get(key), arguments);
    }
	
	private static String get(String key) {
		String txt = resource.getProperty(key);
		if(txt == null)
			txt = enResource.getProperty(key);
		if(txt == null)
			txt = key;
		
		return txt;
	}

}
