package com.github.aureliano.verbum_domini.core;

import java.util.Properties;
import java.util.Set;

import com.github.aureliano.verbum_domini.core.data.DataBookAbbreviations;
import com.github.aureliano.verbum_domini.core.exception.VerbumDominiException;
import com.github.aureliano.verbum_domini.core.helper.PropertyHelper;

public class AppConfiguration {

	private static final String APP_CONFIGURATION_RESOURCE_NAME = "app-configuration.properties";
	private static AppConfiguration instance;
	
	private PersistenceManager persistenceManager;
	private Properties properties;
	private DataBookAbbreviations dataBookAbbreviations;
	
	private Environments environment;
	
	private AppConfiguration() {
		this.properties = PropertyHelper.loadProperties(APP_CONFIGURATION_RESOURCE_NAME);
		this.environment = this.getDefaultEnvironment();
		this.dataBookAbbreviations = this.createDataBookAbbreviations();
	}

	public static AppConfiguration instance(){
		if (instance == null) {
			instance = new AppConfiguration();
		}
		
		return instance;
	}
	
	public String getProperty(String key) {
		return this.properties.getProperty(key);
	}
	
	public Set<Object> getKeys() {
		return this.properties.keySet();
	}
	
	public PersistenceManager getPersistenceManager() {
		if (this.persistenceManager == null) {
			this.persistenceManager = this.createPersistenceManager();
		}
		
		return this.persistenceManager;
	}
	
	public DataBookAbbreviations getDataBookAbbreviations() {
		return this.dataBookAbbreviations;
	}
	
	public void setEnvironment(Environments env) {
		this.environment = env;
	}
	
	public Environments getEnvironment() {
		return this.environment;
	}
	
	private PersistenceManager createPersistenceManager() {
		Class<?> clazz = this.findPersistenceManager("com.github.aureliano.verbum_domini.core.impl.PersistenceManagerImpl");
		if (clazz == null) {
			throw new VerbumDominiException("Could not find any implementation of persistence manager.");
		}
		
		try {
			return (PersistenceManager) clazz.newInstance();
		} catch (Exception ex) {
			throw new VerbumDominiException(ex);
		}
	}
	
	private Class<?> findPersistenceManager(String className) {
		try {
			return Class.forName(className);
		} catch (Exception ex) {
			return null;
		}
	}
	
	private Environments getDefaultEnvironment() {
		if (System.getenv("APP_ENVIRONMENT") != null) {
			return Environments.valueOf(System.getenv("APP_ENVIRONMENT"));
		} else if (System.getProperty("APP_ENVIRONMENT") != null) {
			return Environments.valueOf(System.getProperty("APP_ENVIRONMENT"));
		} else {
			return Environments.DEVELOPMENT;
		}
	}
	
