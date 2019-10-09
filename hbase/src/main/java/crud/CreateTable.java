package crud;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;

import java.io.IOException;

public class CreateTable {
    public static void main(String[] args) throws IOException {
        //instanciando as configurações pra conexão
        Configuration conf = HBaseConfiguration.create();
        Connection connection = ConnectionFactory.createConnection(conf);

        //abrindo e fechando a conexão
        try {
            Admin admin = connection.getAdmin();

            //Criando a tabela
            HTableDescriptor tableName = new HTableDescriptor(TableName.valueOf("census"));

            //Criando as colunas
            tableName.addFamily(new HColumnDescriptor("personal"));
            tableName.addFamily(new HColumnDescriptor("professional"));

            //verificar se a tabela existe
            if (!admin.tableExists(tableName.getTableName())) {
                System.out.print("Creating the census table. ");

                admin.createTable(tableName);

                System.out.println("Done.");
            } else {
                System.out.println("Table already exists");
            }
        } finally {
            connection.close();
        }

    }
}