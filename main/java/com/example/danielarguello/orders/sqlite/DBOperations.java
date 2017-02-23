package com.example.danielarguello.orders.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.example.danielarguello.orders.model.App;
import com.example.danielarguello.orders.model.Customer;
import com.example.danielarguello.orders.model.MethodPayment;
import com.example.danielarguello.orders.model.OrderDetail;
import com.example.danielarguello.orders.model.OrderHeader;
import com.example.danielarguello.orders.model.Product;



/**
 * Created by tacho on 7/02/17.
 */

public final class DBOperations {
    private static OrderDB db;
    private static DBOperations operations;

    private static final String JOIN_ORDER_CUSTOMER_METHOD =
            "order_header " +
            "INNER JOIN customer " +
            "ON order_header.id_customer = customer.id " +
            "INNER JOIN method_payment " +
            "ON order_header.id_method_payment = method_payment.id";
    private final String[] orderProj = new String[]{
            OrderDB.Tables.ORDER_HEADER + "."
                    + OrderContract.OrderHeaders.ID,
            OrderContract.OrderHeaders.DATE,
            OrderContract.Customers.FIRSTNAME,
            OrderContract.Customers.LASTNAME,
            OrderContract.MethodsPayment.NAME};

    private DBOperations(){

    }

    public static DBOperations getDBOperations(
            Context context){
        if(operations==null) {
            operations = new DBOperations();
        }
        if(db==null){
            db = new OrderDB(context);
        }
        return operations;
    }
    //Operations of  Orders
    public Cursor getOrders(){
        SQLiteDatabase database = db.getReadableDatabase();
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(JOIN_ORDER_CUSTOMER_METHOD);

        return  builder.query(database, orderProj,
                null, null, null, null, null);
    }

    public Cursor getOrderById(String id){
        SQLiteDatabase database = db.getWritableDatabase();
        String selection = String.format("%s=?",
                OrderContract.OrderHeaders.ID);
        String[] selectionArgs = {id};
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(JOIN_ORDER_CUSTOMER_METHOD);
        String[] projection = {
                OrderDB.Tables.ORDER_HEADER+"."
                        + OrderContract.OrderHeaders.ID,
                OrderContract.OrderHeaders.DATE,
                OrderContract.Customers.FIRSTNAME,
                OrderContract.Customers.LASTNAME,
                OrderContract.MethodsPayment.NAME
        };
        return builder.query(database, projection, selection,
                selectionArgs, null, null, null);
    }

    public String insertOrderHeader(OrderHeader orderHeader){
        SQLiteDatabase database = db.getWritableDatabase();
        ContentValues values = new ContentValues();
        String idOrderHeader =
                OrderContract.OrderHeaders.generateIdOrderHeader();
        values.put(OrderContract.OrderHeaders.ID, idOrderHeader);
        values.put(OrderContract.OrderHeaders.DATE,
                orderHeader.getDate());
        values.put(OrderContract.OrderHeaders.ID_CUSTOMER,
                orderHeader.getIdCustomer());
        values.put(OrderContract.OrderHeaders.ID_METHOD_PAYMENT,
                orderHeader.getIdMethodPayment());

        database.insertOrThrow(OrderDB.Tables.ORDER_HEADER,
                null, values);
        return idOrderHeader;
    }

    public boolean updateOrderHeader(OrderHeader orderHeader){
        SQLiteDatabase database = db.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(OrderContract.OrderHeaders.DATE,
                orderHeader.getDate());
        values.put(OrderContract.OrderHeaders.ID_CUSTOMER,
                orderHeader.getIdCustomer());
        values.put(OrderContract.OrderHeaders.ID_METHOD_PAYMENT,
                orderHeader.getIdMethodPayment());

        String whereClause = String.format("%s=?", OrderContract.OrderHeaders.ID);
        String[] whereArgs = {orderHeader.getIdOrderHeader()};

        int result = database.update(OrderDB.Tables.ORDER_HEADER, values, whereClause, whereArgs);
        return result>0;
    }

