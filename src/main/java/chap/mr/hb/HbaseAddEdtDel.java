package chap.mr.hb;

import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;

public class HbaseAddEdtDel {

    public static Configuration configuration = null;

    static {
        configuration = HBaseConfiguration.create();
//        configuration.set("hbase.master", "192.168.0.201:60000");
//        configuration.set("hbase.zookeeper.quorum", "192.168.0.201,192.168.0.202,192.168.0.203");
//        configuration.set("hbase.zookeeper.property.clientPort", "2181");
    }

    public static void main(String[] args) throws Exception {
        HBaseAdmin admin = new HBaseAdmin(configuration);
        if (admin.tableExists("riapguh")) {
            System.out.println("删除 table");
            admin.disableTable("riapguh");
            admin.deleteTable("riapguh");
        }

        //创建riapguh表
        System.out.println("创建 table");
        HTableDescriptor tableDescripter = new HTableDescriptor("riapguh".getBytes());//创建表
        tableDescripter.addFamily(new HColumnDescriptor("user"));//创建列簇user
        tableDescripter.addFamily(new HColumnDescriptor("dpt"));//创建列簇dpt
        admin.createTable(tableDescripter);

        HTable table = new HTable(configuration, "riapguh");

        //插入数据
        System.out.println("add riapguh data");
        List<Put> putuser = new ArrayList<Put>();


        Put user1 = new Put(new String("用户A").getBytes());
        //写入用户员信息
        user1.add(new String("user").getBytes(), new String("user_code").getBytes(), new String("u_0001").getBytes());
        user1.add(new String("user").getBytes(), new String("user_name").getBytes(), new String("u_用户A").getBytes());

        //写入部门信息
        user1.add(new String("dpt").getBytes(), new String("dpt_code").getBytes(), new String("d_001").getBytes());
        user1.add(new String("dpt").getBytes(), new String("dpt_name").getBytes(), new String("d_部门A").getBytes());
        putuser.add(user1);


        Put user2 = new Put(new String("用户B").getBytes());
        //写入用户员信息
        user2.add(new String("user").getBytes(), new String("user_code").getBytes(), new String("u_0002").getBytes());
        user2.add(new String("user").getBytes(), new String("user_name").getBytes(), new String("u_用户B").getBytes());

        //写入部门信息
        user2.add(new String("dpt").getBytes(), new String("dpt_code").getBytes(), new String("d_002").getBytes());
        user2.add(new String("dpt").getBytes(), new String("dpt_name").getBytes(), new String("d_部门B").getBytes());
        putuser.add(user2);


        Put user3 = new Put(new String("用户C").getBytes());
        //写入用户员信息
        user3.add(new String("user").getBytes(), new String("user_code").getBytes(), new String("u_0003").getBytes());
        user3.add(new String("user").getBytes(), new String("user_name").getBytes(), new String("u_用户C").getBytes());

        //写入部门信息
        user3.add(new String("dpt").getBytes(), new String("dpt_code").getBytes(), new String("d_003").getBytes());
        user3.add(new String("dpt").getBytes(), new String("dpt_name").getBytes(), new String("d_部门C").getBytes());
        putuser.add(user3);


        table.put(putuser);
        table.flushCommits();

        //更新用户B
        Put updateb = new Put(new String("用户B").getBytes());
        //写入用户员信息
        updateb.add(new String("user").getBytes(), new String("user_code").getBytes(), new String("u_000xsx").getBytes());
        updateb.add(new String("user").getBytes(), new String("user_name").getBytes(), new String("u_用户xsx").getBytes());
        //写入部门信息
        updateb.add(new String("dpt").getBytes(), new String("dpt_code").getBytes(), new String("d_00xsx").getBytes());
        updateb.add(new String("dpt").getBytes(), new String("dpt_name").getBytes(), new String("d_部门xsx").getBytes());
        table.put(updateb);
        table.flushCommits();
        //HBaseBasic.selectByRowKey("riapguh");
    }
}