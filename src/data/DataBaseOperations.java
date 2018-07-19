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

/**
 * @author Pedro Feiteira, n48119
 * This class has the MongoDB needed operations
 * For more information -> http://mongodb.github.io/mongo-java-driver/3.7/driver/getting-started/quick-start/
 */
public class DataBaseOperations {

    private static final String CLOUD_DB = "mongodb://Pedro_Feiteira_Celfocus:dztsR2T97aYgQ4I5@internship-shard-00-00-b5j2n.mongodb.net:27017,internship-shard-00-01-b5j2n.mongodb.net:27017,internship-shard-00-02-b5j2n.mongodb.net:27017/test?ssl=true&replicaSet=Internship-shard-0&authSource=admin";
    private MongoClient clientDB;

    public DataBaseOperations() {
        clientDB = connect();
    }

    private MongoClient connect() {
        MongoClientURI uri = new MongoClientURI(CLOUD_DB);
        return new MongoClient(uri);
    }

    public void addDocument(String database, String collectionName, Document doc) {
        MongoDatabase db = clientDB.getDatabase(database);
        MongoCollection coll = db.getCollection(collectionName);
        coll.insertOne(doc);
    }

    public void addManyDocs(String database, String collectionName, List<Document> docs) {
        MongoCollection coll = clientDB.getDatabase(database).getCollection(collectionName);
        coll.insertMany(docs);
    }

    public void dropCollection(String database, String collectionName) {
        clientDB.getDatabase(database).getCollection(collectionName).drop();
    }

    public void dropDatabase(String database) {
        clientDB.getDatabase(database).drop();
    }

    public void addDatabase(String database) {
        clientDB.getDatabase(database);
    }

    public void addCollection(String database, String collection) {
        clientDB.getDatabase(database).createCollection(collection);
    }

    public int listDB() {
        int result = 0;
        for (String s : clientDB.listDatabaseNames()) {
            result++;
            System.out.println(s);
        }
        return result;
    }

    public String getDocument(String database, String collection, String attribute, String value) {
        MongoCollection coll = clientDB.getDatabase(database).getCollection(collection);
        Document doc = (Document) coll.find(eq(attribute, value)).first();
        if (doc == null) {
            return null;
        }
        return doc.toJson();
    }

    public void updateDocument(String database, String collection, String attribute, String value, Document newDoc) {
        MongoCollection coll = clientDB.getDatabase(database).getCollection(collection);
        coll.updateOne(eq(attribute, value), new Document("$set", newDoc));
    }

    public List<Document> getAllCollection(String database, String collection) {
        MongoCollection coll = clientDB.getDatabase(database).getCollection(collection);
        List<Document> list = new LinkedList<>();
        MongoCursor<Document> cursor = coll.find().iterator();
        try {
            while (cursor.hasNext()) {
                list.add(cursor.next());
            }
        } finally {
            cursor.close();
        }
        return list;
    }
}
