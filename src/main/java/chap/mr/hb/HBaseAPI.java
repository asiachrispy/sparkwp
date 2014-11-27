package chap.mr.hb;

import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.HTablePool;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;

import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.hbase.util.Writables;
import org.apache.zookeeper.KeeperException;

@SuppressWarnings("deprecation")
public class HBaseAPI {
	
    static HBaseConfiguration cfg = null;
    static {
        Configuration HBASE_CONFIG = new Configuration();
        HBASE_CONFIG.set("hbase.zookeeper.quorum", "feige");
        HBASE_CONFIG.set("hbase.zookeeper.property.clientPort", "2222");
        cfg = new HBaseConfiguration(HBASE_CONFIG);
        
    }
    //static HTablePool pool = new HTablePool(cfg, 1000);   
    
    /**  
     * 创建表  
     * @param tableName  
     */  
    public static void createTable(String tableName,String[]families) {   
        System.out.println("start create table ......");   
        try {   
            HBaseAdmin hBaseAdmin = new HBaseAdmin(cfg);   
            if (hBaseAdmin.tableExists(tableName)) {// 如果存在要创建的表，那么先删除，再创建   
                hBaseAdmin.disableTable(tableName);   
                hBaseAdmin.deleteTable(tableName);   
                System.out.println(tableName + " is exist,detele....");   
            }   
            HTableDescriptor tableDescriptor = new HTableDescriptor(tableName);
            for(int i=0;i<families.length;i++){
            	tableDescriptor.addFamily(new HColumnDescriptor(families[i]));
            }
            //tableDescriptor.addFamily(new HColumnDescriptor("column1"));   
            //tableDescriptor.addFamily(new HColumnDescriptor("column2"));   
            //tableDescriptor.addFamily(new HColumnDescriptor("column3"));   
            hBaseAdmin.createTable(tableDescriptor);   
        } catch (MasterNotRunningException e) {   
            e.printStackTrace();   
        } catch (ZooKeeperConnectionException e) {   
            e.printStackTrace();   
        } catch (IOException e) {   
            e.printStackTrace();   
        }   
        System.out.println("end create table ......");   
    }   
  
    /**  
     * 插入数据  
     * @param tableName  
     */  
    public static void insertData(String tableName,String rowKey,String family,String[]qualifiers,String[] values) throws IOException {
        System.out.println("start insert data ......");   
        HTablePool pool = new HTablePool(cfg, 100);   
        HTable table = (HTable) pool.getTable(tableName);   
        Put put = new Put(rowKey.getBytes());
        
        for(int i=0;i<qualifiers.length;i++){
        	put.add(family.getBytes(), qualifiers[i].getBytes(), values[i].getBytes());
        }  
        try {   
            table.put(put);   
            table.close();
        }catch (IOException e) {   
            e.printStackTrace();   
        }finally {   
            if(table != null) {   
            	pool.putTable((HTableInterface)table);
            }   
        }  
        System.out.println("end insert data ......");   
    }   
  
    /**  
     * 删除一张表  
     * @param tableName  
     */  
    public static void dropTable(String tableName) {   
        try {   
            HBaseAdmin admin = new HBaseAdmin(cfg);   
            admin.disableTable(tableName);   
            admin.deleteTable(tableName);   
        } catch (MasterNotRunningException e) {   
            e.printStackTrace();   
        } catch (ZooKeeperConnectionException e) {   
            e.printStackTrace();   
        } catch (IOException e) {   
            e.printStackTrace();   
        }   
  
    }   