    public boolean deleteOrderHeader(String idOrderHeader){
        SQLiteDatabase database = db.getWritableDatabase();
        String whereClause =
                OrderContract.OrderHeaders.ID + "=?";
        String[] whereArgs = {idOrderHeader};

        int result =  database.delete(
                OrderDB.Tables.ORDER_HEADER, whereClause, whereArgs);
        return result > 0;
    }
    //Operations of  OrderDetails
    public Cursor getOrderDetails(){
        SQLiteDatabase database = db.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s",
                OrderDB.Tables.ORDER_DETAIL);
        return  database.rawQuery(sql, null);
    }

    public Cursor getOrderDetailById(String id){
        SQLiteDatabase database = db.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s" +
                " WHERE %s=?", OrderDB.Tables.ORDER_DETAIL,
                OrderContract.OrderHeaders.ID);
        String[] whereArgs = {id};
        return database.rawQuery(sql, whereArgs);
    }

    public String insertOrderDetail(OrderDetail orderDetail){
        SQLiteDatabase database = db.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(OrderContract.OrderDetails.ID,
                orderDetail.getIdOrderHeader());
        values.put(OrderContract.OrderDetails.SEQUENCE,
                orderDetail.getSequence());
        values.put(OrderContract.OrderDetails.ID_PRODUCT,
                orderDetail.getIdProduct());
        values.put(OrderContract.OrderDetails.QUANTITY,
                orderDetail.getQuantity());
        values.put(OrderContract.OrderDetails.PRICE,
                orderDetail.getPrice());

        database.insertOrThrow(OrderDB.Tables.ORDER_DETAIL,
                null, values);
        return String.format("%s#%d",
                orderDetail.getIdOrderHeader(),
                orderDetail.getSequence());
    }

    public boolean updateOrderDetail(OrderDetail orderDetail){
        SQLiteDatabase database = db.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(OrderContract.OrderDetails.SEQUENCE,
                orderDetail.getSequence());
        values.put(OrderContract.OrderDetails.QUANTITY,
                orderDetail.getQuantity());
        values.put(OrderContract.OrderDetails.PRICE,
                orderDetail.getPrice());

        String whereClause = String.format("%s=? AND %s=?",
                OrderContract.OrderDetails.ID,
                OrderContract.OrderDetails.SEQUENCE);
        String[] whereArgs = {orderDetail.getIdOrderHeader(),
        String.valueOf(orderDetail.getSequence())};

        int result = database.update(OrderDB.Tables.ORDER_DETAIL,
                values, whereClause, whereArgs);
        return result>0;
    }

    public boolean deleteOrderDetail(String idOrderDetail){
        SQLiteDatabase database = db.getWritableDatabase();
        String whereClause =
                OrderContract.OrderHeaders.ID + "=?";
        String[] whereArgs = {idOrderDetail};

        int result =  database.delete(
                OrderDB.Tables.ORDER_DETAIL, whereClause, whereArgs);
        return result > 0;
    }
    //Operations of  Products
    public Cursor getProducts(){
        SQLiteDatabase database = db.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s",
                OrderDB.Tables.PRODUCT);
        return  database.rawQuery(sql, null);
    }

    public Cursor getProductById(String id){
        SQLiteDatabase database = db.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s" +
                        " WHERE %s=?",
                OrderDB.Tables.PRODUCT,
                OrderContract.Products.ID);
        String[] whereArgs = {id};
        return database.rawQuery(sql, whereArgs);
    }

    public String insertProduct(Product product){
        SQLiteDatabase database = db.getWritableDatabase();
        String idProduct = OrderContract.Products.generateIdProduct();
        ContentValues values = new ContentValues();
        values.put(OrderContract.Products.ID, idProduct);
        values.put(OrderContract.Products.NAME, product.getName());
        values.put(OrderContract.Products.PRICE, product.getPrice());
        values.put(OrderContract.Products.STOCK, product.getStock());
        database.insertOrThrow(OrderDB.Tables.PRODUCT, null, values);
        return idProduct;
    }

    public boolean updateProduct(Product product){
        SQLiteDatabase database = db.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(OrderContract.Products.NAME, product.getName());
        values.put(OrderContract.Products.PRICE, product.getPrice());
        values.put(OrderContract.Products.STOCK, product.getStock());
        String whereClause = String.format("%s=?", OrderContract.Products.ID);
        String[] whereArgs = {product.getIdProduct()};
        int result = database.update(OrderDB.Tables.PRODUCT,
                values, whereClause, whereArgs);
        return result>0;
    }

    public boolean deleteProduct(String idProduct){
        SQLiteDatabase database = db.getWritableDatabase();
        String whereClause = OrderContract.Products.ID + "=?";
        String[] whereArgs = {idProduct};
        int result =  database.delete(OrderDB.Tables.PRODUCT,
                whereClause, whereArgs);
        return result > 0;
    }
    // Operations Customers
    public Cursor getCustomers() {
        SQLiteDatabase database = db.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s",
                OrderDB.Tables.CUSTOMER);
        return database.rawQuery(sql, null);
    }
    public Cursor getCustomersById(String idCustomer) {
        SQLiteDatabase database = db.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s WHERE %s=?",
                OrderDB.Tables.CUSTOMER,
                OrderContract.Customers.ID);
        String[] whereArgs ={idCustomer};
        return database.rawQuery(sql, whereArgs);
    }

    public String insertCustomer(Customer customer) {
        SQLiteDatabase database = db.getWritableDatabase();
        String idCustomer = OrderContract.Customers.generateIdCustomer();
        ContentValues values = new ContentValues();
        values.put(OrderContract.Customers.ID, idCustomer);
        values.put(OrderContract.Customers.FIRSTNAME, customer.getFirstname());
        values.put(OrderContract.Customers.LASTNAME, customer.getLastname());
        values.put(OrderContract.Customers.PHONE, customer.getPhone());

        return database.insertOrThrow(OrderDB.Tables.CUSTOMER,
                null, values) > 0 ? idCustomer : null;
    }

    public boolean updateCustomer(Customer customer) {
        SQLiteDatabase database = db.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(OrderContract.Customers.FIRSTNAME,
                customer.getFirstname());
        values.put(OrderContract.Customers.LASTNAME,
                customer.getLastname());
        values.put(OrderContract.Customers.PHONE,
                customer.getPhone());
        String whereClause = String.format("%s=?",
                OrderContract.Customers.ID);
        final String[] whereArgs = {customer.getIdCustomer()};
        int result = database.update(OrderDB.Tables.CUSTOMER,
                values, whereClause, whereArgs);
        return result > 0;
    }
    public boolean deleteCustomer(String idCustomer) {
        SQLiteDatabase database = db.getWritableDatabase();
        String whereClause = String.format("%s=?",
                OrderContract.Customers.ID);
        final String[] whereArgs = {idCustomer};
        int result = database.delete(OrderDB.Tables.CUSTOMER, whereClause, whereArgs);
        return result > 0;
    }

    // Operation Method of payment
    public Cursor getMethodsPayment() {
        SQLiteDatabase database = db.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s",
                OrderDB.Tables.METHOD_PAYMENT);
        return database.rawQuery(sql, null);
    }

    public Cursor getMethodsPaymentById(String idMethodPayment) {
        SQLiteDatabase database = db.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s WHERE %s=?",
                OrderDB.Tables.METHOD_PAYMENT,
                OrderContract.MethodsPayment.ID);
        String[] whereArgs = {idMethodPayment};
        return database.rawQuery(sql, null);
    }
    public String insertMethodPayment(MethodPayment methodPayment) {
        SQLiteDatabase database = db.getWritableDatabase();

        String idMethodPayment = OrderContract.MethodsPayment.generateIdMethodPayment();

        ContentValues values = new ContentValues();
        values.put(OrderContract.MethodsPayment.ID, idMethodPayment);
        values.put(OrderContract.MethodsPayment.NAME, methodPayment.getName());

        return database.insertOrThrow(
                OrderDB.Tables.METHOD_PAYMENT, null,
                values) > 0 ? idMethodPayment : null;
    }

    public boolean updateMethodPayment(MethodPayment methodPayment) {
        SQLiteDatabase database = db.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(OrderContract.MethodsPayment.NAME,
                methodPayment.getName());
        String whereClause = String.format("%s=?",
                OrderContract.MethodsPayment.ID);
        String[] whereArgs = {methodPayment.getIdMethodPayment()};
        int result = database.update(
                OrderDB.Tables.METHOD_PAYMENT, values,
                whereClause, whereArgs);
        return result > 0;
    }

    public boolean deleteMethodPayment(String idMethodPayment) {
        SQLiteDatabase database = db.getWritableDatabase();
        String whereClause = String.format("%s=?", OrderContract.MethodsPayment.ID);
        String[] whereArgs = {idMethodPayment};
        int result = database.delete(OrderDB.Tables.METHOD_PAYMENT, whereClause, whereArgs);
        return result > 0;
    }

    //Operations of  App
    public Cursor getApps(){
        SQLiteDatabase database = db.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s",
                OrderDB.Tables.APP);
        return  database.rawQuery(sql, null);
    }

    public Cursor getAppById(String id){
        SQLiteDatabase database = db.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s" +
                        " WHERE %s=?",
                OrderDB.Tables.APP,
                OrderContract.Apps.ID);
        String[] whereArgs = {id};
        return database.rawQuery(sql, whereArgs);
    }

    public String insertApp(App app){
        SQLiteDatabase database = db.getWritableDatabase();
        String idApp = OrderContract.Apps.generateIdApp();
        ContentValues values = new ContentValues();
        values.put(OrderContract.Apps.ID, idApp);
        values.put(OrderContract.Apps.TITLE, app.getTitle());
        values.put(OrderContract.Apps.DESCRIPTION, app.getDescription());
        database.insertOrThrow(OrderDB.Tables.APP, null, values);
        return idApp;
    }

    public boolean updateApp(App app){
        SQLiteDatabase database = db.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(OrderContract.Apps.TITLE, app.getTitle());
        values.put(OrderContract.Apps.DESCRIPTION, app.getDescription());
        String whereClause = String.format("%s=?", OrderContract.Apps.ID);
        String[] whereArgs = {app.getIdApp()};
        int result = database.update(OrderDB.Tables.APP,
                values, whereClause, whereArgs);
        return result>0;
    }

    public boolean deleteApp(String idApp){
        SQLiteDatabase database = db.getWritableDatabase();
        String whereClause = OrderContract.Apps.ID + "=?";
        String[] whereArgs = {idApp};
        int result =  database.delete(OrderDB.Tables.APP,
                whereClause, whereArgs);
        return result > 0;
    }


    public SQLiteDatabase getDb() {
        return db.getWritableDatabase();
    }


}
