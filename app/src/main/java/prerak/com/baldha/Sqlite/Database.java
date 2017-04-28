package prerak.com.baldha.Sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import prerak.com.baldha.model.login.GetArea.Area;
import prerak.com.baldha.model.login.GetArea.AreaList;
import prerak.com.baldha.model.login.OrderModel.Order;
import prerak.com.baldha.model.login.OrderModel.OrderInsert;
import prerak.com.baldha.model.login.getProduct.Product;
import prerak.com.baldha.model.login.getcategory.Category;
import prerak.com.baldha.model.login.getshop.Shop;

/**
 * Created by AMD21 on 18/4/17.
 */

public class Database extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "db_baldha";


    //MultipleUse Variable
    private static final String ID = "id";

    //Area variable and query
    private static final String TABLE_AREA = "Area";
    private static final String AREA_ID = "AreaId";
    private static final String AREA_NAME = "Area";

    String CREATE_AREA_TABLE = "CREATE TABLE " + TABLE_AREA + "("
            + ID + " INTEGER PRIMARY KEY," + AREA_ID + " TEXT,"
            + AREA_NAME + " TEXT" + ")";


    //Shop Variable and query
    private static final String TABLE_SHOP = "Shop";
    private static final String SHOPID = "shopId";
    private static final String SHOPNAME = "shopName";
    private static final String OWNERNAME = "ownerName";
    private static final String ADDRESS = "address";
    private static final String CITYID = "cityID";
    private static final String CONTACTNO = "contactNO";
    private static final String IMAGE = "image";
    private static final String LAT = "lat";
    private static final String LON = "lon";
    private static final String AREAID = "areaID";

    private static final String TABLE_NEW_SHOP = "Shop_new";

    String CREATE_SHOP_TABLE = "CREATE TABLE " + TABLE_SHOP + "("
            + ID + " INTEGER PRIMARY KEY," + SHOPID + " TEXT,"
            + SHOPNAME + " TEXT," + OWNERNAME + " TEXT," + ADDRESS + " TEXT," + CITYID + " TEXT," + CONTACTNO + " TEXT," + IMAGE + " TEXT," + LAT + " TEXT," + LON + " TEXT," + AREAID + " TEXT" + ")";

    String CREATE_NEW_SHOP_TABLE = "CREATE TABLE " + TABLE_NEW_SHOP + "("
            + ID + " INTEGER PRIMARY KEY," + SHOPID + " TEXT,"
            + SHOPNAME + " TEXT," + OWNERNAME + " TEXT," + ADDRESS + " TEXT," + CITYID + " TEXT," + CONTACTNO + " TEXT," + IMAGE + " TEXT," + LAT + " TEXT," + LON + " TEXT," + AREAID + " TEXT" + ")";


    //Category Variable and query
    private static final String TABLE_CATEGORY = "Category";
    private static final String CATEGORYID = "CategoryId";
    private static final String CATEGORYNAME = "CategoryName";
    private static final String CATEGORYDESCRIPTION = "CategoryDescription";

    String CREATE_CATEGORY_TABLE = "CREATE TABLE " + TABLE_CATEGORY + "("
            + ID + " INTEGER PRIMARY KEY," + CATEGORYID + " TEXT,"
            + CATEGORYNAME + " TEXT," + CATEGORYDESCRIPTION + " TEXT" + ")";

    //Product Variable and query
    private static final String TABLE_PRODUCT = "Product";
    private static final String PRODUCTID = "ProductId";
    private static final String PRODUCTNAME = "ProductName";
    private static final String PRODUCTDESCRIPTION = "ProductDescription";

    String CREATE_PRODUCT_TABLE = "CREATE TABLE " + TABLE_PRODUCT + "("
            + ID + " INTEGER PRIMARY KEY," + PRODUCTID + " TEXT,"
            + PRODUCTNAME + " TEXT," + PRODUCTDESCRIPTION + " TEXT," + CATEGORYID + " TEXT" + ")";


    // Order Variable and query
//    productMap.put("userID", USERID);
//        productMap.put("shopID", shopID);
//        productMap.put("productID", ProductID);
//        productMap.put("categoryID", CategoryID);
//        productMap.put("batterylvl", String.valueOf(level));
//        productMap.put("lat", String.valueOf(Lat));
//        productMap.put("long", String.valueOf(Lon));
//        productMap.put("qnt", Quantity);

    private static final String TABLE_ORDER = "Order";
    private static final String BETTERYLEVEL = "BetteryLevel";
    private static final String QUANTITY = "Quantity";

    String CREATE_ORDER_TABLE = "CREATE TABLE " + TABLE_ORDER + "(" + ID + " INTEGER PRIMARY KEY," + SHOPID + " TEXT," + PRODUCTID + " TEXT," + CATEGORYID + " TEXT," + BETTERYLEVEL + " TEXT," + LAT + " TEXT," + LON + " TEXT," + QUANTITY + " TEXT)";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

