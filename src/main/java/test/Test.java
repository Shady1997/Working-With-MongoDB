package test;

import com.mongodb.*;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.*;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;

import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Arrays;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;

public class Test {

	public static byte[] LoadImage(String filePath) throws Exception {
        File file = new File(filePath);
        int size = (int)file.length();
        byte[] buffer = new byte[size];
        FileInputStream in = new FileInputStream(file);
        in.read(buffer);
        in.close();
        return buffer;
    }

    public static void main(String[] args) throws Exception {
        //Load our image
        byte[] imageBytes = LoadImage("C:/AI/in/tt.jpg");
        //Connect to database
        Mongo mongo = new Mongo( "mongodb://localhost:27017" );
        String dbName = "Test";
        DB db = mongo.getDB( dbName );
        //Create GridFS object
        GridFS fs = new GridFS( db );
        //Save image into database
        GridFSInputFile in = fs.createFile( imageBytes );
        in.save();

        //Find saved image
        GridFSDBFile out = fs.findOne( new BasicDBObject( "_id" , in.getId() ) );

        //Save loaded image from database into new image file
        FileOutputStream outputImage = new FileOutputStream("C:/AI/out/ss.jpg");
        out.writeTo( outputImage );
        outputImage.close();
        System.out.println("success");
    }
		

//		MongoClient client = MongoClients.create("mongodb://localhost:27017");
//		MongoDatabase database = client.getDatabase("Test");
//		MongoCollection<Document> users = database.getCollection("users");
////		Document addNewUser = new Document("url","shady@test.com").append("ages", new Document("min", 5));
//		Document addNewUser = new Document("email","shady@yahooo.com").append("password", "P@ssw0rd@123").append("username", "shady1997");
//		ObjectId id = users.insertOne(addNewUser).getInsertedId().asObjectId().getValue();
//		Document getUser = users.find(new Document("email", "shady@yahooo.com")).first();
//		System.out.println(getUser.toJson()+"  "+id.toString());
//		System.out.println(getUser.get("email"));
//		System.out.println(getUser.get("username"));
//		System.out.println(getUser.get("password"));
//		try {
//		users.deleteMany(getUser);
//		System.out.println("deleted successfully");
//		}catch (Exception e) {
//			// TODO: handle exception
//		}
//	}
//	
//	public MongoDatabase connecTomongoDB(String mongourl, String databaseName) {
//		MongoClient client = MongoClients.create("mongodb://localhost:27017");
//		MongoDatabase database = client.getDatabase("Test");
//		return database;
//	}

}
