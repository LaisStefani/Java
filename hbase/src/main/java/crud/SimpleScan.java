package crud;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.client.*;

import java.io.IOException;

public class SimpleScan {
    public static void main(String[] args) throws IOException {
        Configuration conf = HBaseConfiguration.create();
        Connection connection = ConnectionFactory.createConnection(conf);

        Table table = null;
        //interface do lado do cliente para verificação de resultados
        ResultScanner scanResult = null;

        try {
            table = connection.getTable(TableName.valueOf("census"));

            //Scan da biblioteca do hbase
            Scan scan = new Scan();
            //chamada da tabela e "varredura" do scan
            scanResult = table.getScanner(scan);

            //um resultado é igual é um registro associadpo a uma chave de linha
            for (Result res : scanResult) {
                //pesquisa pelas 4 "dimenções" do Hbase com CellUtil e converter e strings
                for (Cell cell : res.listCells()) {
                    //busca individual pela chave da linha
                    String row = new String(CellUtil.cloneRow(cell));
                    //Familia de colunas
                    String family = new String(CellUtil.cloneFamily(cell));
                    //nome da coluna ou o qualificador da coluna
                    String column = new String(CellUtil.cloneQualifier(cell));
                    //registro de data e hora desse valor
                    String value = new String(CellUtil.cloneValue(cell));

                    System.out.println(row + " " + family + " " + column + " " + value);
                 }
            }

        } finally {
            connection.close();
            if (table != null) {
                table.close();
            } // O scan necessita ser fechado também
            if (scanResult != null) {
                scanResult.close();
            }
        }
    }
}



