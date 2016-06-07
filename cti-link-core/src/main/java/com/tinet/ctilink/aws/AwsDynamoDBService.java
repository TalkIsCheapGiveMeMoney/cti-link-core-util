package com.tinet.ctilink.aws;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.BatchGetItemOutcome;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.TableCollection;
import com.amazonaws.services.dynamodbv2.document.TableKeysAndAttributes;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ListTablesResult;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.TableDescription;
import com.tinet.ctilink.json.JSONObject;

import java.util.*;

public class AwsDynamoDBService {
    private AmazonDynamoDB amazonDynamoDBClient;
    private DynamoDB dynamoDB;

    public void setAmazonDynamoDBClient(AmazonDynamoDB amazonDynamoDBClient) {
        this.amazonDynamoDBClient = amazonDynamoDBClient;
        this.amazonDynamoDBClient.setRegion(Region.getRegion(Regions.CN_NORTH_1));
        this.dynamoDB = new DynamoDB(this.amazonDynamoDBClient);
    }

    public void getTableInformation(String tableName) {
        TableDescription tableDescription = dynamoDB.getTable(tableName).describe();
        System.out.format("Name: %s:\n" + "Status: %s \n"
                        + "Provisioned Throughput (read capacity units/sec): %d \n"
                        + "Provisioned Throughput (write capacity units/sec): %d \n",
                tableDescription.getTableName(),
                tableDescription.getTableStatus(),
                tableDescription.getProvisionedThroughput().getReadCapacityUnits(),
                tableDescription.getProvisionedThroughput().getWriteCapacityUnits());

    }

    public void createDynamoDbTable(String tableName, String keyName, KeyType keyType, Object attributeType) {
        TableCollection<ListTablesResult> tables = dynamoDB.listTables();
        Iterator<Table> iterator = tables.iterator();
        while (iterator.hasNext()) {
            Table table = iterator.next();
            if (table.getTableName().equals(tableName)) {
                return;
            }
        }

        ArrayList<AttributeDefinition> attributeDefinitions = new ArrayList<AttributeDefinition>();
        attributeDefinitions.add(new AttributeDefinition().withAttributeName(keyName).withAttributeType(attributeType.toString()));

        ArrayList<KeySchemaElement> keySchema = new ArrayList<KeySchemaElement>();
        keySchema.add(new KeySchemaElement().withAttributeName(keyName).withKeyType(keyType));// 哈希键 名称

        CreateTableRequest request = new CreateTableRequest()
                .withTableName(tableName)
                .withKeySchema(keySchema)
                .withAttributeDefinitions(attributeDefinitions)
                .withProvisionedThroughput(new ProvisionedThroughput()
                        .withReadCapacityUnits(100L) // 读取吞吐量总和
                        .withWriteCapacityUnits(10L)); // 写入吞吐量总和

        Table table = dynamoDB.createTable(request);
        try {
            //在 DynamoDB 创建表并且将其状态设置为 ACTIVE 之前，表将无法使用
            table.waitForActive();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void updateDynamoDbTableCapacityUnits(String tableName, Long readCapacityUnits, Long writeCapacityUnits) {
        Table table = dynamoDB.getTable(tableName);
        ProvisionedThroughput provisionedThroughput = new ProvisionedThroughput()
                .withReadCapacityUnits(readCapacityUnits)
                .withWriteCapacityUnits(writeCapacityUnits);

        table.updateTable(provisionedThroughput);
        try {
            table.waitForActive();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Modifying provisioned throughput for " + tableName);
    }

    public void deleteDynamoDbTable(String tableName) {
        Table table = dynamoDB.getTable(tableName);
        table.delete();
        try {
            table.waitForDelete();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Issuing DeleteTable request for " + tableName);
    }

    public List<Table> listDynamoDbTable() {
        List<Table> list = new ArrayList<Table>();
        System.out.println("Listing table names");
        DynamoDB dynamoDB = new DynamoDB(amazonDynamoDBClient);

        TableCollection<ListTablesResult> tables = dynamoDB.listTables();
        Iterator<Table> iterator = tables.iterator();
        while (iterator.hasNext()) {
            Table table = iterator.next();
            list.add(table);
        }

        return list;
    }

    public void putItem(String tableName, Item item) {
        Table table = dynamoDB.getTable(tableName);
        // Write the item to the table
        table.putItem(item);
    }

    public void putItemByCondition(String tableName, Item item,
                                   String conditionExpression, Map<String, String> expressionAttributeNames,
                                   Map<String, Object> expressionAttributeValues) {
        try {

            Table table = dynamoDB.getTable(tableName);
            // Write the item to the table
            table.putItem(item, conditionExpression, expressionAttributeNames, expressionAttributeValues);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String getItem(String tableName, String hashKeyName, Object hashKeyValue) {
        Table table = dynamoDB.getTable(tableName);

        Item item = table.getItem(hashKeyName, hashKeyValue);

        return item.toJSONPretty();
    }

    public String getItemOnlyForProjection(String tableName,
                                           String hashKeyName, Object hashKeyValue,
                                           String projectionExpression, boolean isConsistentRead) {
        GetItemSpec spec = new GetItemSpec();
        spec.withPrimaryKey(hashKeyName, hashKeyValue);
        spec.withProjectionExpression(projectionExpression);
        spec.withConsistentRead(isConsistentRead);

        Table table = dynamoDB.getTable(tableName);

        Item item = table.getItem(spec);

        return item.toJSONPretty();
    }

    public void batchWriteItem() {
        // TODO Auto-generated method stub

    }

    public List<Item> batchGetItemHashOnlyPrimaryKey(String tableName,
                                                     String hashKeyName, Object[] hashKeyValues, String projectionExpression) {

        TableKeysAndAttributes tableKeysAndAttributes = new TableKeysAndAttributes(tableName);
        tableKeysAndAttributes.addHashOnlyPrimaryKeys(hashKeyName, hashKeyValues);
        if (null != projectionExpression && projectionExpression.length() > 0) {
            tableKeysAndAttributes.withProjectionExpression(projectionExpression);
        }

        BatchGetItemOutcome outcome = dynamoDB.batchGetItem(tableKeysAndAttributes);
        List<Item> items = outcome.getTableItems().get(tableName);
        return items;
    }

    public boolean updateItem() {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean deleteItem() {
        // TODO Auto-generated method stub
        return false;
    }

    public List<JSONObject> queryItem(String tableName, String hashKeyName, String hashKeyValue) {
        Table table = dynamoDB.getTable(tableName);

        List<JSONObject> res = new ArrayList<JSONObject>();
        ItemCollection<QueryOutcome> items = table.query(hashKeyName, hashKeyValue);
        Iterator<Item> iterator = items.iterator();

        Item item = null;
        while (iterator.hasNext()) {
            item = iterator.next();
            JSONObject object = JSONObject.fromObject(item.toJSON());
            res.add(object);
        }

        return res;
    }

    public boolean queryKeyExists(String tableName, String hashKeyName,
                                  String hashKeyValue) {
        Table table = dynamoDB.getTable(tableName);

        ItemCollection<QueryOutcome> items = table.query(hashKeyName, hashKeyValue);
        Iterator<Item> iterator = items.iterator();

        while (iterator.hasNext()) {
            return true;
        }

        return false;
    }

}