    /**  
     * 查询所有数据  
     * @param tableName  
     */  
    public static void QueryAll(String tableName) throws IOException {
        HTablePool pool = new HTablePool(cfg, 100);   
        HTable table = (HTable) pool.getTable(tableName);   
        try {
            ResultScanner rs = table.getScanner(new Scan());   
            for (Result r : rs) {   
                System.out.println("rowkey:" + new String(r.getRow()));   
                for (KeyValue keyValue : r.raw()) {
                    System.out.println("family：" + new String(keyValue.getFamily())   
                    +";Qualifier:"+new String(keyValue.getQualifier())
                    + ";Value:" + new String(keyValue.getValue()));   
                }
            }
            rs.close();
        } catch (IOException e) {
            e.printStackTrace();   
        } finally {   
            if(table != null) {   
            	pool.putTable((HTableInterface)table);   
            }   
        }
    }   
  
    /**  
     * 单条件查询,根据rowkey查询唯一一条记录  
     * @param tableName  
     */  
    public static void QueryByRowKey(String tableName,String rowKey) {   
  
        HTablePool pool = new HTablePool(cfg, 1000);   
        HTable table = (HTable) pool.getTable(tableName);   
        try {   
            Get scan = new Get(rowKey.getBytes());// 根据rowkey查询   
            Result r = table.get(scan);   
            System.out.println("rowkey:" + new String(r.getRow()));   
            for (KeyValue keyValue : r.raw()) {   
                System.out.println("Family：" + new String(keyValue.getFamily())   
                +";Qualifier:" + new String(keyValue.getQualifier())   
                + ";Vaule:" + new String(keyValue.getValue()));   
            }
        } catch (IOException e) {
            e.printStackTrace();
        }   
    }   
  
    /**  
     * 单条件按查询，查询多条记录  
     * @param tableName  
     */  
    public static void QueryByFilter(String tableName,String family,String qualifier,String value) {   
  
        try {   
            HTablePool pool = new HTablePool(cfg, 1000);   
            HTable table = (HTable) pool.getTable(tableName);   
            SingleColumnValueFilter filter = new SingleColumnValueFilter(Bytes.toBytes(family), 
            		Bytes.toBytes(qualifier), 
            		CompareOp.EQUAL,Bytes.toBytes(value));
            filter.setFilterIfMissing(true);
            
            Scan s = new Scan();   
            s.setFilter(filter);   
            ResultScanner rs = table.getScanner(s);   
            for (Result r : rs) {   
                System.out.println("rowkey:" + new String(r.getRow()));   
                for (KeyValue keyValue : r.raw()) {   
                    System.out.println("Family：" + new String(keyValue.getFamily())   
                    +";Qualifier:" + new String(keyValue.getQualifier())   
                    + ";Vaule:" + new String(keyValue.getValue()));   
                }   
            }
            rs.close();
        } catch (Exception e) {   
            e.printStackTrace();   
        }   
  
    }   
  
    /**  
     * 组合条件查询  
     * @param tableName  
     */  
    public static void QueryByMultiFilter(String tableName,String[] families,String[] qualifiers,String[] values) {   
  
        try {   
            HTablePool pool = new HTablePool(cfg, 100);   
            HTable table = (HTable) pool.getTable(tableName);   
  
            List<Filter> filters = new ArrayList<Filter>();   
  
            for(int index=0;index<families.length;index++){
            	SingleColumnValueFilter filter = new SingleColumnValueFilter(Bytes.toBytes(families[index]), 
            		Bytes.toBytes(qualifiers[index]), 
            		CompareOp.EQUAL,Bytes.toBytes(values[index]));
            	filters.add(filter);
            }
            
            FilterList filterList = new FilterList(filters);   
  
            Scan scan = new Scan();   
            scan.setFilter(filterList);   
            ResultScanner rs = table.getScanner(scan);   
            for (Result r : rs) {   
            	System.out.println("rowkey:" + new String(r.getRow()));   
                for (KeyValue keyValue : r.raw()) {   
                    System.out.println("Family：" + new String(keyValue.getFamily())   
                    +";Qualifier:" + new String(keyValue.getQualifier())   
                    + ";Vaule:" + new String(keyValue.getValue()));   
                }  
            }   
            rs.close();   
        } catch (Exception e) {   
            e.printStackTrace();   
        }
    } 
}