//        CreateAreaTable();

        db.execSQL(CREATE_AREA_TABLE);
        db.execSQL(CREATE_SHOP_TABLE);
        db.execSQL(CREATE_CATEGORY_TABLE);
        db.execSQL(CREATE_PRODUCT_TABLE);
        db.execSQL(CREATE_NEW_SHOP_TABLE);
        db.execSQL(CREATE_ORDER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


        onCreate(db);
    }


//    //Create Table
//    public void CreateAreaTable() {
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.execSQL(CREATE_AREA_TABLE);
//    }

    //Insert Area
    public void InsertArea(AreaList area) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(AREA_ID, area.getAreaID());
        values.put(AREA_NAME, area.getAreaName());

        // Inserting Row
        db.insert(TABLE_AREA, null, values);
        db.close();
    }

    // Get AreaList
    public List<AreaList> getAllArea() {
        List<AreaList> areaList = new ArrayList<AreaList>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_AREA;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                AreaList area = new AreaList();
                area.setAreaID(cursor.getString(1));
                area.setAreaName(cursor.getString(2));
                // Adding Area to list
                areaList.add(area);
            } while (cursor.moveToNext());
        }

        // return contact list
        return areaList;
    }

    //get Area Count
    public int getAreaCount() {
        String countQuery = "SELECT  * FROM " + TABLE_AREA;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();

        // return count
        return count;
    }

    //Delete Area()
    public void deleteArea() {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DELETE FROM " + TABLE_AREA);
        db.close();
    }

//    //Drop Area Table()
//    public void deleteAllArea() {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        db.execSQL("TRUNCATE table " + TABLE_AREA);
//        db.close();
//    }


    //Insert Area
    public void InsertShop(Shop shop) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SHOPID, shop.getShopID());
        values.put(SHOPNAME, shop.getShopName());
        values.put(OWNERNAME, shop.getOwnerName());
        values.put(ADDRESS, shop.getAddress());
        values.put(CITYID, shop.getCityID());
        values.put(CONTACTNO, shop.getContactNO());
        values.put(IMAGE, shop.getImage());
        values.put(LAT, shop.getLat());
        values.put(LON, shop.getLong());
        values.put(AREA_ID, shop.getAreaID());

        // Inserting Row
        db.insert(TABLE_SHOP, null, values);
        db.close();
    }

    // Get ShopList
    public ArrayList<Shop> getAllShop() {
        ArrayList<Shop> shopList = new ArrayList<Shop>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_SHOP;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Shop shop = new Shop();
                shop.setShopID(cursor.getString(1));
                shop.setShopName(cursor.getString(2));
                shop.setOwnerName(cursor.getString(3));
                shop.setAddress(cursor.getString(4));
                shop.setCityID(cursor.getString(5));
                shop.setContactNO(cursor.getString(6));
                shop.setImage(cursor.getString(7));
                shop.setLat(cursor.getString(8));
                shop.setLong(cursor.getString(9));

                // Adding Area to list
                shopList.add(shop);
            } while (cursor.moveToNext());
        }

        // return contact list
        return shopList;
    }

    //get Shop Count
    public int getShopCount() {
        String countQuery = "SELECT  * FROM " + TABLE_SHOP;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();

        // return count
        return count;
    }

    //Delete Shop()
    public void deleteShop() {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DELETE FROM " + TABLE_SHOP);
        db.close();
    }

    //Drop Area Table()
//    public void deleteAllShop() {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        db.execSQL("TRUNCATE table " + TABLE_SHOP);
//        db.close();
//    }

    //Insert Category
    public void InsertCategory(Category category) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CATEGORYID, category.getCatID());
        values.put(CATEGORYNAME, category.getCatName());
        values.put(CATEGORYDESCRIPTION, category.getCatDescription());

        // Inserting Row
        db.insert(TABLE_CATEGORY, null, values);
        db.close();
    }

    // Get CategoryList
    public List<Category> getAllCategory() {
        List<Category> categoryList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CATEGORY;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Category Cat = new Category();
                Cat.setCatID(cursor.getString(1));
                Cat.setCatName(cursor.getString(2));
                Cat.setCatDescription(cursor.getString(3));
                // Adding Area to list
                categoryList.add(Cat);
            } while (cursor.moveToNext());
        }

        // return Category list
        return categoryList;
    }

    //get category Count
    public int getCategoryCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CATEGORY;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();

        // return count
        return count;
    }

    //Delete Category()
    public void deleteCategory() {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DELETE FROM " + TABLE_CATEGORY);
        db.close();
    }

