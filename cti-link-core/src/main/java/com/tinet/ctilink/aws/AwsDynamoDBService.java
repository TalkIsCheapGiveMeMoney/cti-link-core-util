package com.tinet.ctilink.aws;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.utils.NameMap;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.*;
import com.tinet.ctilink.util.ContextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;


public class AwsDynamoDBService {
    private Logger logger = LoggerFactory.getLogger(getClass());

    private DynamoDB dynamoDB;

    public void setAmazonDynamoDB(AmazonDynamoDB amazonDynamoDB) {
        amazonDynamoDB.setRegion(Region.getRegion(Regions.CN_NORTH_1));
        this.dynamoDB = new DynamoDB(amazonDynamoDB);
    }

    public TableDescription describeTable(String tableName) {
        return dynamoDB.getTable(tableName).describe();
    }

    public List<Table> listTables() {
        List<Table> list = new ArrayList<>();

        TableCollection<ListTablesResult> tables = dynamoDB.listTables();
        for (Table table : tables) {
            list.add(table);
        }

        return list;
    }

    public PutItemOutcome putItem(String tableName, Item item) {
        Table table = dynamoDB.getTable(tableName);
        return table.putItem(item);
    }

    public PutItemOutcome putItem(String tableName, Item item, String conditionExpression
            , Map<String, String> nameMap, Map<String, Object> valueMap) {
        Table table = dynamoDB.getTable(tableName);
        // Write the item to the table
        return table.putItem(item, conditionExpression, nameMap, valueMap);
    }

