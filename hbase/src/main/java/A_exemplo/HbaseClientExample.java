package A_exemplo;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.client.HBaseAdmin;

import java.io.IOException;
import java.util.Objects;

//install hbase locally & hbase master start
public class HbaseClientExample {

    public static void main(String[] args) throws IOException {
        new HbaseClientExample().connect();
    }

    private void connect() throws IOException {
        Configuration config = HBaseConfiguration.create();

        String path = Objects.requireNonNull(this.getClass().getClassLoader().getResource("hbase-site.xml")).getPath();

        config.addResource(new Path(path));

        try {
            HBaseAdmin.checkHBaseAvailable(config);
        } catch (MasterNotRunningException | org.apache.hadoop.hbase.shaded.com.google.protobuf.ServiceException e) {
            System.out.println("HBase is not running." + e.getMessage());
            return;
        }

        HBaseClientOperations HBaseClientOperations = new HBaseClientOperations();
        HBaseClientOperations.run(config);
    }

}