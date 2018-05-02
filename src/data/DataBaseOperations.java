package data;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class DataBaseOperations {

    private static final String CLOUD_DB = "mongodb://Pedro_Feiteira_Celfocus:dztsR2T97aYgQ4I5@internship-shard-00-00-b5j2n.mongodb.net:27017,internship-shard-00-01-b5j2n.mongodb.net:27017,internship-shard-00-02-b5j2n.mongodb.net:27017/test?ssl=true&replicaSet=Internship-shard-0&authSource=admin";
    private MongoClient clientDB;

    public DataBaseOperations() throws UnknownHostException {
        clientDB = connect();
    }

    private MongoClient connect() throws UnknownHostException {
        MongoClientURI uri = new MongoClientURI(CLOUD_DB);
        return new MongoClient(uri);
    }

    public void addDocument(String database, String collectionName, Document doc){
        MongoDatabase db = clientDB.getDatabase(database);
        MongoCollection coll = db.getCollection(collectionName);
        coll.insertOne(doc);
    }

    public void addManyDocs(String database, String collectionName, List<Document> docs){
        MongoCollection coll = clientDB.getDatabase(database).getCollection(collectionName);
        coll.insertMany(docs);
    }

    public void dropCollection(String database, String collectionName){
        clientDB.getDatabase(database).getCollection(collectionName).drop();
    }

    public void addDatabase(String database){
        clientDB.getDatabase(database);
    }

    public void addCollection(String database, String collection){
        clientDB.getDatabase(database).createCollection(collection);
    }

    public String getDocument(String database, String collection, String attribute, String value){
        MongoCollection coll = clientDB.getDatabase(database).getCollection(collection);
        Document doc = (Document) coll.find(eq(attribute,value)).first();
        if(doc == null){
            return null;
        }
        return doc.toJson();
    }

}