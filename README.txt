Connecting to the database:

1. We create the < worldDB.properties > file, that is found in resources
    - fill in the connection info, so we can use that to connect to our DB
__ Now we want to use that connection info, to connect to our DB...
2. We create PropertiesProvider to be able to read from worldDB.properties file.
    - because of its static{} block , it will be executed when PropertiesProvider is "instantiated"

3. So when we want the "hub" for our database interaction methods (WorldService), with query insertions that look for example: "SELECT * FROM country,
        when we instantiate WorldService it will make PropertiesProvider feed the data it reads from the worldDB.properties file and send it in  ::   private final MysqlDataSource worldDS;
        so now worldDS is something we use to connect to our DB, every method needs the  getConnection() inside the try(){}catch(){};
        so we say worldDS.getConnection();

-- so now that we have made methods inside WorldService class, that use wordDS to connect to the database and the methods with query insertions
    we want to use Menu to use those methods,  there is in case 7 that I moved responsibility more to the addCity method to showcase that you can
    create menus more "slimmer" and not have so much in them , but also you can ... your choice
            -- you can also notice that i sent objects to addCity method in case 7 (inside Menu class), those objects are: connection,

