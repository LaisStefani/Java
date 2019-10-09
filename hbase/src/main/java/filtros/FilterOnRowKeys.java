package filtros;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.hbase.filter.RowFilter;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;

public class FilterOnRowKeys {
    //metodo de varredura
    private static void printResults(ResultScanner scanResult) {
        //O print result que exibe os resultados do filtro
        System.out.println();
        System.out.println("Results: ");

        //For para percorrer os dados e lista-los
        for (Result res : scanResult) {
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
    }

    public static void main(String[] args) throws IOException {
        Configuration conf = HBaseConfiguration.create();
        Connection connection = ConnectionFactory.createConnection(conf);

        Table table = null;
        ResultScanner scanResult = null;
        /*BinaryComparator*/
        try {
            //instanciando a tabela
            table = connection.getTable(TableName.valueOf("census"));

            //operação de varredura tabela como nossa operação de filtro
            // na chave de linha usamos um filtro interno da blibliota chamado Rowfilter
            Filter filter = new RowFilter(CompareFilter.CompareOp.EQUAL,
                    new BinaryComparator(Bytes.toBytes("4")));
            //o primeiro é do tipo de operação que você deseja realizar no filtro
            // um filtro de igualdade smples [EQUAL =>, =<, >,<]

            //Aceita um filtro
            Scan userScan = new Scan();
            userScan.setFilter(filter);

            scanResult = table.getScanner(userScan);

            printResults(scanResult);
        } finally {
            connection.close();
            if (table != null) {
                table.close();
            }
            if (scanResult != null) {
                scanResult.close();
            }
        }

    }
}

