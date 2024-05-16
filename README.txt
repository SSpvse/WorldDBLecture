Connecting to the database:

1. We create the < worldDB.properties > file, that is found in resources
    - fill in the connection info, so we can use that to connect to our DB
__ Now we want to use that connection info, to connect to our DB...
2. We create PropertiesProvider to be able to read from worldDB.properties file.
    - because of its static{} block , it will be executed when PropertiesProvider is loaded in java virtual machine meaning : it will load before the main method even starts, making the data be there from the get-go.



3. So when we want the "hub" for our database interaction methods (WorldService), with query insertions that look for example: "SELECT * FROM country,
        we instantiate WorldService and it will make PropertiesProvider feed the data it reads from the worldDB.properties file and send it in  ::   private final MysqlDataSource worldDS;
        so now worldDS is something we use to connect to our DB, every method needs the  getConnection() inside the try(){}catch(){};
        so we say worldDS.getConnection();

-- so now that we have made methods inside WorldService class, that use wordDS to connect to the database and the methods with query insertions
    we want to use Menu to use those methods,  there is in case 7 that I moved responsibility more to the addCity method to showcase that you can
    create menus more "slimmer" and not have so much in them , but also you can ... your choice
            -- you can also notice that i sent objects to addCity method in case 7 (inside Menu class), those objects are: connection,