//    //Drop Area Table()
//    public void deleteAllArea() {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        db.execSQL("TRUNCATE table " + TABLE_AREA);
//        db.close();
//    }


    //Insert Product
    public void InsertProduct(Product product, String CatID) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PRODUCTID, product.getProductID());
        values.put(PRODUCTNAME, product.getProductName());
        values.put(PRODUCTDESCRIPTION, product.getProductDescription());
        values.put(CATEGORYID, CatID);

        // Inserting Row
        db.insert(TABLE_PRODUCT, null, values);
        db.close();
    }

    // Get ProductList
    public List<Product> getAllProduct(String CatID) {
        List<Product> ProductList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_PRODUCT + " WHERE " + CATEGORYID + " = " + CatID;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Product product = new Product();
                product.setProductID(cursor.getString(1));
                product.setProductName(cursor.getString(2));
                product.setProductDescription(cursor.getString(3));
                // Adding Area to list
                ProductList.add(product);
            } while (cursor.moveToNext());
        }

        // return Product list
        return ProductList;
    }

    //get Product Count
    public int getProductCount(String ID) {
        String countQuery = "SELECT  * FROM " + TABLE_PRODUCT + " WHERE " + CATEGORYID + " = " + ID;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();

        // return count
        return count;
    }


    //Delete Product()
    public void deleteProduct() {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DELETE FROM " + TABLE_PRODUCT);
        db.close();
    }


    //New Added Shop
    public void InsertNewShop(Shop shop) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SHOPID, shop.getShopID());
        values.put(SHOPNAME, shop.getShopName());
        values.put(OWNERNAME, shop.getOwnerName());
        values.put(ADDRESS, shop.getAddress());
        values.put(CITYID, shop.getCityID());
        values.put(CONTACTNO, shop.getContactNO());
        values.put(IMAGE, shop.getImage());
        values.put(LAT, shop.getLat());
        values.put(LON, shop.getLong());
        values.put(AREA_ID, shop.getAreaID());

        // Inserting Row
        db.insert(TABLE_NEW_SHOP, null, values);
        db.close();
    }

    // Get ShopList
    public ArrayList<Shop> getAllNewShop() {
        ArrayList<Shop> shopList = new ArrayList<Shop>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NEW_SHOP;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Shop shop = new Shop();
                shop.setID(cursor.getString(0));
                shop.setShopID(cursor.getString(1));
                shop.setShopName(cursor.getString(2));
                shop.setOwnerName(cursor.getString(3));
                shop.setAddress(cursor.getString(4));
                shop.setCityID(cursor.getString(5));
                shop.setContactNO(cursor.getString(6));
                shop.setImage(cursor.getString(7));
                shop.setLat(cursor.getString(8));
                shop.setLong(cursor.getString(9));
                shop.setAreaID(cursor.getString(10));

                // Adding Area to list
                shopList.add(shop);
            } while (cursor.moveToNext());
        }

        // return contact list
        return shopList;
    }

    //get Shop Count
    public int getNewShopCount() {
        String countQuery = "SELECT  * FROM " + TABLE_NEW_SHOP;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();

        // return count
        return count;
    }

    //Delete Record()
    public void deleteNewShop(String Id) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DELETE FROM " + TABLE_NEW_SHOP + " WHERE " + ID + " = " + Id);
        db.close();
    }


    // Order Functions

    //New Added Shop
    public void InsertOrder(OrderInsert order) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SHOPID, order.getSHOPID());
        values.put(PRODUCTID, order.getPRODUCTID());
        values.put(CATEGORYID, order.getCATEGORYID());
        values.put(BETTERYLEVEL, order.getBETTERYLEVEL());
        values.put(LAT, order.getLAT());
        values.put(LON, order.getLON());
        values.put(QUANTITY, order.getQUANTITY());


        // Inserting Row
        db.insert(TABLE_ORDER, null, values);
        db.close();
    }

    // Get ShopList
    public ArrayList<OrderInsert> getAllOrder() {
        ArrayList<OrderInsert> OrderList = new ArrayList<OrderInsert>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_ORDER;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                OrderInsert order = new OrderInsert();
                order.setID(cursor.getString(0));
                order.setSHOPID(cursor.getString(1));
                order.setPRODUCTID(cursor.getString(2));
                order.setCATEGORYID(cursor.getString(3));
                order.setBETTERYLEVEL(cursor.getString(4));
                order.setLAT(cursor.getString(5));
                order.setLON(cursor.getString(6));
                order.setQUANTITY(cursor.getString(7));


                // Adding Area to list
                OrderList.add(order);
            } while (cursor.moveToNext());
        }

        // return contact list
        return OrderList;
    }

    //get Shop Count
    public int getNewOrderCount() {
        String countQuery = "SELECT  * FROM " + TABLE_ORDER;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();

        // return count
        return count;
    }

    //Delete Record()
    public void deleteNewOrder(String Id) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DELETE FROM " + TABLE_ORDER + " WHERE " + ID + " = " + Id);
        db.close();
    }

    //Delete Product()
    public void deleteOrder() {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DELETE FROM " + TABLE_ORDER);
        db.close();
    }


}
