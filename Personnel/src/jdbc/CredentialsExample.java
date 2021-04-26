package jdbc;

public class CredentialsExample 
{
	private static String driver ="mysql",
			driverClassName = "com.mysql.cj.jdbc.Driver",
			host = "localhost", 
			port ="3306",
			database ="personnel",
			user = "root",
			password = "",
			TimeZone ="?zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=UTC";
	
	static String getUrl() 
	{
		return "jdbc:" + driver + "://" + host + "/" + database + TimeZone ;
	}
	
	static String getDriverClassName()
	{
		return driverClassName;
	}
	
	static String getUser() 
	{
		return user;
	}

	static String getPassword() 
	{
		return password;
	}
}
