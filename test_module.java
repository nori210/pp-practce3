import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseTestSuite {

    private JDBCConnectionMaker connectionMaker;
    private EntityBits entityBits;

    @BeforeEach
    public void setUp() {
        // Initialize with valid parameters before each test
        connectionMaker = new JDBCConnectionMaker(
                "testDB",
                "localhost",
                "3306",
                "testUser",
                "testPass"
        );
        EntityBits.setDbConnector(connectionMaker);
        entityBits = new EntityBits();
    }

    @AfterEach
    public void tearDown() throws SQLException {
        // Clean up the table for the next test
        Statement statement = connection.createStatement();
        statement.execute("TRUNCATE TABLE Bits;");
    }

    @AfterAll
    public static void tearDownClass() throws SQLException {
        // Close the in-memory database
        connection.close();
    }

    @Test
    public void testPrimaryKeyColumnName() throws Exception {
        // Use reflection to access the protected method
        Method method = EntityBits.class.getDeclaredMethod("primaryKeyColumnName");
        method.setAccessible(true);

        // Invoke the method and check the result
        String columnName = (String) method.invoke(entityBits);
        assertEquals("Bits_pk", columnName, "The primary key column name should match");
    }

    @Test
    public void testPrimaryKeyColumnIndex() throws Exception {
        // Use reflection to access the protected method
        Method method = EntityBits.class.getDeclaredMethod("primaryKeyColumnIndex");
        method.setAccessible(true);

        // Invoke the method and check the result
        int columnIndex = (int) method.invoke(entityBits);
        assertEquals(1, columnIndex, "The primary key column index should match");
    }

    @Test
    public void testPrimaryKeyValue() throws Exception {
        // Assuming you have a setter for the primary key in EntityBits or a way to set it up
        // For the purpose of this test, let's assume there's a method setPrimaryKey(int pk)
        // which is public for setting up the primary key. If not, you'll need to set it via constructor
        // or any other means necessary.
        int expectedPrimaryKey = 123; // Example primary key value
        entityBits.setPrimaryKey(expectedPrimaryKey); // This method needs to be implemented

        // Use reflection to access the protected method
        Method method = EntityBits.class.getDeclaredMethod("primaryKey");
        method.setAccessible(true);

        // Invoke the method and check the result
        int primaryKeyValue = (int) method.invoke(entityBits);
        assertEquals(expectedPrimaryKey, primaryKeyValue, "The primary key value should match");
    }

    @Test
    public void testPopulateFromResultSet() throws SQLException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        // Retrieve the inserted data as a ResultSet
        PreparedStatement selectStatement = connection.prepareStatement("SELECT * FROM Bits WHERE Bits_pk = ?");
        selectStatement.setInt(1, 1);
        ResultSet resultSet = selectStatement.executeQuery();
        assertTrue(resultSet.next(), "A row must be present in the ResultSet");

        // Use reflection to access the protected method
        Method method = EntityBits.class.getDeclaredMethod("populateFromResultSet", ResultSet.class);
        method.setAccessible(true);

        // Invoke the method with the actual ResultSet
        boolean result = (boolean) method.invoke(entityBits, resultSet);
        assertTrue(result, "populateFromResultSet should return true on success");

        // Optionally, verify that entityBits fields are set correctly
        // This assumes you have getters or can access the fields directly
        assertEquals(10, entityBits.getMn(), "Mn field should be set correctly");
        assertEquals(EntityBits.OPValues.i, entityBits.getOp(), "Op field should be set correctly");
        // For the date, you may need to adjust the assertion depending on how you're handling date/time conversion
        assertNotNull(entityBits.getQr(), "Qr field should be set correctly");
    }

    // test to check invalid connection
    @Test
    public void testValidConnectionParameters() {
        Connection connection = connectionMaker.getConnection();
        assertNotNull(connection, "Connection should be established with valid parameters");
    }

    // test to check valid connection
    @Test
    public void testInvalidConnectionParameters() {
        JDBCConnectionMaker badConnectionMaker = new JDBCConnectionMaker(
                "invalidDB",
                "unknownHost",
                "invalidPort",
                "invalidUser",
                "invalidPass"
        );
        Connection badConnection = badConnectionMaker.getConnection();
        assertNull(badConnection, "Connection should fail with invalid parameters");
    }

    // test to check EntityBits schema creation
    @Test
    public void testCreateEntityBits() {
        // Assuming the create method is correctly implemented
        boolean result = entityBits.create();
        assertTrue(result, "EntityBits should be created successfully");
    }

    // test to check reading from EntityBits table
    @Test
    public void testReadEntityBits() {
        // Assuming the read method is correctly implemented
        boolean result = entityBits.read(1); // Assuming '1' is a valid primary key
        assertTrue(result, "EntityBits should be read successfully");
    }

    // test to check update to the EntityBits 
    @Test
    public void testUpdateEntityBits() {
        // Assuming the update method is correctly implemented
        boolean result = entityBits.update();
        assertTrue(result, "EntityBits should be updated successfully");
    }

    // test to check deletion of a record in the EntityBit
    @Test
    public void testDeleteEntityBits() {
        // Assuming the delete method is correctly implemented
        boolean result = entityBits.delete();
        assertTrue(result, "EntityBits should be deleted successfully");
    }

    // test to check if a record successfully saved into the EntityBits schema
    @Test
    public void testSaveEntityBits() {
        // Assuming the save method is correctly implemented
        boolean result = entityBits.save();
        assertTrue(result, "EntityBits should be saved successfully");
    }

    // test to chekc if records are successfully loaded form the EntityBits schema
    @Test
    public void testLoadEntityBits() {
        // Assuming the load method is correctly implemented
        boolean result = entityBits.load(1); // Assuming '1' is a valid primary key
        assertTrue(result, "EntityBits should be loaded successfully");
    }

    //test to check if records are successfully deleted form the EntityBits schema
    @Test
    public void testRemoveEntityBits() {
        // Assuming the remove method is correctly implemented
        boolean result = entityBits.remove();
        assertTrue(result, "EntityBits should be removed successfully");
    }

  
}