	private DataBookAbbreviations createDataBookAbbreviations() {
		return new DataBookAbbreviations()
			.addAbbreviation("Genesis", "Gen", "Ge", "Gn")
			.addAbbreviation("Exodus", "Exod", "Ex")
			.addAbbreviation("Leviticus", "Lev", "Lv", "Le")
			.addAbbreviation("Numbers", "Num", "Nm", "Nu")
			.addAbbreviation("Deuteronomy", "Deut", "Dt", "De", "Du")
			.addAbbreviation("Joshua", "Josh", "Jos", "Jo")
			.addAbbreviation("Judges", "Judg", "Jdg", "Jgs")
			.addAbbreviation("Ruth", "Ruth", "Ru")
			.addAbbreviation("1 Samuel", "1 Sam", "1Sm", "1Sa")
			.addAbbreviation("2 Samuel", "2 Sam", "2Sm", "2Sa")
			.addAbbreviation("1 Kings", "1 Kgs", "1Kg", "1Ki")
			.addAbbreviation("2 Kings", "2 Kgs", "2Kg", "2Ki")
			.addAbbreviation("1 Chronicles", "1 Chr", "1 Chron", "1Ch")
			.addAbbreviation("2 Chronicles", "2 Chr", "2 Chron", "2Ch")
			.addAbbreviation("Ezra", "Ezra", "Ezr")
			.addAbbreviation("Nehemiah", "Neh", "Ne")
			.addAbbreviation("Tobit", "Tob", "Tb")
			.addAbbreviation("Judith", "Jdt", "Jth")
			.addAbbreviation("Esther", "Esth", "Est", "Es")
			.addAbbreviation("1 Maccabees", "1 Macc", "1Mc", "1Ma")
			.addAbbreviation("2 Maccabees", "2 Macc", "2Mc", "2Ma")
			.addAbbreviation("Job", "Job", "Jb")
			.addAbbreviation("Psalms", "Ps", "Pss")
			.addAbbreviation("Proverbs", "Prov", "Prv", "Pr")
			.addAbbreviation("Ecclesiastes", "Eccl", "Eccles", "Ec")
			.addAbbreviation("The Song of Songs", "Song", "SS", "So", "Sg")
			.addAbbreviation("Wisdom", "Wis,  Ws")
			.addAbbreviation("Sirach", "Sir")
			.addAbbreviation("Isaiah", "Isa", "Is")
			.addAbbreviation("Jeremiah", "Jer", "Je")
			.addAbbreviation("Lamentations", "Lam", "La")
			.addAbbreviation("Baruch", "Bar", "Ba")
			.addAbbreviation("Ezekiel", "Ezek", "Ezk", "Ez")
			.addAbbreviation("Daniel", "Dan", "Dn", "Da")
			.addAbbreviation("Hosea", "Hos", "Ho")
			.addAbbreviation("Joel", "Joel", "Joe", "Jl")
			.addAbbreviation("Amos", "Amos", "Am")
			.addAbbreviation("Obadiah", "Obad", "Ob")
			.addAbbreviation("Jonah", "Jonah", "Jon")
			.addAbbreviation("Micah", "Mic", "Mi")
			.addAbbreviation("Nahum", "Nah", "Na")
			.addAbbreviation("Habakkuk", "Hab", "Hb")
			.addAbbreviation("Zephaniah", "Zeph", "Zep")
			.addAbbreviation("Haggai", "Hag", "Hg")
			.addAbbreviation("Zechariah", "Zech", "Zec")
			.addAbbreviation("Malachi", "Mal", "Ml")
			.addAbbreviation("Matthew", "Matt", "Mat", "Mt")
			.addAbbreviation("Mark", "Mark", "Mar", "Mk")
			.addAbbreviation("Luke", "Luke", "Lk", "Lu")
			.addAbbreviation("John", "John", "Jn", "Jo")
			.addAbbreviation("Acts of the Apostles", "Acts", "Ac")
			.addAbbreviation("Romans", "Rom", "Rm", "Ro")
			.addAbbreviation("1 Corinthians", "1 Cor", "1 Co", "1C")
			.addAbbreviation("2 Corinthians", "2 Cor", "2 Co", "2C")
			.addAbbreviation("Galatians", "Gal", "Ga")
			.addAbbreviation("Ephesians", "Eph", "Ep")
			.addAbbreviation("Philippians", "Phil", "Php")
			.addAbbreviation("Colossians", "Col", "Co")
			.addAbbreviation("1 Thessalonians", "1 Thess", "1 Thes", "1Th")
			.addAbbreviation("2 Thessalonians", "2 Thess", "2 Thes", "2Th")
			.addAbbreviation("1 Timothy", "1 Tim", "1 Tm", "1 Ti", "1T")
			.addAbbreviation("2 Timothy", "2 Tim", "2 Tm", "2 Ti", "2T")
			.addAbbreviation("Titus", "Titus", "Tit", "Ti")
			.addAbbreviation("Philemon", "Phlm", "Philem", "Phm")
			.addAbbreviation("Hebrews", "Heb", "He")
			.addAbbreviation("James", "Jas", "Ja")
			.addAbbreviation("1 Peter", "1 Pet", "1 Pt", "1P")
			.addAbbreviation("2 Peter", "2 Pet", "2 Pt", "2P")
			.addAbbreviation("1 John", "1 John", "1 Jn", "1 Jo", "1J")
			.addAbbreviation("2 John", "2 John", "2 Jn", "2 Jo", "2J")
			.addAbbreviation("3 John", "3 John", "3 Jn", "3 Jo", "3J")
			.addAbbreviation("Jude", "Jude", "Ju")
			.addAbbreviation("Revelation", "Rev", "Re", "Rv")
			.done();
	}
}