package filtros;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.BinaryComparator;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.filter.SubstringComparator;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;

public class FilterOnColumnValues {
    private static void printResults(ResultScanner scanResult) {
        //O print result que exibe os resultados do filtro
        System.out.println();
        System.out.println("Results: ");
        //Imprime todos os resultados que recuperamos da tabela Hbase
        for (Result res : scanResult) {
            for (Cell cell : res.listCells()) {
                String row = new String(CellUtil.cloneRow(cell));
                String family = new String(CellUtil.cloneFamily(cell));
                String column = new String(CellUtil.cloneQualifier(cell));
                String value = new String(CellUtil.cloneValue(cell));

                System.out.println(row + " " + family + " " + column + " " + value);
            }
        }
    }

    /*
    é possivel recuperar gesistros com base em filtros aplicados aos valores da coluna
    */
    public static void main(String[] args) throws IOException {
        Configuration conf = HBaseConfiguration.create();
        Connection connection = ConnectionFactory.createConnection(conf);

        Table table = null;
        ResultScanner scanResult = null;
        try {
            table = connection.getTable(TableName.valueOf("census"));
            //filtro interno para especificar as condições do valor da coluna
            //Esse filtro efetiva os registros que seriam recuperados por uma instrução sql
            //do tipo, onde gender é igual a male,
            // então todos os registros que o individuo for pesquisar são do sexo masculino
            //Temos que especificar as colunas que iremos filtar e usar o comparador
            SingleColumnValueFilter filter = new SingleColumnValueFilter(
                    Bytes.toBytes("personal"),
                    Bytes.toBytes("gender"),
                    CompareFilter.CompareOp.EQUAL,
                    new BinaryComparator(Bytes.toBytes("male")));

            //esse metodo é para quando o campo não esta preenchido
            filter.setFilterIfMissing(true);

            Scan userScan = new Scan();
            userScan.setFilter(filter);

            //recupera o sedultado e o exiba
            scanResult = table.getScanner(userScan);
            printResults(scanResult);


            /*SubstringComparator: novo comparador pra busca por string */
            filter = new SingleColumnValueFilter(
                    Bytes.toBytes("personal"),
                    Bytes.toBytes("name"),
                    CompareFilter.CompareOp.EQUAL,
                    new SubstringComparator("Jones"));

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
