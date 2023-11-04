import java.sql.*;

/**
* JDBCConnectionMaker is a utility class for managing the details of
* instantiating a JDBC Connection object.
* Although it resembles an Object Pool class, it is not quite that.
* Mainly, its purpose is to centralize the information required
* to connect to a DB, so that the information is not duplicated
* across any other classes/methods that would need to instantiate
* a JDBC Connection.
*
* @author Scott Swanson
* @version 2023.04.01
*/
public class JDBCConnectionMaker {
private String dbName;
private String hostName;
private String portNumber;
private String userName;
private String password;
/**
* Constructor requires all information needed to instantiate a
* functional Connection object.
* Note that the constructor makes no attempt to validate
* arguments.
* @param dbName_new also called the schema
* @param hostName_new
* @param portNumber_new
* @param userName_new
* @param password_new
* @return a valid, initialized JDBCConnector object
*/
public JDBCConnectionMaker (
String dbName_new,
String hostName_new,
String portNumber_new,
String userName_new,
String password_new
) {
this.dbName = dbName_new;
this.hostName = hostName_new;
this.portNumber = portNumber_new;
this.userName = userName_new;
this.password = password_new;
}
/**
* Attempts to instantiate a JDBC Connection object.
* @return a valid Connection object for the DB configuration
* the JDBCConnectionMaker was constructed with.
* null if the connection failed for any reason.
*/
public Connection getConnection () {
Connection dbCxn = null;
String jdbcURL =
"jdbc:mySQL://"
+ hostName
+ ":"
+ portNumber
+ dbName;
try {
dbCxn = DriverManager.getConnection(
jdbcURL,
userName,
password
);
}
catch(SQLException e){
System.out.println(e);
}
return dbCxn;
}
}
/**
* EntityBits is an entity mapped to a preexisting mySQL table called Bits.
*
* EntityBits supports CRUD operations via the JDBC library objects and methods:
* Connection.createStatement; Statement.executeQuery; Statement.executeUpdate.
*
* @author Scott Swanson
* @version 2023.10.12
*/
public class EntityBits {
// Is this really the right way to embed the table information?
// Should it be hard-coded? If not, then ... what?
// People will argue about such things
private static final String dbTableName = "Bits";
private static final String primaryKeyColumnName = "Bits_pk";
private static final int primaryKeyColumnIdx = 1;
// The proper handling of enumerated types between Java and MySQL
// is also a matter of some debate.
enum JKLValues {
i,
me,
mine
}
// these member fields map directly to the Bits DB table columns
// with the same names, but prefixed with "Bits_" -- e.g., Bits_pk.
private int pk;
private int mn;
private OPValues op;
private java.util.Date qr;
// To keep this example simple, ALL instances of EntityBits will share the
// the same JDBCConnector. Bits is a problem if you want your program
// to be able to access different databases for different EntityBits objects.
// There are solutions, but they are complicated distractions from the
// objectives of this assignment.
private static JDBCConnectionMaker dbConnectionMaker;
/**
* Provides protected access to the name of the column containing the Primary Key.
* @return the name of the column containing the Primary Key
*/
protected String primaryKeyColumnName() {
};
/**
* Provides protected access to the index of the column containing the Primary Key.
* @return the index of the column containing the Primary Key
*/
protected int primaryKeyColumnIndex() {
}
/**
* Provides protected access to the value of the Primary Key
* @return the integer value corresponding to the Primary Key
*/
protected int primaryKey() {
}
/**
* Copies values from a JDBC ResultSet object into the
* EntityBits internal data structures.
* Does NOT set the primary key
* @param resSet ResultSet of a Select * on the Bits table.
* Precondition: the ResultSet iterator *must* point at a valid row.
* i.e., resSet.next() must have been called, and must
* have returned true.
* @return true
*/
protected boolean populateFromResultSet(ResultSet resSet) {
// iterate over the fields of the resSet, picking up each database value by its name
// and then putting it down with the appropriate public Setter.
}
/**
* Inserts a single record into the Bits db table, populating it with the values
* stored in the current object instance.
* Precondition: the dbConnectionMaker must have been set
* @return true IFF the record was successfully inserted into the DB
*/
protected boolean create() {
// assume success -- set result = true
// build a SQL Insert statement string
// get a Connection object from the dbConnectionMaker
// get a Statement object from the Connection
// execute the insert statement via the Statement object
// get from the Statement the primary key that was generated
// and store it as the primary key value of this object instance
// if the insert threw an exception, set result = false
// return result
}
/**
* Reads a single record from the sql table, and populates this object instance
with
* the values returned. read populates ONLY those fields contained in one row of
* the table mapped by the JDBCEntity subclass. It does NOT "drill" recursively
* into other sql records with related Foreign Keys. EVER.
* Precondition: the dbConnector must have been set
* @param primaryKey the value of the primary key field for the target row.
* Precondition: this must be either a valid primary key for
* for an existing row in the DB
* @return true IFF the record was successfully read from the DB and the object
instance
* was successfully populated.
*/
protected boolean read (int queryPrimaryKey) {
// assume success -- set result = true
// build a SQL Select statement string
// get a Connection object from the dbConnectionMaker
// get a Statement object from the Connection
// execute the Select statement via the Statement object
// if the select threw an exception, set result = false
// otherwise:
// set the primary key of this object to the queryPrimaryKey
// call setDataFromResultSet with resultSet of the Select
// set result to whatever is returned by setDataFromResultSet
// OR if the select threw an exception, set result = false
// return result
}
/**
* Updates a single record of the Bits db table, populating it with the values
* stored in the current object instance.
* Precondition: the dbConnectionMaker must have been set
* the primary key must be a valid key in the table
* @return true IFF the record was successfully updated into the DB
*/
protected boolean update() {
// assume success -- set result = true
// build a SQL Update statement string
// get a Connection object from the dbConnectionMaker
// get a Statement object from the Connection
// execute the update statement via the Statement object
// if the update throws an exception, set result = false
// return result
}
/**
* Deletes a single record from the Bits db table, corresponding to the
* primary key value of the current object instance
* Precondition: the dbConnectionMaker must have been set
* the primary key must be a valid key in the table
* @return true IFF the record was successfully deleted from the DB
*/
protected boolean delete () {
// assume success -- set result = true
// build a SQL Delete statement string
// get a Connection object from the dbConnectionMaker
// get a Statement object from the Connection
// execute the delete statement via the Statement object
// if the delete throws an exception, set result = false
// return result
}
/**
* Either inserts or updates a single record of the Bits db table, depending on
* whether the primary key of the current object instance has been set.
* Precondition: the dbConnectionMaker must have been set
* the primary key must be a valid key in the table, or 0
* @return true IFF the record was successfully inserted/updated into the DB
*/
public boolean save() {
// assume failure, set result = false
// if the current primary key is 0, call create
// if the current primary key is not 0, call update
// set the result to whatever was returned by the create/update call
// return the result
};
/**
* Reads a single record of the Bits db table
* Precondition: the dbConnectionMaker must have been set
* @param queryPrimaryKey primary key of the desired record
* @return true IFF the record was successfully read from the DB
* and the current object instance successfully
* populated with the returned values
*/
public boolean load (int queryPrimaryKey) {
// assume failure, set result = false
// call read() with the queryPrimaryKey
// set result to whatever was returned by read()
// return result
};
/**
* Deletes the record of the Bits db table corresponding to the current object
instance
* Precondition: the dbConnectionMaker must have been set
* @return true IFF the record was successfully deleted
*/
public boolean remove () {
// assume failure, set result = false
// if the primary key is > 0
// call delete()
// set result to whatever was returned by delete()
// return result
};
/**
* Setter for the static dbConnectionMaker object member field
* Precondition: the dbConnectionMaker should be a valid JDBConnectionMaker
instance
* @return void
*/
public void setDbConnector ( JDBCConnectionMaker dbConnectionMaker_new ) {
dbConnectionMaker = dbConnectionMaker_new;
}
/**
* Setter for Bits_mn table field
* @param mn_new
* @return void
*/
public void setMn (int mn_new){
}
/**
* Setter for Bits_op table field
* @param op_new
* @return void
*/
public void setOp (OPValues op_new){
}
/**
* Setter for Bits_qr table field
* @param qr_new
* @return void
*/
public void setQr (java.util.Date qr_new){
}
/**
* Getter for Bits_mn table field
* @return void
*/
public int getMn (){
}
/**
* Getter for Bits_op table field
* @return void
*/
public OPValues getOp (){
}
/**
* Getter for Bits_qr table field
* @return void
*/
public java.util.Date getQr (){
}
/**
* Constructor creates an empty EntityBits object
* @return a valid EntityBits object that does not map to a record in the DB
*/
public EntityBits (
) {
}
/**
* Constructor creates an EntityBits object, and attempts to load a record from the
DB
* @return IFF the queryPrimaryKey is valid, an EntityBits object populated with
the
* corresponding values for that record.
* otherwise, returns a valid but empty EntityBits object
*/
public EntityBits (int queryPrimaryKey) {
this.load(queryPrimaryKey);
}
}
/**
* EntityBobs is an entity mapped to a preexisting mySQL table called Bobs.
*
* EntityBits supports CRUD operations via the JDBC library objects and methods:
* Connection.createStatement; Statement.executeQuery; Statement.executdUpdate.
*
* @author Scott Swanson
* @version 2023.10.12
*/
public class EntityBobs {
// Is this really the right way to embed the table information?
// Should it be hard-coded? If not, then ... what?
// People will argue about such things
private static final String dbTableName = "Bobs";
private static final String primaryKeyColumnName = "Bobs_pk";
private static final int primaryKeyColumnIdx = 1;
// these member fields map directly to the Bobs DB table columns
// with the same names, but prefixed with "Bobs_" -- e.g., Bobs_pk.
private int pk;
private String st;
private int uv;
private int bobsBitsPk;
// To keep this example simple, ALL instances of EntityBobs will share the
// the same JDBCConnector. Bits is a problem if you want your program
// to be able to access different databases for different EntityBobs objects.
// There are solutions, but they are complicated distractions from the
// objectives of this assignment.
protected static JDBCConnectionMaker dbConnectionMaker;
/**
* Provides protected access to the name of the column containing the Primary Key.
* @return the name of the column containing the Primary Key
*/
protected String primaryKeyColumnName() {
};
/**
* Provides protected access to the index of the column containing the Primary Key.
* @return the index of the column containing the Primary Key
*/
protected int primaryKeyColumnIndex() {
}
/**
* Provides protected access to the value of the Primary Key
* @return the integer value corresponding to the Primary Key
*/
protected int primaryKey() {
}
/**
* Copies values from a JDBC ResultSet object into the
* EntityBobs internal data structures.
* Does NOT set the primary key
* @param resSet ResultSet of a Select * on the Bobs table.
* Precondition: the ResultSet iterator *must* point at a valid row.
* i.e., resSet.next() must have been called, and must
* have returned true.
* @return true
*/
protected boolean populateFromResultSet(ResultSet resSet) {
// iterate over the fields of the resSet, picking up each database value by its name
// and then putting it down with the appropriate public Setter.
}
/**
* Inserts a single record into the Bobs db table, populating it with the values
* stored in the current object instance.
* Precondition: the dbConnectionMaker must have been set
* @return true IFF the record was successfully inserted into the DB
*/
protected boolean create() {
// assume success -- set result = true
// build a SQL Insert statement string
// get a Connection object from the dbConnectionMaker
// get a Statement object from the Connection
// execute the insert statement via the Statement object
// get from the Statement the primary key that was generated
// and store it as the primary key value of this object instance
// if the insert threw an exception, set result = false
// return result
}
/**
* Reads a single record from the sql table, and populates this object instance
with
* the values returned. read populates ONLY those fields contained in one row of
* the table mapped by the JDBCEntity subclass. It does NOT "drill" recursively
* into other sql records with related Foreign Keys. EVER.
* Precondition: the dbConnector must have been set
* @param primaryKey the value of the primary key field for the target row.
* Precondition: must be either a valid primary key for
* for an existing row in the DB
* @return true IFF the record was successfully read from the DB and the object
instance
* was successfully populated.
*/
protected boolean read (int queryPrimaryKey) {
// assume success -- set result = true
// build a SQL Select statement string
// get a Connection object from the dbConnectionMaker
// get a Statement object from the Connection
// execute the Select statement via the Statement object
// if the select threw an exception, set result = false
// otherwise:
// set the primary key of this object to the queryPrimaryKey
// call setDataFromResultSet with resultSet of the Select
// set result to whatever is returned by setDataFromResultSet
// OR if the select threw an exception, set result = false
// return result
}
/**
* Updates a single record of the Bobs db table, populating it with the values
* stored in the current object instance.
* Precondition: the dbConnectionMaker must have been set
* the primary key must be a valid key in the table
* @return true IFF the record was successfully updated into the DB
*/
protected boolean update() {
    // assume success -- set result = true
    // build a SQL Update statement string
    // get a Connection object from the dbConnectionMaker
    // get a Statement object from the Connection
    // execute the update statement via the Statement object
    // if the update throws an exception, set result = false
    // return result
    }
    /**
    * Deletes a single record from the Bobs db table, corresponding to the
    * primary key value of the current object instance
    * Precondition: the dbConnectionMaker must have been set
    * the primary key must be a valid key in the table
    * @return true IFF the record was successfully deleted from the DB
    */
    protected boolean delete () {
    // assume success -- set result = true
    // build a SQL Delete statement string
    // get a Connection object from the dbConnectionMaker
    // get a Statement object from the Connection
    // execute the delete statement via the Statement object
    // if the delete throws an exception, set result = false
    // return result
    }
    /**
    * Either inserts or updates a single record of the Bobs db table, depending on
    * whether the primary key of the current object instance has been set.
    * Precondition: the dbConnectionMaker must have been set
    * the primary key must be a valid key in the table, or 0
    * @return true IFF the record was successfully inserted/updated into the DB
    */
    public boolean save() {
    // assume failure, set result = false
    // if the current primary key is 0, call create
    // if the current primary key is not 0, call update
    // set the result to whatever was returned by the create/update call
    // return the result
    };
    /**
    * Reads a single record of the Bobs db table
    * Precondition: the dbConnectionMaker must have been set
    * @param queryPrimaryKey primary key of the desired record
    * @return true IFF the record was successfully read from the DB
    * and the current object instance successfully
    * populated with the returned values
    */
    public boolean load (int queryPrimaryKey) {
    // assume failure, set result = false
    // call read() with the queryPrimaryKey
    // set result to whatever was returned by read()
    // return result
    };
    /**
    * Deletes the record of the Bits db table corresponding to the current object
    instance
    * Precondition: the dbConnectionMaker must have been set
    * @return true IFF the record was successfully deleted
    */
    public boolean remove () {
    // assume failure, set result = false
    // if the primary key is > 0
    // call delete()
    // set result to whatever was returned by delete()
    // return result
    };
    /**
    * Setter for the static dbConnectionMaker object member field
    * Precondition: the dbConnectionMaker should be a valid JDBCConnectionMaker
    instance
    * @return void
    */
    public void setDbConnector ( JDBCConnectionMaker dbConnectionMaker_new ) {
    dbConnectionMaker = dbConnectionMaker_new;
    }
    /**
    * Setter for Bobs_st table field
    * @param st_new
    * @return void
    */
    public void setSt (String st_new){
    }
    /**
    * Setter for Bobs_uv table field
    * @param pqr_new
    * @return void
    */
    public void setUv (int uv_new){
    }
    /**
    * Setter for Bobs_Bits_pk table field
    * @param bobsBitsPk_new
    * @return void
    */
    public void setBobsBitsPk (int bobsBitsPk_new){
    }
    /**
    * Getter for Bobs_st table field
    * @return void
    */
    public String getSt (){
    }
    /**
    * Getter for Bobs_uv table field
    * @return void
    */
    public int getUv (){
    }
    /**
    * Getter for Bobs_Bits_pk table field
    * @return void
    */
    public int getBobsBitsPk (){
    }
    /**
    * Constructor creates an empty EntityBobs object
    * @return a valid EntityBobs object that does not map to a record in the DB
    */
    public EntityBobs (
    ) {
    }
    /**
    * Constructor creates an EntityBobs object, and attempts to load a record from the
    DB
    * @return IFF the queryPrimaryKey is valid, an EntityBobs object populated with
    the
    * corresponding values for that record.
    * otherwise, returns a valid but empty EntityBobs object
    */
    public EntityBobs (int queryPrimaryKey) {
    this.load(queryPrimaryKey);

    }
}