    public ItemCollection<QueryOutcome> query(String tableName, String indexName) {
        try {
            Table table = dynamoDB.getTable(tableName);
            Index index = table.getIndex(indexName);
            NameMap name = new NameMap();
            name.put("#enterpriseId", "enterpriseId");

            ValueMap value = new ValueMap();
            value.put(":enterpriseId", 6000001);
            QuerySpec querySpec = new QuerySpec();

            querySpec.withKeyConditionExpression("#enterpriseId = :enterpriseId")
                .withNameMap(name)
                .withValueMap(value);
            return index.query(querySpec);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Table createTable(String tableName, ArrayList<KeySchemaElement> keySchemaElements, ArrayList<AttributeDefinition> attributeDefinitions
            , ArrayList<LocalSecondaryIndex> localSecondaryIndexes) {

        TableCollection<ListTablesResult> tables = dynamoDB.listTables();
        for (Table table : tables) {
            if (table.getTableName().equals(tableName)) {
                return null;
            }
        }

        CreateTableRequest request = new CreateTableRequest()
                .withTableName(tableName)
                .withKeySchema(keySchemaElements)
                .withAttributeDefinitions(attributeDefinitions)
                .withLocalSecondaryIndexes(localSecondaryIndexes)
                .withProvisionedThroughput(new ProvisionedThroughput()
                        .withReadCapacityUnits(5L) // 读取吞吐量总和
                        .withWriteCapacityUnits(5L)); // 写入吞吐量总和

        Table table = dynamoDB.createTable(request);
        try {
            //在 DynamoDB 创建表并且将其状态设置为 ACTIVE 之前，表将无法使用
            table.waitForActive();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public DeleteTableResult deleteTable(String tableName) {
        Table table = dynamoDB.getTable(tableName);
        if (table != null) {
            table.delete();
            try {
                table.waitForDelete();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public TableDescription updateCapacityUnits(String tableName, Long readCapacityUnits, Long writeCapacityUnits) {
        Table table = dynamoDB.getTable(tableName);
        ProvisionedThroughput throughput = new ProvisionedThroughput()
                .withReadCapacityUnits(readCapacityUnits)
                .withWriteCapacityUnits(writeCapacityUnits);

        return table.updateTable(throughput);
    }

    public static void main(String[] args) {

        ArrayList<KeySchemaElement> keySchemaElements = new ArrayList<>();
        keySchemaElements.add(new KeySchemaElement().withAttributeName("cdr_enterprise_id").withKeyType(KeyType.HASH));
        keySchemaElements.add(new KeySchemaElement().withAttributeName("cdr_unique_id").withKeyType(KeyType.RANGE));

        ArrayList<AttributeDefinition> attributeDefinitions = new ArrayList<>();
        attributeDefinitions.add(new AttributeDefinition().withAttributeName("cdr_enterprise_id").withAttributeType(ScalarAttributeType.N));
        attributeDefinitions.add(new AttributeDefinition().withAttributeName("cdr_unique_id").withAttributeType(ScalarAttributeType.S));
        attributeDefinitions.add(new AttributeDefinition().withAttributeName("cdr_start_time").withAttributeType(ScalarAttributeType.N));
        attributeDefinitions.add(new AttributeDefinition().withAttributeName("cdr_end_time").withAttributeType(ScalarAttributeType.N));

        ArrayList<LocalSecondaryIndex> localSecondaryIndexes = new ArrayList<>();
        ArrayList<KeySchemaElement> startTimeIndex = new ArrayList<>();
        startTimeIndex.add(new KeySchemaElement().withAttributeName("cdr_enterprise_id").withKeyType(KeyType.HASH));
        startTimeIndex.add(new KeySchemaElement().withAttributeName("cdr_start_time").withKeyType(KeyType.RANGE));
        localSecondaryIndexes.add(new LocalSecondaryIndex().withIndexName("startTimeIndex").withKeySchema(startTimeIndex)
                .withProjection(new Projection().withProjectionType(ProjectionType.ALL)));

        ArrayList<KeySchemaElement> endTimeIndex = new ArrayList<>();
        endTimeIndex.add(new KeySchemaElement().withAttributeName("cdr_enterprise_id").withKeyType(KeyType.HASH));
        endTimeIndex.add(new KeySchemaElement().withAttributeName("cdr_end_time").withKeyType(KeyType.RANGE));
        localSecondaryIndexes.add(new LocalSecondaryIndex().withIndexName("endTimeIndex").withKeySchema(endTimeIndex)
                .withProjection(new Projection().withProjectionType(ProjectionType.ALL)));

        //awsDynamoDBService.createTable("CdrObCustomer", keySchemaElements, attributeDefinitions, localSecondaryIndexes);

        keySchemaElements = new ArrayList<>();
        keySchemaElements.add(new KeySchemaElement().withAttributeName("cdr_enterprise_id").withKeyType(KeyType.HASH));
        keySchemaElements.add(new KeySchemaElement().withAttributeName("cdr_unique_id").withKeyType(KeyType.RANGE));

        attributeDefinitions = new ArrayList<>();
        attributeDefinitions.add(new AttributeDefinition().withAttributeName("cdr_enterprise_id").withAttributeType(ScalarAttributeType.N));
        attributeDefinitions.add(new AttributeDefinition().withAttributeName("cdr_unique_id").withAttributeType(ScalarAttributeType.S));
        attributeDefinitions.add(new AttributeDefinition().withAttributeName("cdr_main_unique_id").withAttributeType(ScalarAttributeType.S));
        attributeDefinitions.add(new AttributeDefinition().withAttributeName("cdr_start_time").withAttributeType(ScalarAttributeType.N));
        attributeDefinitions.add(new AttributeDefinition().withAttributeName("cdr_end_time").withAttributeType(ScalarAttributeType.N));

        localSecondaryIndexes = new ArrayList<>();
        startTimeIndex = new ArrayList<>();
        startTimeIndex.add(new KeySchemaElement().withAttributeName("cdr_enterprise_id").withKeyType(KeyType.HASH));
        startTimeIndex.add(new KeySchemaElement().withAttributeName("cdr_start_time").withKeyType(KeyType.RANGE));
        localSecondaryIndexes.add(new LocalSecondaryIndex().withIndexName("startTimeIndex").withKeySchema(startTimeIndex)
                .withProjection(new Projection().withProjectionType(ProjectionType.ALL)));

        endTimeIndex = new ArrayList<>();
        endTimeIndex.add(new KeySchemaElement().withAttributeName("cdr_enterprise_id").withKeyType(KeyType.HASH));
        endTimeIndex.add(new KeySchemaElement().withAttributeName("cdr_end_time").withKeyType(KeyType.RANGE));
        localSecondaryIndexes.add(new LocalSecondaryIndex().withIndexName("endTimeIndex").withKeySchema(endTimeIndex)
                .withProjection(new Projection().withProjectionType(ProjectionType.ALL)));

        ArrayList<KeySchemaElement> mainUniqueIdIndex = new ArrayList<>();
        mainUniqueIdIndex.add(new KeySchemaElement().withAttributeName("cdr_enterprise_id").withKeyType(KeyType.HASH));
        mainUniqueIdIndex.add(new KeySchemaElement().withAttributeName("cdr_main_unique_id").withKeyType(KeyType.RANGE));
        localSecondaryIndexes.add(new LocalSecondaryIndex().withIndexName("mainUniqueIdIndex").withKeySchema(mainUniqueIdIndex)
                .withProjection(new Projection().withProjectionType(ProjectionType.ALL)));

        //awsDynamoDBService.createTable("CdrObCustomerDetail", keySchemaElements, attributeDefinitions, localSecondaryIndexes);


    }

}
